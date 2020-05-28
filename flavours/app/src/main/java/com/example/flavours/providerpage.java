package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class providerpage extends AppCompatActivity {

    EditText providerid;
    EditText display;
    Button signin;
    Button newuser;
    DatabaseReference databaseprovider;
    provider pr = new provider();
    int auth=0;
    String username1;
    String displayname1;

    Button btn;
    TextView ch;
    String personEmail = null;
    String personName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providerpage);



        providerid = findViewById(R.id.username);
        display = findViewById(R.id.displayname);
        signin = findViewById(R.id.providerlogin);
        displayname1 = display.getText().toString();
        username1 = providerid.getText().toString();
        System.out.println("debugging code hello karthik"+username1);
        Toast.makeText(this," can you please "+providerid.getText().toString(), Toast.LENGTH_LONG).show();
        databaseprovider = FirebaseDatabase.getInstance().getReference("provider");

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
        }

       findViewById(R.id.providerlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               final String username = providerid.getText().toString();
               final String disp = display.getText().toString();

//               if(username.equals(null) || disp.equals(null)){
//                   Toast.makeText(providerpage.this, "Enter all the details", Toast.LENGTH_LONG).show();
//               }


                databaseprovider = FirebaseDatabase.getInstance().getReference().child("provider");
                databaseprovider.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){

                            provider p = ds.getValue(provider.class);
                            String displayname = p.displayname;
                            String uname = p.username;
                            if(disp.equals(displayname) && username.equals(uname) && personEmail.equals(p.email)){
                                Toast.makeText(providerpage.this,"hello "+username, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(providerpage.this, providerorders.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("username",uname);
                                intent.putExtra("displayname",displayname);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        findViewById(R.id.newprovider).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(providerpage.this, provider_reg.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(providerpage.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
