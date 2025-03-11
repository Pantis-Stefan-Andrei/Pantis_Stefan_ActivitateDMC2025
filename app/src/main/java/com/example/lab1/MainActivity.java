package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Button btnOpenInput;
    private TextView tvApartamentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenInput = findViewById(R.id.btnOpenInput);
        tvApartamentInfo = findViewById(R.id.tvApartamentInfo);

        btnOpenInput.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ApartamentInputActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("apartament")) {
                try {
                    APArtament apartament = (APArtament) data.getSerializableExtra("apartament");
                    if (apartament != null) {
                        tvApartamentInfo.setText(
                                " Adresa: " + apartament.getAdresa() +
                                        "\n Număr camere: " + apartament.getNumarCamere() +
                                        "\n Are balcon: " + (apartament.isAreBalcon() ? "Da" : "Nu") +
                                        "\n Locație: " + apartament.getLocatie() +
                                        "\n Suprafață: " + apartament.getSuprafata() + " m²" +
                                        "\n Rating: " + apartament.getRating() + " stele" +
                                        "\n Disponibil pentru închiriere: " + (apartament.isInchiriere() ? "Da" : "Nu") +
                                        "\n Mobilat: " + (apartament.isMobilat() ? "Da" : "Nu") +
                                        "\n Tip Apartament: " + apartament.getTipApartament()
                        );
                    } else {
                        Toast.makeText(this, "Obiectul apartament este NULL!", Toast.LENGTH_LONG).show();
                        Log.e("MainActivity", "Apartament este NULL!");
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Eroare la primirea obiectului APArtament", e);
                    Toast.makeText(this, "Eroare la primirea obiectului!", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("MainActivity", "Nu există date în Intent!");
                Toast.makeText(this, "Datele nu au fost transmise!", Toast.LENGTH_LONG).show();
            }
        }
    }



}
