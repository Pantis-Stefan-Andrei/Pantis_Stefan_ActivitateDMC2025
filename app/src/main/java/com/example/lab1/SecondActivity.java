package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int val1 = getIntent().getIntExtra("value1", 5);
        int val2 = getIntent().getIntExtra("value2", 10);

        Button btnOpenThird = findViewById(R.id.btnOpenThird);
        btnOpenThird.setOnClickListener(view -> {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("message", "Hello from SecondActivity");
            intent.putExtra("value1", val1);
            intent.putExtra("value2", val2);
            startActivityForResult(intent, 1);
        });
    }
}