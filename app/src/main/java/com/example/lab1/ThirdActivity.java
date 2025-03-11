package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView textView = findViewById(R.id.textView);
        Button btnReturn = findViewById(R.id.btnReturn);

        // Preluare date din intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = extras.getString("message");
            int val1 = extras.getInt("value1", 0);
            int val2 = extras.getInt("value2", 0);
            int sum = val1 + val2;

            String displayText = message + "\n" + "Number 1: " + val1 + " Number 2: " +val2 ;
            textView.setText(displayText);
            Toast.makeText(this, displayText, Toast.LENGTH_LONG).show();

            btnReturn.setOnClickListener(view -> {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("responseMessage", "Result from ThirdActivity");
                returnIntent.putExtra("sumResult", sum);
                setResult(RESULT_OK, returnIntent);
                finish();
            });
        }
    }
}
