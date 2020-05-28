package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ratingactivity extends AppCompatActivity {

    EditText rating,suggestion;
    Button cancel, submit;
    String itemname, orderid, providername, providerdisplayname;
    int rat = 0;
    DatabaseReference dataref, databaseprovider;
    int prevnumber;
    Double prevvrat;
    String providerid, sugg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingactivity);

        Intent intent = getIntent();
        itemname = intent.getStringExtra("itemname");
        orderid = intent.getStringExtra("orderid");
        providername = intent.getStringExtra("providername");
        providerdisplayname = intent.getStringExtra("providerdisplay");

        System.out.println("provider dispaly name is "+providerdisplayname);
        System.out.println("provider name is"+providername);

        rating = findViewById(R.id.ratingbox);
        rating.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        rating.setKeyListener(DigitsKeyListener.getInstance("012345"));
        suggestion = findViewById(R.id.suggestionbox);

        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);


        dataref = FirebaseDatabase.getInstance().getReference().child("provider");
//        String output = dataref.child(providername).child("ratingprovider").get
//        System.out.println("output is "+output);
        dataref.keepSynced(true);



        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    provider p = dataSnapshot1.getValue(provider.class);
                    System.out.println("output is "+p.toString());
                    if(p.getUsername().equals(providername) && p.getDisplayname().equals(providerdisplayname)){
                        prevvrat = Double.parseDouble(p.getRatingprovider());
                        prevnumber = Integer.parseInt(p.getNumberofratings());
                        providerid = p.getProviderid();
                }



////

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rat = Integer.parseInt(rating.getText().toString());
                if(rat<=5) {
                    sugg = suggestion.getText().toString();
                    if (!sugg.equals("")) {

                        databaseprovider = FirebaseDatabase.getInstance().getReference("suggestions");
                        String uploadId = databaseprovider.push().getKey();

                        suggestion s = new suggestion(uploadId, itemname, providername, providerdisplayname, sugg);
                        databaseprovider.child(uploadId).setValue(s);
                        Toast.makeText(ratingactivity.this, "Thank you for submitting your suggestion", Toast.LENGTH_LONG).show();


                    }

                    double newrat = ((prevvrat * prevnumber) + rat) / (prevnumber + 1);
                    int newnumber = prevnumber + 1;
                    String newnumberstore = Integer.toString(newnumber);

                    dataref.child(providerid).child("ratingprovider").setValue(Double.toString(newrat));
                    dataref.child(providerid).child("numberofratings").setValue(Integer.toString(newnumber));
                    System.out.println("changing the value in the database");
//                Toast.makeText(ratingactivity.this,"Thank you for submitting your rating", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(ratingactivity.this, notifications.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                else{
                    Toast.makeText(ratingactivity.this, "rating should be less than 5", Toast.LENGTH_LONG).show();

                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ratingactivity.this, notifications.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });


    }
}
