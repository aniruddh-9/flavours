package com.example.flavours;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class provider_reg extends AppCompatActivity {

    DatabaseReference databaseprovider;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1 ;
    private FusedLocationProviderClient fusedLocationClient;
    EditText username;
    EditText pswd;
    EditText cookname;
    EditText nameprovider;
    EditText addressinput;
    EditText providernumber;
    EditText safetynumber;
    Button fetch_location;
    Button submitdata;
    private LocationManager locationManager;
    private LocationListener listener;


   double clientlat,clientlon;
    int res = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_reg);

        databaseprovider = FirebaseDatabase.getInstance().getReference("provider");
        username = findViewById(R.id.username);
        cookname = findViewById(R.id.cookname);
        nameprovider = findViewById(R.id.nameprovider);
        addressinput = findViewById(R.id.addressinput);
        providernumber = findViewById(R.id.providernumber);
        safetynumber = findViewById(R.id.safetynumber);
        fetch_location = findViewById(R.id.fetch_location);
        submitdata = findViewById(R.id.submitdata);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fetch_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


                listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
//                t.append("\n " + location.getLongitude() + " " + location.getLatitude());

                        clientlat = location.getLatitude();
                        clientlon = location.getLongitude();
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

            }
        });

        submitdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();



            }
        });



    }

    private void addData(){


        String cname = cookname.getText().toString();
        String nameprov = nameprovider.getText().toString();
        String adinp = addressinput.getText().toString();
        String prnum = providernumber.getText().toString();
        String safnum = safetynumber.getText().toString();
        String uname = username.getText().toString();
        String personEmail = null;
        String personName = null;
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
        }


        if(!TextUtils.isEmpty(cname) || !TextUtils.isEmpty(nameprov) || !TextUtils.isEmpty(adinp) || !TextUtils.isEmpty(prnum)|| !TextUtils.isEmpty(safnum)||!TextUtils.isEmpty(cname)){
            Toast.makeText(this,"you are in checking stage", Toast.LENGTH_LONG).show();
            String id = databaseprovider.push().getKey();
            provider provide = new provider(id, cname, nameprov, uname, adinp, prnum, safnum,personEmail,personName, clientlat, clientlon, "","");
            databaseprovider.child(id).setValue(provide);
            Toast.makeText(this, "artist added", Toast.LENGTH_LONG).show();



        }
        else{
            Toast.makeText(this,"enter all the details", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(provider_reg.this, providerpage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }

            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

        locationManager.requestLocationUpdates("gps", 5000, 0, listener);

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


}
