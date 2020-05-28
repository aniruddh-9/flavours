package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class providerhistory extends AppCompatActivity {


    String uname,dname;
    private RecyclerView foodlist;
    private DatabaseReference dataref;
    ArrayList<orders> list;
    provideracceptedholder holder;
    @Override
    public void onBackPressed(){
        startActivity(new Intent(providerhistory.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerhistory);

        uname = getIntent().getStringExtra("username");
        dname = getIntent().getStringExtra("displayname");

//        DataHolder dh = DataHolder.getInstance();
//        dh.setData(uname);

        BottomNavigationView bottomNavigationView = findViewById(R.id.provider_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_history:
                        return true;
                    case R.id.navigation_additems:
                    {
                        Intent intent = new Intent(providerhistory.this, providerafterlogin.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",uname);
                        intent.putExtra("displayname",dname);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_orders:
                    {
                        Intent intent = new Intent(providerhistory.this, providerorders.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",uname);
                        intent.putExtra("displayname",dname);
                        startActivity(intent);
                        return true;}
                    case R.id.navigation_todaysitems:
                    {
                        Intent intent = new Intent(providerhistory.this, providertodayitems.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",uname);
                        intent.putExtra("displayname",dname);
                        startActivity(intent);
                        return true;}
                }
                return false;
            }
        });

        dataref = FirebaseDatabase.getInstance().getReference().child("orders");
        dataref.keepSynced(true);
        foodlist = findViewById(R.id.provideracceptedholder);
        foodlist.setHasFixedSize(true);
        foodlist.setLayoutManager(new LinearLayoutManager(this));
//        list = new ArrayList<orders>();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<orders>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    orders p = dataSnapshot1.getValue(orders.class);
                    if(p.getItemproviderdisplayname().equals(dname) && p.getItemprovidername().equals(uname)  && p.getStatusacceptrreject().equals("accepted") && p.getOrdersubmitted().equals("no")) {
                        System.out.println("storing in the list adapter");
                        System.out.println(p.getItemprovideremail()+"stored value");
                        list.add(p);
                    }
                }
                System.out.println("accepted items display");
                holder = new provideracceptedholder(providerhistory.this,list);
                foodlist.setAdapter(holder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
