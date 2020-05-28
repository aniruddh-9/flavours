package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.flavours.Notifications.Client;
import com.example.flavours.Notifications.Data;
import com.example.flavours.Notifications.MyResponse;
import com.example.flavours.Notifications.Sender;
import com.example.flavours.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    String user,provider,orderid,isClient;
    CircleImageView profile_image;
    TextView username;

    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;

    APIService apiService;
    boolean notify = false;
    FirebaseUser fuser;

    String recieveremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        intent = getIntent();

        user = intent.getStringExtra("user");
        provider = intent.getStringExtra("provider");
        orderid = intent.getStringExtra("orderid");
        isClient = intent.getStringExtra("isClient");




        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);                            // provider's profile pic
        username = findViewById(R.id.username);                                      // providers name
        text_send = findViewById(R.id.text_send);                                    // message
        btn_send = findViewById(R.id.btn_send);

        readMessages(user,provider,orderid);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    notify = true;
                    sendMessage(user, provider, orderid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You cannot send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });
    }

    public void sendMessage(final String sender, final String receiver, String orderid, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("");          // Reference to DB

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("orderid",orderid);
        reference.child("Chats").push().setValue(hashMap);                    // push the Message alongwith details to DB


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child(orderid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(Boolean.parseBoolean(isClient)==true){
                    recieveremail = order.getItemprovideremail();
                }else {
                    recieveremail = order.getClientemail();
                }
                if (notify) {
                   // sendNotification(recieveremail,sender, message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Receiver's email and Sender's username.
    private void sendNotification(String receiver, final String username, final String message){
        int index = receiver.indexOf('@');
        receiver = receiver.substring(0,index);
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            username);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(MessageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private  void readMessages (final String myid, final String userid, final String orderid){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) && chat.getOrderid().equals(orderid)
                            || chat.getReceiver().equals(userid) && chat.getSender().equals(myid) && chat.getOrderid().equals(orderid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this,mchat,myid);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

