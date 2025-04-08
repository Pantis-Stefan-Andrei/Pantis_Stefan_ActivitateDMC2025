package com.example.lab1;
import android.graphics.Color;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ApartamentAdapter extends ArrayAdapter<APArtament> {
    private final LayoutInflater inflater;
    private int textSize = 16; // valoare default
    private String textColor = "#000000"; // valoare default
    public void seteazaPreferinte(int textSize, String textColor) {
        this.textSize = textSize;
        this.textColor = textColor;
        notifyDataSetChanged();
    }

    public ApartamentAdapter(Context context, ArrayList<APArtament> apartamente) {
        super(context, 0, apartamente);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_apartament, parent, false);
        }

        APArtament ap = getItem(position);
        if (ap != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            TextView tvAdresa = view.findViewById(R.id.tvAdresa);
            tvAdresa.setText("Adresa: " + ap.getAdresa());
            tvAdresa.setTextSize(textSize);
            tvAdresa.setTextColor(Color.parseColor(textColor));

            TextView tvCamere = view.findViewById(R.id.tvCamere);
            tvCamere.setText("Camere: " + ap.getNumarCamere());
            tvCamere.setTextSize(textSize);
            tvCamere.setTextColor(Color.parseColor(textColor));

            TextView tvSuprafata = view.findViewById(R.id.tvSuprafata);
            tvSuprafata.setText("Suprafață: " + ap.getSuprafata() + " m²");
            tvSuprafata.setTextSize(textSize);
            tvSuprafata.setTextColor(Color.parseColor(textColor));

            CheckBox cbBalcon = view.findViewById(R.id.cbBalcon);
            cbBalcon.setChecked(ap.isAreBalcon());
            cbBalcon.setEnabled(false);

            TextView tvRating = view.findViewById(R.id.tvRating);
            StringBuilder stele = new StringBuilder("Rating: ");
            int fullStars = (int) ap.getRating();
            for (int i = 0; i < fullStars; i++) stele.append("⭐");
            tvRating.setText(stele.toString());
            tvRating.setTextSize(textSize);
            tvRating.setTextColor(Color.parseColor(textColor));

            TextView tvLocatie = view.findViewById(R.id.tvLocatie);
            tvLocatie.setText("Locație: " + ap.getLocatie().name());
            tvLocatie.setTextSize(textSize);
            tvLocatie.setTextColor(Color.parseColor(textColor));

            TextView tvTip = view.findViewById(R.id.tvTip);
            tvTip.setText("Tip apartament: " + ap.getTipApartament());
            tvTip.setTextSize(textSize);
            tvTip.setTextColor(Color.parseColor(textColor));

            CheckBox cbInchiriere = view.findViewById(R.id.cbInchiriere);
            cbInchiriere.setChecked(ap.isInchiriere());
            cbInchiriere.setEnabled(false);

            CheckBox cbMobilat = view.findViewById(R.id.cbMobilat);
            cbMobilat.setChecked(ap.isMobilat());
            cbMobilat.setEnabled(false);

            TextView tvData = view.findViewById(R.id.tvData);
            tvData.setText("Data construcției: " + sdf.format(ap.getDataConstructie()));
            tvData.setTextSize(textSize);
            tvData.setTextColor(Color.parseColor(textColor));
        }

        return view;
    }

}
