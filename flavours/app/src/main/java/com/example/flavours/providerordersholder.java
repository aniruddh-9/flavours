package com.example.flavours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class providerordersholder extends RecyclerView.Adapter<providerordersholder.ProviderOrdersHolder> {

    Context context;
    ArrayList<orders> profiles;
    DatabaseReference databaseprovider;
    APIService apiService;
    boolean notify = false;
    FirebaseUser fuser;

    public providerordersholder(Context c , ArrayList<orders> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public ProviderOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProviderOrdersHolder(LayoutInflater.from(context).inflate(R.layout.activity_providerordersholder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderOrdersHolder holder, final int position) {
        final orders profile = profiles.get(position);
//        StorageReference path = FirebaseStorage.getInstance().getReferenceFromUrl(profiles.get(position).getImageurl());
        holder.itemname.setText("ITEM NAME : "+profile.getItemname() );
        holder.clientname.setText("Client Name : "+profile.getClientname());
        holder.clientmobile.setText("CLient Mobile no : "+profile.getClientnumber());
        holder.clientquantity.setText("Quantity : "+profile.getItemquantity());
        Picasso.with(context).load(profile.getItemimageurl()).into(holder.profilePic);
        holder.btmaccept.setVisibility(View.VISIBLE);
        holder.btnreject.setVisibility(View.VISIBLE);
        holder.onClick(position,profile);


        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        holder.btnreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("entered reject section");
                String pri = profile.getItemcost();
                int price = Integer.parseInt(pri);
                String qu = profile.getItemquantity();
                int quantity = Integer.parseInt(qu);
                int amu = price*quantity;
                String amount = Integer.toString(amu);
                System.out.println("the refund amount is "+amount);



                Intent intent = new Intent(v.getContext(), refund.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("dname",profile.itemproviderdisplayname);
                intent.putExtra("itemname",profile.itemname);
                intent.putExtra("uname",profile.itemprovidername);
                intent.putExtra("amount",amount);
                intent.putExtra("upiidclient",profile.upiidclient);
                intent.putExtra("upinameclient",profile.upinameclient);
                intent.putExtra("clientname",profile.clientname);
                intent.putExtra("orderid",profile.getId());


                String message = " has rejected your order. Your amount will be refunded soon";
                String itemname = profile.getItemname();
                sendOrderNotification(profile.getClientemail(),message,itemname);


                v.getContext().startActivity(intent);


//                String id = profile.getId();
//                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
//
//
//
//                databaseprovider.child(id).child("statusacceptrreject").setValue("rejected");
//
//                Toast.makeText(context, "rejected", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btmaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = profile.getId();
                databaseprovider = FirebaseDatabase.getInstance().getReference("orders");
                databaseprovider.child(id).child("statusacceptrreject").setValue("accepted");
                databaseprovider.child(id).child("preperationstatus").setValue("Started Preparing");

                String message = " has accepetd your order";
                String itemname = profile.getItemname();



                Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();
                sendOrderNotification(profile.getClientemail(),message,itemname);
            }
        });

    }

    private void sendOrderNotification(String receiver,String message,String itemname){
        int index = receiver.indexOf('@');
        receiver = receiver.substring(0,index);
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, fuser.getDisplayName() +message,
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

    class ProviderOrdersHolder extends RecyclerView.ViewHolder
    {
        TextView itemname,clientname,clientmobile,clientquantity;
        ImageView profilePic;
        Button btnreject,btmaccept;
        public LinearLayout linearLayout;
        public ProviderOrdersHolder(View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            clientname = itemView.findViewById(R.id.clientname);
            clientmobile = itemView.findViewById(R.id.clientmobile);
            profilePic = itemView.findViewById(R.id.post_image);
            btnreject = itemView.findViewById(R.id.rejectorder);
            btmaccept = itemView.findViewById(R.id.acceptbutton);
            linearLayout = itemView.findViewById(R.id.providerordersview);
            clientquantity = itemView.findViewById(R.id.clientquantity);
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
