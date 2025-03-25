package com.example.lab1;

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

            ((TextView) view.findViewById(R.id.tvAdresa)).setText("Adresa: " + ap.getAdresa());
            ((TextView) view.findViewById(R.id.tvCamere)).setText("Camere: " + ap.getNumarCamere());
            ((TextView) view.findViewById(R.id.tvSuprafata)).setText("Suprafață: " + ap.getSuprafata() + " m²");

            ((CheckBox) view.findViewById(R.id.cbBalcon)).setChecked(ap.isAreBalcon());
            ((CheckBox) view.findViewById(R.id.cbBalcon)).setEnabled(false);

            // rating cu stele
            StringBuilder stele = new StringBuilder("Rating: ");
            int fullStars = (int) ap.getRating();
            for (int i = 0; i < fullStars; i++) stele.append("⭐");
            ((TextView) view.findViewById(R.id.tvRating)).setText(stele.toString());

            ((TextView) view.findViewById(R.id.tvLocatie)).setText("Locație: " + ap.getLocatie().name());
            ((TextView) view.findViewById(R.id.tvTip)).setText("Tip apartament: " + ap.getTipApartament());

            ((CheckBox) view.findViewById(R.id.cbInchiriere)).setChecked(ap.isInchiriere());
            ((CheckBox) view.findViewById(R.id.cbInchiriere)).setEnabled(false);

            ((CheckBox) view.findViewById(R.id.cbMobilat)).setChecked(ap.isMobilat());
            ((CheckBox) view.findViewById(R.id.cbMobilat)).setEnabled(false);

            ((TextView) view.findViewById(R.id.tvData)).setText("Data construcției: " + sdf.format(ap.getDataConstructie()));
        }

        return view;
    }
}
