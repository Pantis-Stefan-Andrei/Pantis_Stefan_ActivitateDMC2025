package com.example.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Parcelable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class ApartamentInputActivity extends AppCompatActivity {
    private EditText etAdresa, etNumarCamere, etSuprafata;
    private CheckBox cbBalcon;
    private RadioGroup rgLocatie;
    private Spinner spinnerTipApartament;
    private RatingBar rbRating;
    private Switch switchInchiriere;
    private ToggleButton toggleMobilat;
    private DatePicker datePicker;
    private Button btnSubmit;

    private APArtament apartamentEditabil;

    private void salveazaObiectInFisier(APArtament apartament) {
        try {
            File file = new File(getFilesDir(), "apartamente.dat");
            FileOutputStream fos = openFileOutput("apartamente.dat", MODE_APPEND);
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


        setContentView(R.layout.activity_apartament_input);


        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String culoare = prefs.getString("textColor", "#000000");
        float dimensiune = Float.parseFloat(prefs.getString("textSize", "16"));


        etAdresa = findViewById(R.id.etAdresa);
        etNumarCamere = findViewById(R.id.etNumarCamere);
        etSuprafata = findViewById(R.id.etSuprafata);
        cbBalcon = findViewById(R.id.cbBalcon);
        rgLocatie = findViewById(R.id.rgLocatie);
        spinnerTipApartament = findViewById(R.id.spinnerTipApartament);
        rbRating = findViewById(R.id.rbRating);
        switchInchiriere = findViewById(R.id.switchInchiriere);
        toggleMobilat = findViewById(R.id.toggleMobilat);
        datePicker = findViewById(R.id.datePicker);
        btnSubmit = findViewById(R.id.btnSubmit);


        etAdresa.setTextColor(Color.parseColor(culoare));
        etAdresa.setTextSize(dimensiune);
        etNumarCamere.setTextColor(Color.parseColor(culoare));
        etNumarCamere.setTextSize(dimensiune);
        etSuprafata.setTextColor(Color.parseColor(culoare));
        etSuprafata.setTextSize(dimensiune);


        apartamentEditabil = getIntent().getParcelableExtra("apartament");
        if (apartamentEditabil != null) {
            etAdresa.setText(apartamentEditabil.getAdresa());
            etNumarCamere.setText(String.valueOf(apartamentEditabil.getNumarCamere()));
            cbBalcon.setChecked(apartamentEditabil.isAreBalcon());
            etSuprafata.setText(String.valueOf(apartamentEditabil.getSuprafata()));
            rbRating.setRating(apartamentEditabil.getRating());
            switchInchiriere.setChecked(apartamentEditabil.isInchiriere());
            toggleMobilat.setChecked(apartamentEditabil.isMobilat());

            if (apartamentEditabil.getLocatie() == APArtament.TipLocatie.RURAL)
                rgLocatie.check(R.id.rbRural);
            else if (apartamentEditabil.getLocatie() == APArtament.TipLocatie.SEMIURBAN)
                rgLocatie.check(R.id.rbSemiurban);
            else
                rgLocatie.check(R.id.rbUrban);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.tip_apartamente, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTipApartament.setAdapter(adapter);
            int spinnerPosition = adapter.getPosition(apartamentEditabil.getTipApartament());
            spinnerTipApartament.setSelection(spinnerPosition);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(apartamentEditabil.getDataConstructie());
            datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        btnSubmit.setOnClickListener(v -> {
            APArtament.TipLocatie locatie = APArtament.TipLocatie.URBAN;
            int selectedId = rgLocatie.getCheckedRadioButtonId();
            if (selectedId == R.id.rbRural) locatie = APArtament.TipLocatie.RURAL;
            else if (selectedId == R.id.rbSemiurban) locatie = APArtament.TipLocatie.SEMIURBAN;

            Calendar c = Calendar.getInstance();
            c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

            APArtament apartament = new APArtament(
                    etAdresa.getText().toString(),
                    Integer.parseInt(etNumarCamere.getText().toString()),
                    cbBalcon.isChecked(),
                    locatie,
                    Float.parseFloat(etSuprafata.getText().toString()),
                    rbRating.getRating(),
                    switchInchiriere.isChecked(),
                    toggleMobilat.isChecked(),
                    spinnerTipApartament.getSelectedItem().toString(),
                    c.getTime()
            );

            Intent returnIntent = new Intent();
            returnIntent.putExtra("apartament", (Parcelable) apartament);
            setResult(RESULT_OK, returnIntent);


            salveazaObiectInFisier(apartament);

            finish();
        });
    }
}
