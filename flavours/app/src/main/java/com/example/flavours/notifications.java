package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notifications extends AppCompatActivity {



    String provname, pr, itmname, add, dname, uname, imageurl, mobnum, em, lat, lon, mobclient, description, sttime, entime;
    Double latitude, longitude;
    DatabaseReference dataref;
    private RecyclerView foodlist;
    ArrayList<orders> list;
    foodclientacceptedholder holder;
    String pname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.notifications);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.notifications:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,foodsearch.class));
                        overridePendingTransition(0,0);
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

        dataref = FirebaseDatabase.getInstance().getReference().child("orders");
        dataref.keepSynced(true);
        foodlist = findViewById(R.id.foodclientacceptedholder);
        foodlist.setHasFixedSize(true);
        System.out.println("entering accepted orders");
        foodlist.setLayoutManager(new LinearLayoutManager(this));
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(notifications.this);
            pname = acct.getDisplayName();


//        list = new ArrayList<orders>();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<orders>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {


                     orders p = dataSnapshot1.getValue(orders.class);
                     System.out.println("storing in the list adapter");
                     System.out.println("entered into linearlayout");
                     System.out.println(p.getItemprovideremail()+"stored value");
                     if(p.getClientname().equals(pname)) {
                         list.add(p);
                     }

                }
                System.out.println("today items display");
                holder = new foodclientacceptedholder(notifications.this,list);
                foodlist.setAdapter(holder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
