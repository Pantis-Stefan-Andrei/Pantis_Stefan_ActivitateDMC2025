package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

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
            APArtament.TipLocatie locatie = APArtament.TipLocatie.URBAN;
            int selectedId = rgLocatie.getCheckedRadioButtonId();
            if (selectedId == R.id.rbRural) {
                locatie = APArtament.TipLocatie.RURAL;
            } else if (selectedId == R.id.rbSemiurban) {
                locatie = APArtament.TipLocatie.SEMIURBAN;
            }

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
                    new Date()
            );

            Intent returnIntent = new Intent();
            returnIntent.putExtra("apartament", apartament);
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}
