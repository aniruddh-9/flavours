package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.flavours.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class afterLogin extends AppCompatActivity {

    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.foodButton:
                        // ОК button
                        Intent intent = new Intent(afterLogin.this, foodsearch.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                break;
                    case R.id.foodprovider:
                        // Cancel button
                        intent = new Intent(afterLogin.this, providerpage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
            }
        };
        findViewById(R.id.foodButton).setOnClickListener(oclBtn);
        findViewById(R.id.foodprovider).setOnClickListener(oclBtn);


//        findViewById(R.id.foodButton).setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(afterLogin.this, foodsearch.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                startActivity(intent);
//
//
//            }
//        });
//        findViewById(R.id.foodprovider).setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);

        int index = fuser.getEmail().indexOf('@');
        String email = fuser.getEmail().substring(0,index);
        reference.child(email).setValue(token1);
    }

}