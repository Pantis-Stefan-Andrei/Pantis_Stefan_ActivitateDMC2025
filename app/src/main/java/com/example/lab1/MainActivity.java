package com.example.lab1;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import android.os.Parcelable;
import android.content.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.example.lab1.AppendableObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Button btnOpenInput;
    private Button btnSettings;
    private ListView lvApartamente;
    private ArrayList<APArtament> apartamenteList;
    private ApartamentAdapter adapter;
    private int selectedPosition = -1;
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

        btnOpenInput = findViewById(R.id.btnOpenInput);
        btnSettings = findViewById(R.id.btnSettings);
        lvApartamente = findViewById(R.id.lvApartamente);

        apartamenteList = new ArrayList<>();
        adapter = new ApartamentAdapter(this, apartamenteList);
        lvApartamente.setAdapter(adapter);


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

        lvApartamente.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Intent intent = new Intent(MainActivity.this, ApartamentInputActivity.class);
            intent.putExtra("apartament", (Parcelable) apartamenteList.get(position));
            startActivityForResult(intent, REQUEST_CODE);
        });

        lvApartamente.setOnItemLongClickListener((parent, view, position, id) -> {
            APArtament favorit = apartamenteList.get(position);
            salveazaObiectFavorit(favorit);
            Toast.makeText(this, "Adăugat în favorite!", Toast.LENGTH_SHORT).show();
            return true;

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

        aplicaSetari();  // AICI
    }

}
