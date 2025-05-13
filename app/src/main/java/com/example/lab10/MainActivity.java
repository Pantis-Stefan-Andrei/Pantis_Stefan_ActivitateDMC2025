package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button btnPolygon, btnCircle;
    Button openMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPolygon = findViewById(R.id.btnPolygon);
        btnCircle = findViewById(R.id.btnCircle);
        openMap = findViewById(R.id.openMap);

        btnPolygon.setOnClickListener(v -> {
            Intent i = new Intent(this, MapShapeActivity.class);
            i.putExtra("type", "polygon");
            startActivity(i);
        });

        btnCircle.setOnClickListener(v -> {
            Intent i = new Intent(this, MapShapeActivity.class);
            i.putExtra("type", "circle");
            startActivity(i);
        });
        openMap.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivity(i);
        });
    }
}

