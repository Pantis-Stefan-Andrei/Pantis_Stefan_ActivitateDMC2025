package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private Button btnOpenInput;
    private ListView lvApartamente;
    private ArrayList<APArtament> apartamenteList;
    private ArrayAdapter<APArtament> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenInput = findViewById(R.id.btnOpenInput);
        lvApartamente = findViewById(R.id.lvApartamente);

        apartamenteList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, apartamenteList);
        lvApartamente.setAdapter(adapter);

        btnOpenInput.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ApartamentInputActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        lvApartamente.setOnItemClickListener((parent, view, position, id) -> {
            APArtament selectedApartament = apartamenteList.get(position);
            Toast.makeText(this, selectedApartament.toString(), Toast.LENGTH_SHORT).show();
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

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("apartament")) {
                APArtament apartament = (APArtament) data.getSerializableExtra("apartament");
                if (apartament != null) {
                    apartamenteList.add(apartament);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
