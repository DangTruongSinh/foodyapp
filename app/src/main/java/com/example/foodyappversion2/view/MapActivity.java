package com.example.foodyappversion2.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodyappversion2.R;
import com.example.foodyappversion2.model.DowloadPolyline;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
//https://maps.googleapis.com/maps/api/directions/json?
//origin=Toronto&destination=Montreal
//&key=YOUR_API_KEY

    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitude,longitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        latitude = getIntent().getDoubleExtra("latitude",0);
        longitude = getIntent().getDoubleExtra("longitude",0);
        //
        SharedPreferences sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
        String latitueHienTai = sharedPreferences.getString("latitue","");
        String longtitueHienTai = sharedPreferences.getString("longtitue","");
        //
        String link = "https://maps.googleapis.com/maps/api/directions/json?origin="+latitueHienTai+","+longtitueHienTai+
                "&destination="+latitude+","+longitude+"&key=AIzaSyAgybQme2Zw1eTRjKwdUqJk4sHqKjkZnNk";
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
        new DowloadPolyline().execute(link);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(latitude,longitude);
        this.googleMap.addMarker(new MarkerOptions().position(latLng));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }
}
