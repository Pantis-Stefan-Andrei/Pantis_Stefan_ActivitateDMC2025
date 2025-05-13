package com.example.lab10;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.annotation.SuppressLint;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class MapShapeActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String type;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_shape);
        type = getIntent().getStringExtra("type");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));

                if ("polygon".equals(type)) {
                    drawPolygon(current);
                } else {
                    drawCircle(current);
                }
            } else {

                LatLng paris = new LatLng(48.8566, 2.3522);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 13));
                if ("polygon".equals(type)) {
                    drawPolygon(paris);
                } else {
                    drawCircle(paris);
                }
            }
        });
    }

    private void drawPolygon(LatLng center) {
        double offset = 0.001;
        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(center.latitude + offset, center.longitude - offset),
                        new LatLng(center.latitude + offset, center.longitude + offset),
                        new LatLng(center.latitude - offset, center.longitude + offset),
                        new LatLng(center.latitude - offset, center.longitude - offset))
                .strokeColor(Color.BLUE)
                .fillColor(Color.argb(100, 0, 0, 255));
        mMap.addPolygon(polygonOptions);
    }

    private void drawCircle(LatLng center) {
        CircleOptions circleOptions = new CircleOptions()
                .center(center)
                .radius(100)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(70, 255, 0, 0));
        mMap.addCircle(circleOptions);
    }
}
