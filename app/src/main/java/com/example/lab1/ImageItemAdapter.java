package com.example.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executors;

public class ImageItemAdapter extends ArrayAdapter<ImageItem> {

    private Context context;
    private List<ImageItem> items;

    public ImageItemAdapter(Context context, List<ImageItem> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_image, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_view);
        TextView textView = convertView.findViewById(R.id.text_view);

        textView.setText(item.getDescription());

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                InputStream input = new java.net.URL(item.getImageUrl()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                ((Activity) context).runOnUiThread(() -> imageView.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return convertView;
    }
}
