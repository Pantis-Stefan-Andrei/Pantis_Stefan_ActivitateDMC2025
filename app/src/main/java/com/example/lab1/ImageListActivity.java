package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {

    ListView listView;
    List<ImageItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        listView = findViewById(R.id.list_view);

        itemList = new ArrayList<>();
        itemList.add(new ImageItem(
                "https://www.ebihoreanul.ro/modules/news/files/0-IMAGINE-PRINCIPALA-Prima-Universitatii-imagini-reale-3.jpg",
                "Apartament modern",
                "https://www.ebihoreanul.ro/stiri/oportunitate-8-apartamente-semi-finisate-de-vanzare-in-prima-universitatii-192006.html"
        ));
        itemList.add(new ImageItem(
                "https://images.unsplash.com/photo-1600585154340-be6161a56a0c",
                "Apartament de lux",
                "https://unsplash.com/photos/eWqOgJ-lfiI"
        ));
        itemList.add(new ImageItem(
                "https://www.dezvoltator.eu/wp-content/uploads/2015/04/Sufragerie-2.jpg",
                "Living spațios",
                "https://adelaparvu.com/2019/07/03/apartament-cu-pereti-albi-elegant-amenajat/"
        ));
        itemList.add(new ImageItem(
                "https://adelaparvu.com/wp-content/uploads/2019/07/adelaparvu.com-despre-apartament-80-mp-cu-pereti-albi-elegant-amenajat-design-JT-Grupa-Foto-Ayuko-Studio-3-758x480.jpg",
                "Apartament cu ferestre mari",
                "https://adelaparvu.com/2019/07/03/apartament-cu-pereti-albi-elegant-amenajat/"
        ));
        itemList.add(new ImageItem(
                "https://www.total-design.ro/wp-content/uploads/2023/11/Poze-idei-renovare-apartament-2024.jpg",
                "Apartament minimalist",
                "https://www.total-design.ro/renovare-completa-apartament-2-camere-colentina-2021/poze-idei-renovare-apartament-2024/"
        ));


        ImageItemAdapter adapter = new ImageItemAdapter(this, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            ImageItem selectedItem = itemList.get(position);
            Intent intent = new Intent(ImageListActivity.this, WebViewActivity.class);
            intent.putExtra("url", selectedItem.getWebLink());
            startActivity(intent);
        });
    }
}
