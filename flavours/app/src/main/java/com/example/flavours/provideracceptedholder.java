package com.example.flavours;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class provideracceptedholder extends RecyclerView.Adapter<provideracceptedholder.ProviderAcceptedHolder> {

    Context context;
    ArrayList<orders> profiles;
    DatabaseReference databaseprovider;
    APIService apiService;
    FirebaseUser fuser;

    public provideracceptedholder(Context c , ArrayList<orders> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public ProviderAcceptedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProviderAcceptedHolder(LayoutInflater.from(context).inflate(R.layout.activity_provideracceptedholder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderAcceptedHolder holder, final int position) {
        final orders profile = profiles.get(position);
//        StorageReference path = FirebaseStorage.getInstance().getReferenceFromUrl(profiles.get(position).getImageurl());
        holder.itemname.setText("ITEM NAME : "+profile.getItemname() );
        holder.clientname.setText("Client Name : "+profile.getClientname());
        holder.clientmobile.setText("CLient Mobile no : "+profile.getClientnumber());
        holder.clientquantity.setText("Quantity : "+profile.getItemquantity());
        Picasso.with(context).load(profile.getItemimageurl()).into(holder.profilePic);
        holder.completedbutton.setVisibility(View.VISIBLE);
        holder.timeleftbutton.setVisibility(View.VISIBLE);
        holder.onClick(position,profile);


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        fuser = FirebaseAuth.getInstance().getCurrentUser();




        holder.timeleftbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = profile.getId();
                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
                databaseprovider.child(id).child("timeleftorcompleted").setValue("15 minutes left");
                Toast.makeText(context, "Notified to the Client", Toast.LENGTH_SHORT).show();

                String message = "15 minutes to go. Head to your destination";
                String itemname = profile.getItemname();
                String receiver = profile.getClientemail();

                sendOrderStatusNotification(receiver,message,itemname);

            }
        });
        holder.completedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = profile.getId();
                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
                databaseprovider.child(id).child("timeleftorcompleted").setValue("Preperation completed. Go to your destination.");
                databaseprovider.child(id).child("preperationstatus").setValue("Completed");
                Toast.makeText(context, "Order Completed", Toast.LENGTH_SHORT).show();

                String message = "Your order has been prepared. Go to your destination";
                String itemname = profile.getItemname();
                String receiver = profile.getClientemail();

                sendOrderStatusNotification(receiver,message,itemname);
            }
        });
        holder.chatbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MessageActivity.class);
                intent.putExtra("user",profile.itemprovidername);
                intent.putExtra("provider",profile.clientname);
                intent.putExtra("orderid",profile.id);
                intent.putExtra("isClient","false");
                v.getContext().startActivity(intent);
            }
        });
        holder.deliveredbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String id = profile.getId();
                                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
                                databaseprovider.child(id).child("ordersubmitted").setValue("yes");
                                databaseprovider.child(id).child("completedorder").setValue("yes");
                                databaseprovider.child(id).removeValue();

                                Toast.makeText(context, "Order Delivered", Toast.LENGTH_SHORT).show();

                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                String data = df.format(new Date());
                                databaseprovider = FirebaseDatabase.getInstance().getReference("completedorders");
                                String uploadId = databaseprovider.push().getKey();
                                completedorders c = new completedorders(uploadId, profile.getItemname(), profile.getProvideruname(), profile.getItemproviderdisplayname(), profile.getItemcost(), profile.getItemquantity(), data, profile.getClientname(),profile.getClientnumber());
                                databaseprovider.child(uploadId).setValue(c);





                                String message = "Your order has been delivered";
                                String itemname = profile.getItemname();
                                String receiver = profile.getClientemail();

                                sendOrderStatusNotification(receiver,message,itemname);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



//                String id = profile.getId();
//                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
//                databaseprovider.child(id).child("ordersubmitted").setValue("yes");
//                databaseprovider.child(id).child("completedorder").setValue("yes");
//
//                Toast.makeText(context, "Order Delivered", Toast.LENGTH_SHORT).show();
//
//                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//                String data = df.format(new Date());
//                databaseprovider = FirebaseDatabase.getInstance().getReference("completedorders");
//                String uploadId = databaseprovider.push().getKey();
//                completedorders c = new completedorders(uploadId, profile.getItemname(), profile.getProvideruname(), profile.getItemproviderdisplayname(), profile.getItemcost(), profile.getItemquantity(), data, profile.getClientname(),profile.getClientnumber());
//                databaseprovider.child(uploadId).setValue(c);
//
//
//
//
//
//                String message = "Your order has been delivered";
//                String itemname = profile.getItemname();
//                String receiver = profile.getClientemail();
//
//                sendOrderStatusNotification(receiver,message,itemname);
            }
        });
    }


    private void sendOrderStatusNotification(String receiver,String message,String itemname){
        int index = receiver.indexOf('@');
        receiver = receiver.substring(0,index);
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, message,
                            "Your Item: "+itemname, fuser.getEmail());

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            //  Toast.makeText(, "Failed!", Toast.LENGTH_SHORT).show();
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



    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class ProviderAcceptedHolder extends RecyclerView.ViewHolder
    {
        TextView itemname,clientname,clientmobile,clientquantity;
        ImageView profilePic;
        Button completedbutton,timeleftbutton,chatbox, deliveredbutton;
        public LinearLayout linearLayout;
        public ProviderAcceptedHolder(View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            clientname = itemView.findViewById(R.id.clientname);
            clientmobile = itemView.findViewById(R.id.clientmobile);
            profilePic = itemView.findViewById(R.id.post_image);
            completedbutton = itemView.findViewById(R.id.completedbutton);
            timeleftbutton = itemView.findViewById(R.id.fifteenminleftbutton);
            linearLayout = itemView.findViewById(R.id.providerordersview);
            clientquantity = itemView.findViewById(R.id.clientquantity);
            chatbox = itemView.findViewById(R.id.chatoption);
            deliveredbutton = itemView.findViewById(R.id.deliveredbutton);
        }
        public void onClick(final int position, final orders profile)
        {
//            btnreject.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                    Toast.makeText(context, "rejected", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
