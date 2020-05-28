package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class foodsearch extends AppCompatActivity {


    private RecyclerView foodlist;
    private DatabaseReference dataref,dataref1;
    ArrayList<TodayProvider> list=null;
    viewholder holder;
    ArrayList<TodayProvider> completelist=null;
    Intent intent;
    private LocationManager locationManager;
    private LocationListener listener;
    double clientlat;
    double clientlon;
    double radius = 1;
    EditText rad;
    Button update;

    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodsearch);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
////        getSupportActionBar().setTitle("AVAILABLE ITEMS");
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(foodsearch.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            }
//        });
        intent = getIntent();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.notifications:
                        startActivity(new Intent(getApplicationContext()
                                ,notifications.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext()
                                ,food_profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        rad = findViewById(R.id.radius);
        rad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        rad.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        update = findViewById(R.id.updatedistance);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                t.append("\n " + location.getLongitude() + " " + location.getLatitude());

                clientlat = location.getLatitude();
                clientlon = location.getLongitude();
                System.out.println("latitude is "+clientlat);
                System.out.println("longitude is "+clientlon);
                addadapter();
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
//        locationManager.requestLocationUpdates("gps", 5000, 0, listener);


        dataref = FirebaseDatabase.getInstance().getReference().child("TodayProvider");
        dataref.keepSynced(true);
        foodlist = findViewById(R.id.myrecyclerview);
        foodlist.setHasFixedSize(true);
        foodlist.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<TodayProvider>();
        addadapter();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("update button pressed");
                radius = Double.parseDouble(rad.getText().toString());
                if(!rad.getText().toString().equals(""))
                addadapter();

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(foodsearch.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;

            return (dist);
        }
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
    public int addadapter(){
        System.out.println("entering add adapter");

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<TodayProvider>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    TodayProvider p = dataSnapshot1.getValue(TodayProvider.class);
//                    double distance = HaversineInKM(clientlat,clientlon,p.latitude,p.longitude);
                    System.out.println("client latitude is "+clientlat);
                    System.out.println("client longitude is "+clientlon);
                    System.out.println("provider latitude is "+p.latitude);
                    System.out.println("provider longitude is "+clientlon);

                    double dist = distance(clientlat,clientlon,p.latitude,p.longitude,"K");
                    System.out.println("the provider is "+p.getUname());
                    System.out.println("the distance in kilo meters is "+dist + " to provider "+p.getUname());
                    if(!p.getRemoved().equals("removed") && dist<= radius ) {
                        list.add(p);
                    }
                }
                completelist = list;
                holder = new viewholder(foodsearch.this,list);
                foodlist.setAdapter(holder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return 1;
    }






}

