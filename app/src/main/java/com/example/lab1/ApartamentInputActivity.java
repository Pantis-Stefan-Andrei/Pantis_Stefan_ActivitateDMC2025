package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

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
        etSuprafata = findViewById(R.id.etSuprafata);
        datePicker = findViewById(R.id.datePicker);
        btnSubmit = findViewById(R.id.btnSubmit);

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
            returnIntent.putExtra("apartament", apartament);
            setResult(RESULT_OK, returnIntent);
            finish();
        });
    }
}
