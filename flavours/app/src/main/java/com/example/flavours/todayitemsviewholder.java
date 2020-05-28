package com.example.flavours;


import android.content.Context;
import android.provider.ContactsContract;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class todayitemsviewholder extends RecyclerView.Adapter<todayitemsviewholder.Todayitemsholder> {

    Context context;
    ArrayList<TodayProvider> profiles;
    DatabaseReference databaseprovider;


    public todayitemsviewholder(Context c , ArrayList<TodayProvider> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public Todayitemsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Todayitemsholder(LayoutInflater.from(context).inflate(R.layout.todayitemscardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Todayitemsholder holder, final int position) {
        final TodayProvider profile = profiles.get(position);
        //StorageReference path = FirebaseStorage.getInstance().getReferenceFromUrl(profiles.get(position).getImageurl());
        holder.foodname.setText("ITEM NAME : "+profile.getItemname() );
        holder.cost.setText("COST OF THE ITEM : "+profile.getAmount());
        holder.description.setText("DESCRIPTION : "+profile.getDescription());
        Picasso.with(context).load(profile.getImageurl()).into(holder.profilePic);
        holder.btn.setVisibility(View.VISIBLE);
//        holder.onClick(position,profile);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = profile.getId();
                databaseprovider = FirebaseDatabase.getInstance().getReference("TodayProvider");
                databaseprovider.child(id).child("removed").setValue("removed");
                Toast.makeText(context, profile.getItemname()+ " is item removed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class Todayitemsholder extends RecyclerView.ViewHolder
    {
        TextView foodname,cost,description;
        ImageView profilePic;
        Button btn;
        public LinearLayout linearLayout;
        public Todayitemsholder(View itemView) {
            super(itemView);
            foodname = itemView.findViewById(R.id.itemname);
            cost = itemView.findViewById(R.id.itemprice);
            description = itemView.findViewById(R.id.itemdescription);
            profilePic = itemView.findViewById(R.id.post_image);
            btn = itemView.findViewById(R.id.removeimage);
            linearLayout = itemView.findViewById(R.id.todayitemscard);
        }
        public void onClick(final int position, final TodayProvider profile)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, profile.itemname+" "+position+" is clicked", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}

