package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Button btnOpenInput;
    private ListView lvApartamente;
    private ArrayList<APArtament> apartamenteList;
    private ApartamentAdapter adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenInput = findViewById(R.id.btnOpenInput);
        lvApartamente = findViewById(R.id.lvApartamente);

        apartamenteList = new ArrayList<>();
        adapter = new ApartamentAdapter(this, apartamenteList);
        lvApartamente.setAdapter(adapter);


        btnOpenInput.setOnClickListener(v -> {
            selectedPosition = -1;
            Intent intent = new Intent(MainActivity.this, ApartamentInputActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        lvApartamente.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Intent intent = new Intent(MainActivity.this, ApartamentInputActivity.class);
            intent.putExtra("apartament", apartamenteList.get(position));
            startActivityForResult(intent, REQUEST_CODE);
        });

        lvApartamente.setOnItemLongClickListener((parent, view, position, id) -> {
            apartamenteList.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            APArtament apartament = data.getParcelableExtra("apartament");
            if (apartament != null) {
                if (selectedPosition != -1) {
                    apartamenteList.set(selectedPosition, apartament);
                    selectedPosition = -1;
                } else {
                    apartamenteList.add(apartament);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
