package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Gestionare margini sistem
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referințe UI
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        EditText editText1 = findViewById(R.id.editText1);
        EditText editText2 = findViewById(R.id.editText2);

        // Setare acțiune pentru buton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1Str = editText1.getText().toString();
                String num2Str = editText2.getText().toString();

                if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
                    try {
                        int num1 = Integer.parseInt(num1Str);
                        int num2 = Integer.parseInt(num2Str);
                        int sum = num1 + num2;
                        textView.setText(String.valueOf(sum));
                    } catch (NumberFormatException e) {
                        textView.setText("Invalid input");
                    }
                } else {
                    textView.setText("Enter numbers");
                }
            }
        });
    }
}
