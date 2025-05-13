package com.example.lab10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText editTextCity;
    Button buttonSearch;
    TextView textViewResult;
    Spinner spinner;
    TextView textViewCityCode;

    String apiKey = "5ZZRQTbAAQfiIN5tWaDQofmuGsF1yoFo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editTextCity);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewResult = findViewById(R.id.textViewResult);
        spinner = findViewById(R.id.spinner);
        textViewCityCode = findViewById(R.id.textViewCityCode);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"1 zi", "5 zile", "10 zile"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        buttonSearch.setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            if (!city.isEmpty()) {
                new GetCityCodeTask().execute(city);
            } else {
                textViewResult.setText("Introduceți un oraș.");
            }
        });
    }

    private class GetCityCodeTask extends AsyncTask<String, Void, String[]> {
        String cityNameResponse = "";

        @Override
        protected String[] doInBackground(String... strings) {
            try {
                String cityName = strings[0];
                String url = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apiKey
                        + "&q=" + URLEncoder.encode(cityName, "UTF-8") + "&language=en-us";

                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) jsonBuilder.append(line);

                JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
                if (jsonArray.length() > 0) {
                    JSONObject obj = jsonArray.getJSONObject(0);
                    String cityCode = obj.getString("Key");
                    cityNameResponse = obj.getString("LocalizedName");
                    return new String[]{cityCode, cityNameResponse};
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                String cityCode = result[0];
                String cityName = result[1];
                textViewCityCode.setText("Oraș: " + cityName + "\nCod: " + cityCode);

                int days;
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        days = 1;
                        break;
                    case 1:
                        days = 5;
                        break;
                    default:
                        days = 10;
                        break;
                }
                new GetWeatherTask().execute(cityCode, String.valueOf(days));
            } else {
                textViewCityCode.setText("");
                textViewResult.setText("Oraș invalid sau nerecunoscut.");
            }
        }
    }

    private class GetWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                String cityCode = strings[0];
                String days = strings[1];
                String method;

                switch (days) {
                    case "1":
                        method = "1day";
                        break;
                    case "5":
                        method = "5day";
                        break;
                    default:
                        method = "10day";
                        break;
                }

                String url = "https://dataservice.accuweather.com/forecasts/v1/daily/" + method + "/" + cityCode
                        + "?apikey=" + apiKey + "&metric=true";
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) jsonBuilder.append(line);

                JSONObject response = new JSONObject(jsonBuilder.toString());
                JSONArray dailyForecasts = response.getJSONArray("DailyForecasts");

                StringBuilder result = new StringBuilder();
                for (int i = 0; i < dailyForecasts.length(); i++) {
                    JSONObject day = dailyForecasts.getJSONObject(i);
                    String date = day.getString("Date").substring(0, 10);
                    JSONObject temp = day.getJSONObject("Temperature");
                    double min = temp.getJSONObject("Minimum").getDouble("Value");
                    double max = temp.getJSONObject("Maximum").getDouble("Value");
                    String unit = temp.getJSONObject("Minimum").getString("Unit");

                    result.append("Ziua ").append(i + 1).append(" (").append(date).append("):\n")
                            .append("Min = ").append(min).append(unit).append(", ")
                            .append("Max = ").append(max).append(unit).append("\n\n");
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Eroare la obținerea vremii.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
        }
    }
}
