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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class foodclientacceptedholder extends RecyclerView.Adapter<foodclientacceptedholder.FoodAcceptedHolder> {

    Context context;
    ArrayList<orders> profiles;
    DatabaseReference databaseprovider;
    EditText ratingimput, suggestion;
    String rating,sugg;
    String providername = "";
    private DatabaseReference dataref;
    String iditerate,ratingrate, numeberrating;
    int toast = 0;

    public foodclientacceptedholder(Context c , ArrayList<orders> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public FoodAcceptedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodAcceptedHolder(LayoutInflater.from(context).inflate(R.layout.activity_foodclientacceptedholder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAcceptedHolder holder, final int position) {
        toast = 0;
        final orders profile = profiles.get(position);
        providername = profile.getItemprovidername();
        System.out.println("provider names is "+providername);
//        StorageReference path = FirebaseStorage.getInstance().getReferenceFromUrl(profiles.get(position).getImageurl());
        holder.itemname.setText("ITEM NAME : "+profile.getItemname() );
        holder.providername.setText("Client Name : "+profile.getItemprovidername());
        holder.providermobile.setText("CLient Mobile no : "+profile.getItemproviderphonenumber());
        holder.quantity.setText("Quantity : "+profile.getItemquantity());
        holder.statusreport.setText("STATUS : "+ profile.getPreperationstatus());
        holder.timeleft.setText("Time left : "+ profile.getTimeleftorcompleted());
        Picasso.with(context).load(profile.getItemimageurl()).into(holder.profilePic);
        holder.trackingbutton.setVisibility(View.VISIBLE);
        holder.chatbutton.setVisibility(View.VISIBLE);
        holder.chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MessageActivity.class);
                intent.putExtra("user",profile.clientname);
                intent.putExtra("provider",profile.itemprovidername);
                intent.putExtra("orderid",profile.id);
                intent.putExtra("isClient","true");
                v.getContext().startActivity(intent);
            }
        });


        holder.trackingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),TrackActivity.class);
                intent.putExtra("provideruname",profile.itemprovidername);
//                intent.putExtra("provider",profile.clientname);
//                intent.putExtra("orderid",profile.id);
                v.getContext().startActivity(intent);


            }
        });


        holder.ratingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),ratingactivity.class);
                intent.putExtra("orderid",profile.id);
                intent.putExtra("itemname",profile.itemname);
                intent.putExtra("providername",profile.provideruname);
                intent.putExtra("providerdisplay",profile.itemproviderdisplayname);
                v.getContext().startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class FoodAcceptedHolder extends RecyclerView.ViewHolder
    {
        TextView itemname,providername,providermobile,quantity,statusreport,timeleft;
        ImageView profilePic;
        Button trackingbutton, chatbutton,ratingbutton;
        public FoodAcceptedHolder(View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            providername = itemView.findViewById(R.id.clientname);
            providermobile = itemView.findViewById(R.id.clientmobile);
            profilePic = itemView.findViewById(R.id.post_image);
            trackingbutton = itemView.findViewById(R.id.livetrackingbutton);
            chatbutton = itemView.findViewById(R.id.chatbutton);
            quantity = itemView.findViewById(R.id.clientquantity);
            statusreport = itemView.findViewById(R.id.statusreport);
            timeleft = itemView.findViewById(R.id.remainingtime);
            ratingbutton = itemView.findViewById(R.id.ratingbutton);
        }
        public void onClick(final int position, final orders profile)
        {

        }
    }
}
