package com.example.lab1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    private Button btnOpenInput, btnSettings, btnInsert;
    private ListView lvApartamente;
    private ArrayList<APArtament> apartamenteList;
    private ApartamentAdapter adapter;
    private int selectedPosition = -1;

    private EditText etValoareString, etMin, etMax, etRating, etLitera;
    private Button btnCautaAdresa, btnInterval, btnStergere, btnUpdate;

    // pentru inserare
    private EditText etAdresa, etNrCamere, etRatingInput;

    private ApartamentDbHelper dbHelper;

    private void aplicaSetari() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String dimensiuneText = prefs.getString("textSize", "16");
        String culoareText = prefs.getString("textColor", "#000000");

        adapter.seteazaPreferinte(Integer.parseInt(dimensiuneText), culoareText);
    }

    private void salveazaObiectFavorit(APArtament apartament) {
        try {
            File file = new File(getFilesDir(), "favorite.dat");
            FileOutputStream fos = openFileOutput("favorite.dat", MODE_APPEND);
            ObjectOutputStream oos = (file.length() == 0)
                    ? new ObjectOutputStream(fos)
                    : new AppendableObjectOutputStream(fos);
            oos.writeObject(apartament);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dbHelper = new ApartamentDbHelper(this);

        // inițializare EditText-uri
        etValoareString = findViewById(R.id.etValoareString);
        etMin = findViewById(R.id.etMin);
        etMax = findViewById(R.id.etMax);
        etRating = findViewById(R.id.etRating);
        etLitera = findViewById(R.id.etLitera);

        etAdresa = findViewById(R.id.etAdresa); // nou
        etNrCamere = findViewById(R.id.etNrCamere); // nou
        etRatingInput = findViewById(R.id.etRatingInput); // nou

        // inițializare butoane
        btnCautaAdresa = findViewById(R.id.btnCautaAdresa);
        btnInterval = findViewById(R.id.btnInterval);
        btnStergere = findViewById(R.id.btnStergere);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnOpenInput = findViewById(R.id.btnOpenInput);
        btnSettings = findViewById(R.id.btnSettings);
        btnInsert = findViewById(R.id.btnInsert); // nou

        lvApartamente = findViewById(R.id.lvApartamente);

        apartamenteList = new ArrayList<>();
        adapter = new ApartamentAdapter(this, apartamenteList);
        lvApartamente.setAdapter(adapter);

        apartamenteList.addAll(dbHelper.getAll());
        adapter.notifyDataSetChanged();


        btnInsert.setOnClickListener(v -> {
            String adresa = etAdresa.getText().toString();
            String nrCamereStr = etNrCamere.getText().toString();
            String ratingStr = etRatingInput.getText().toString();

            if (!adresa.isEmpty() && !nrCamereStr.isEmpty() && !ratingStr.isEmpty()) {
                int nrCamere = Integer.parseInt(nrCamereStr);
                float rating = Float.parseFloat(ratingStr);
                APArtament apartament = new APArtament(adresa, nrCamere, rating);

                dbHelper.insertApartament(apartament);

                apartamenteList.clear();
                apartamenteList.addAll(dbHelper.getAll());
                adapter.notifyDataSetChanged();

                Toast.makeText(this, "Apartament salvat!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSettings.setOnClickListener(v -> {
            selectedPosition = -1;
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        btnOpenInput.setOnClickListener(v -> {
            selectedPosition = -1;
            Intent intent = new Intent(MainActivity.this, ApartamentInputActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        lvApartamente.setOnItemLongClickListener((parent, view, position, id) -> {
            APArtament favorit = apartamenteList.get(position);
            salveazaObiectFavorit(favorit);
            Toast.makeText(this, "Adăugat în favorite!", Toast.LENGTH_SHORT).show();
            return true;
        });

        btnCautaAdresa.setOnClickListener(v -> {
            String adresa = etValoareString.getText().toString();
            if (!adresa.isEmpty()) {
                apartamenteList.clear();
                apartamenteList.addAll(dbHelper.getByAdresa(adresa));
                adapter.notifyDataSetChanged();
            }
        });

        btnInterval.setOnClickListener(v -> {
            String minStr = etMin.getText().toString();
            String maxStr = etMax.getText().toString();
            if (!minStr.isEmpty() && !maxStr.isEmpty()) {
                int min = Integer.parseInt(minStr);
                int max = Integer.parseInt(maxStr);
                apartamenteList.clear();
                apartamenteList.addAll(dbHelper.getByInterval(min, max));
                adapter.notifyDataSetChanged();
            }
        });

        btnStergere.setOnClickListener(v -> {
            String ratingStr = etRating.getText().toString();
            if (!ratingStr.isEmpty()) {
                float rating = Float.parseFloat(ratingStr);
                dbHelper.deleteByRatingLessThan(rating);
                apartamenteList.clear();
                apartamenteList.addAll(dbHelper.getAll());
                adapter.notifyDataSetChanged();
            }
        });
        Button button = findViewById(R.id.button_open_list);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
            startActivity(intent);
        });

        btnUpdate.setOnClickListener(v -> {
            String literaStr = etLitera.getText().toString();
            if (!literaStr.isEmpty()) {
                char litera = literaStr.charAt(0);
                dbHelper.incrementCamereAdresaIncepeCu(litera);
                apartamenteList.clear();
                apartamenteList.addAll(dbHelper.getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            APArtament apartament = data.getParcelableExtra("apartament");
            if (apartament != null) {
                if (selectedPosition != -1) {
                    apartamenteList.set(selectedPosition, apartament);
                    selectedPosition = -1;
                } else {
                    apartamenteList.add(apartament);
                }
                adapter.notifyDataSetChanged();
            }
        }

        aplicaSetari();
    }
}
