package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.flavours.Notifications.Data;
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

import java.util.ArrayList;

public class providertodayitems extends AppCompatActivity {

    private RecyclerView foodlist;
    private DatabaseReference dataref;
    ArrayList<TodayProvider> list;
    todayitemsviewholder holder;
    private ValueEventListener valevlis;

    String uname,dname;
    String personEmail;
    String personName;

    @Override
    public void onBackPressed(){
        startActivity(new Intent(providertodayitems.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providertodayitems);

        uname = getIntent().getStringExtra("username");
        dname = getIntent().getStringExtra("displayname");


     /*   DatabaseReference ref = FirebaseDatabase.getInstance().getReference("provider").child(uname);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     String username = dataSnapshot.child("provideraddress").getValue().toString();
                     Log.i("TAGGGGGGGGGGGGGG",username);
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });*/


                //   FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                //  Log.i("TASSSSSSSSSSSSS",user.getDisplayName());

//        DataHolder dh = DataHolder.getInstance();
//        dh.setData(uname);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(providertodayitems.this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            System.out.println("todayitems"+personName+"    "+personEmail);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.provider_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_todaysitems);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_history:
                    {
                        Intent intent = new Intent(providertodayitems.this, providerhistory.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",uname);
                        intent.putExtra("displayname",dname);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_additems:
                    {
                        Intent intent = new Intent(providertodayitems.this, providerafterlogin.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",uname);
                        intent.putExtra("displayname",dname);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_orders:
                    {
                        Intent intent = new Intent(providertodayitems.this, providerorders.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",uname);
                        intent.putExtra("displayname",dname);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_todaysitems:
                        return true;
                }
                return false;
            }
        });
        dataref = FirebaseDatabase.getInstance().getReference().child("TodayProvider");
        dataref.keepSynced(true);
        foodlist = findViewById(R.id.todayitemsrecyclerview);
        foodlist.setHasFixedSize(true);
        foodlist.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<TodayProvider>();
        valevlis = dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<TodayProvider>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    TodayProvider p = dataSnapshot1.getValue(TodayProvider.class);
                    if(p.getEmail().equals(personEmail) && p.getGivennameemail().equals(personName) && !p.getRemoved().equals("removed")) {
                        System.out.println("storing in the list adapter");
                        System.out.println(p.getEmail()+"stored value");
                        list.add(p);
                    }
                }
                System.out.println("today items display");
                holder = new todayitemsviewholder(providertodayitems.this,list);
                foodlist.setAdapter(holder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//        System.out.println("name is"+account.getDisplayName());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataref.removeEventListener(valevlis);
    }


    }

