package com.example.lab1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class FavoriteFirebaseActivity extends AppCompatActivity {

    private ListView lvFavorite;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> favoriteList;

    private static final String FIREBASE_URL =
            "https://lab11-96a9e-default-rtdb.europe-west1.firebasedatabase.app/apartamente.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_firebase);

        lvFavorite = findViewById(R.id.lvFavorite);
        favoriteList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteList);
        lvFavorite.setAdapter(adapter);

        new LoadFavoritesTask().execute();
    }

    private class LoadFavoritesTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> resultList = new ArrayList<>();
            try {
                URL url = new URL(FIREBASE_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONObject json = new JSONObject(response.toString());
                    Iterator<String> keys = json.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        JSONObject obj = json.getJSONObject(key);
                        if (obj.has("favorit") && obj.getBoolean("favorit")) {
                            String item = "Adresa: " + obj.optString("adresa") +
                                    ", Camere: " + obj.optInt("numarCamere") +
                                    ", Rating: " + obj.optDouble("rating", 0.0);
                            resultList.add(item);
                        }
                    }

                } else {
                    Log.e("HTTP_ERROR", "Cod răspuns: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result.isEmpty()) {
                Toast.makeText(FavoriteFirebaseActivity.this, "Nu s-au găsit favorite!", Toast.LENGTH_SHORT).show();
            } else {
                favoriteList.clear();
                favoriteList.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
