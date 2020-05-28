package com.example.flavours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TrackActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions user;
    private MarkerOptions provider;
    String providerName;
    Double latitude;
    Double longitude;
    double clientlat, clientlon;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Intent intent = getIntent();
        providerName = intent.getStringExtra("provideruname");


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                t.append("\n " + location.getLongitude() + " " + location.getLatitude());

                clientlat = location.getLatitude();
                clientlon = location.getLongitude();
                user = new MarkerOptions().position(new LatLng(clientlat,clientlon)).title("User");
                System.out.println("latitude is "+clientlat);
                System.out.println("longitude is "+clientlon);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        configure_button();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        user = new MarkerOptions().position(new LatLng(17.3825453,78.436356)).title("User");
        mMap.addMarker(user);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("provider").child(providerName).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        com.example.flavours.provider p = dataSnapshot.getValue(com.example.flavours.provider.class);
                        assert p != null;
                        latitude = p.getLatitude();
                        // Log.i("TAGGGGGGGGGGGGGGG",latitude.toString());
                        longitude = p.getLongitude();
                        // Log.i("TAGGGGGGGGGGGGGGG",longitude.toString());
                        provider = new MarkerOptions().position(new LatLng(latitude,longitude)).title("Provider");
                        mMap.addMarker(provider);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user.getPosition(),14f));
                        mMap.addPolyline(new PolylineOptions()
                                .add(user.getPosition())
                                .add(provider.getPosition())
                                .width(2f)
                                .color(Color.RED)
                        );
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            System.out.println("latitude null");
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
//        addadapter();
    }
}

