package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class ApartamentInputActivity extends AppCompatActivity {
    private EditText etAdresa, etNumarCamere, etSuprafata;
    private CheckBox cbBalcon;
    private RadioGroup rgLocatie;
    private Spinner spinnerTipApartament;
    private RatingBar rbRating;
    private Switch switchInchiriere;
    private ToggleButton toggleMobilat;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartament_input);

        etAdresa = findViewById(R.id.etAdresa);
        etNumarCamere = findViewById(R.id.etNumarCamere);
        cbBalcon = findViewById(R.id.cbBalcon);
        rgLocatie = findViewById(R.id.rgLocatie);
        spinnerTipApartament = findViewById(R.id.spinnerTipApartament);
        rbRating = findViewById(R.id.rbRating);
        switchInchiriere = findViewById(R.id.switchInchiriere);
        toggleMobilat = findViewById(R.id.toggleMobilat);
        btnSubmit = findViewById(R.id.btnSubmit);
        etSuprafata = findViewById(R.id.etSuprafata);


        btnSubmit.setOnClickListener(v -> {
            String adresa = etAdresa.getText().toString();
            int numarCamere = Integer.parseInt(etNumarCamere.getText().toString());
            boolean areBalcon = cbBalcon.isChecked();
            float suprafata = Float.parseFloat(etSuprafata.getText().toString());
            float rating = rbRating.getRating();
            boolean inchiriere = switchInchiriere.isChecked();
            boolean mobilat = toggleMobilat.isChecked();
            String tipApartament = spinnerTipApartament.getSelectedItem().toString();

            APArtament.TipLocatie locatie = APArtament.TipLocatie.URBAN;
            int selectedId = rgLocatie.getCheckedRadioButtonId();
            if (selectedId == R.id.rbRural) {
                locatie = APArtament.TipLocatie.RURAL;
            } else if (selectedId == R.id.rbSemiurban) {
                locatie = APArtament.TipLocatie.SEMIURBAN;
            }

            APArtament apartament = new APArtament(adresa, numarCamere, areBalcon, locatie, suprafata,
                    rating, inchiriere, mobilat, tipApartament);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("apartament", apartament);
            setResult(RESULT_OK, returnIntent);
            finish();
        });

    }
}
