package com.example.lab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private EditText etSize, etColor;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etSize = findViewById(R.id.etSize);
        etColor = findViewById(R.id.etColor);
        btnSave = findViewById(R.id.btnSave);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        etSize.setText(prefs.getString("textSize", "16"));
        etColor.setText(prefs.getString("textColor", "#000000"));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("textSize", etSize.getText().toString());
            editor.putString("textColor", etColor.getText().toString());
            editor.apply();
            finish();
        });
    }
}
