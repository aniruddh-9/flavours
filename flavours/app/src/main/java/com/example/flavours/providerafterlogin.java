package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class providerafterlogin extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1 ;
        String rating;
        EditText itemname1;
        EditText mobilenumber,upiidtext,upinametext;
        TextView viewstarttime,viewendtime;
        EditText description1;
        EditText amount1;
        TextView t1;
        String uname,dname;
        TodayProvider tp = new TodayProvider();
        Button imageupload1, additem1,starttime,endtime;
        ImageView imageview;
        DatabaseReference databaseprovider, dataref;
        FirebaseStorage storage;
        StorageReference mstorageReference;
        String ref;
        String personEmail = null;
        String personName = null;
        private final int PICK_IMAGE_REQUEST = 71;
        private Uri filePath;
        String unamefrom,dnamefrom;
        String unamestore,dnamestore,amountstore,starttimestore,endtimestore,descriptionstore,itemnamestore,mobilenum;
        Double latitude;
        Double longitude;
        String upiid;
        String upiname;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(providerafterlogin.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerafterlogin);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        unamefrom = getIntent().getStringExtra("username");
        dnamefrom = getIntent().getStringExtra("displayname");
        unamestore = unamefrom;
        dnamestore = dnamefrom;

        BottomNavigationView bottomNavigationView = findViewById(R.id.provider_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_additems);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_history:
                    {
                        Intent intent = new Intent(providerafterlogin.this, providerhistory.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",unamefrom);
                        intent.putExtra("displayname",dnamefrom);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_additems:
                        return true;
                    case R.id.navigation_orders:
                    {
                        Intent intent = new Intent(providerafterlogin.this, providerorders.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",unamefrom);
                        intent.putExtra("displayname",dnamefrom);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_todaysitems:
                    {
                        Intent intent = new Intent(providerafterlogin.this, providertodayitems.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",unamefrom);
                        intent.putExtra("displayname",dnamefrom);
                        startActivity(intent);
                        return true;}
                }
                return false;
            }
        });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
        }

        viewstarttime = findViewById(R.id.viewstrarttime);
        viewendtime = findViewById(R.id.viewendtime);
        itemname1 = findViewById(R.id.foodname);
        description1 = findViewById(R.id.description);
        amount1 = findViewById(R.id.amount);
        additem1 = findViewById(R.id.additem);
        imageupload1 = findViewById(R.id.imageupload);
        imageview = findViewById(R.id.imageView);
        t1 = findViewById(R.id.displayname);
        endtime = findViewById(R.id.endtimebutton);
        mobilenumber = findViewById(R.id.providermobile);
        upiidtext = findViewById(R.id.upiid);
        upinametext = findViewById(R.id.upiname);
        String temp = mobilenumber.getText().toString();
        System.out.println("temp mobile number is "+ temp);

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minut = cal.get(Calendar.MINUTE);
        String start = ""+hour+" hours :"+minut+" mins";
        viewstarttime.setText(start);
        starttimestore = hour+"/"+minut;
        descriptionstore = description1.getText().toString();


        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        t1.setText(" WELCOME:"+ unamefrom);


        storage = FirebaseStorage.getInstance();
        mstorageReference = storage.getReference("uploads");


        tp.itemname = itemname1.getText().toString();
        tp.amount = amount1.getText().toString();
        tp.description = description1.getText().toString();
        tp.uname = uname;
        tp.dname = dname;
        tp.starttime = starttimestore;
        tp.endtime = endtimestore;
        databaseprovider = FirebaseDatabase.getInstance().getReference("TodayProvider");
        dataref = FirebaseDatabase.getInstance().getReference().child("provider");
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    provider p = dataSnapshot1.getValue(provider.class);

                    if(p.displayname.equals(dnamefrom) && p.username.equals(unamefrom)){
                        rating = p.getRatingprovider();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mobilenum = temp;
        System.out.println("mobile number is "+mobilenum);

        imageupload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLocation();
                System.out.println("latitude is "+latitude);
                System.out.println("longitude is "+longitude);
                fileChoose();

            }
        });

        additem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("item name is "+ itemname1.getText().toString());
//                System.out.println("starttime name is "+ starttime1.getText().toString());
//                System.out.println("endtime is "+ endtime1.getText().toString());
                System.out.println("description is  is "+ description1.getText().toString());
                descriptionstore = description1.getText().toString();
                itemnamestore = itemname1.getText().toString();
                amountstore = amount1.getText().toString();
                String number = mobilenumber.getText().toString();
                System.out.println("mobile number is "+number);

                if(!itemnamestore.equals("")&& !endtimestore.equals("") && !amountstore.equals("") && !descriptionstore.equals("")){

//                    uploadImage();
//                    addData();
                    uploadFile();

                    // fileUploader();

                }
                else{
                    Toast.makeText(providerafterlogin.this,"enter all the details mentioned above", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void fileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageview.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        final String date = ""+day+"/"+month+"/"+year;
        if (filePath != null) {
            System.out.println("hello");
            final StorageReference fileReference = mstorageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(filePath));

            fileReference.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {

                        Uri downloadUri = task.getResult();
                        System.out.println("hello");
                            String uploadId = databaseprovider.push().getKey();
                            String number = mobilenumber.getText().toString();
                            System.out.println("mobile number is "+number);

                            Toast.makeText(providerafterlogin.this, "Upload successful", Toast.LENGTH_LONG).show();
                            System.out.println("username  "+unamestore+"  itemname "+itemnamestore +"  starttiime  "+starttimestore
                                    +"  endtime  "+endtimestore  +"  displayname  "+dnamestore+"  amount "+amountstore+"  description "+descriptionstore);


                            if(!unamestore.equals(null)
                                    && !itemnamestore.equals(null)
                                    &&
                                    !dnamestore.equals(null) &&
                                    !amountstore.equals(null) &&
                                    !descriptionstore.equals(null) &&
                                    !upiidtext.getText().toString().equals(null) &&
                                    !upinametext.getText().toString().equals(null)) {

                                TodayProvider upload = new TodayProvider(uploadId, dnamestore, unamestore, itemname1.getText().toString(), tp.starttime, endtimestore, amount1.getText().toString(), description1.getText().toString(),
                                        downloadUri.toString(), date, personEmail, personName, number, latitude, longitude, "", upiidtext.getText().toString(), upinametext.getText().toString(),rating);

                                databaseprovider.child(uploadId).setValue(upload);
                                Toast.makeText(providerafterlogin.this, "the data has been uploaded", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(providerafterlogin.this, providertodayitems.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("username", unamefrom);
                                intent.putExtra("displayname", dnamefrom);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(providerafterlogin.this, "enter all the details " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }


                    } else {
                        Toast.makeText(providerafterlogin.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = findViewById(R.id.viewendtime);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
        endtimestore = ""+hourOfDay+"/"+minute;
    }
    private void findLocation() {
        if (ContextCompat.checkSelfPermission(providerafterlogin.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(providerafterlogin.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to acess this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(providerafterlogin.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(providerafterlogin.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            System.out.println("location is out side if loop");
                            if (location != null) {
                                System.out.println("location is being set");
                                // Logic to handle location object
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                System.out.println(latitude.doubleValue()+"latitide");
                                System.out.println(longitude.doubleValue()+"longitude");
                            }
                        }
                    });
            Toast.makeText(this,"location set", Toast.LENGTH_LONG).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //abc
            }else{

            }
        }
    }

}



