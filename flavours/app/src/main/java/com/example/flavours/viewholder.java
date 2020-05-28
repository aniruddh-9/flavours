package com.example.flavours;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class viewholder extends RecyclerView.Adapter<viewholder.MyViewHolder> {

    Context context;
    ArrayList<TodayProvider> profiles;
    onClickListener onclicklistener;
    Double latitude;
    Double longitude;
    private DatabaseReference dataref;


    public viewholder(Context c , ArrayList<TodayProvider> p)
    {
        context = c;
        profiles = p;
        this.onclicklistener = onclicklistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardviewsearchfood,parent,false),onclicklistener);
    }





    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        final TodayProvider profile = profiles.get(position);




        //StorageReference path = FirebaseStorage.getInstance().getReferenceFromUrl(profiles.get(position).getImageurl());
        holder.foodname.setText("ITEM NAME : "+profile.getItemname() );
        holder.cost.setText("COST OF THE ITEM : "+profile.getAmount());
        holder.description.setText("DESCRIPTION : "+profile.getDescription());
        holder.rating.setText("Rating of the provider is "+profile.getRating());
        //Picasso.get().load(profiles.get(position).getImageurl()).into(holder.profilePic);
        //Glide.with(context).load(profile.getImageurl()).into(holder.profilePic);
        Picasso.with(context).load(profile.getImageurl()).into(holder.profilePic);
        System.out.println(holder.profilePic+"imageinfo");
//        holder.btn.setVisibility(View.VISIBLE);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), orderfoodclient.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("dname",profile.dname);
                intent.putExtra("itemname",profile.itemname);
                intent.putExtra("uname",profile.uname);
                intent.putExtra("amount",profile.amount);
                intent.putExtra("imageurl",profile.imageurl);
                intent.putExtra("mobilenumber",profile.moblienumber);
                intent.putExtra("email",profile.email);
                intent.putExtra("latitude",profile.latitude+"");
                intent.putExtra("longitude",profile.longitude+"");
                intent.putExtra("description",profile.description);
                intent.putExtra("starttime",profile.starttime);
                intent.putExtra("endtime",profile.endtime);
                intent.putExtra("upiid",profile.upiid);
                intent.putExtra("upiname",profile.upiname);
                v.getContext().startActivity(intent);

            }
        });



        //holder.onClick(position,profile);


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView foodname,cost,description,rating;
        ImageView profilePic;
        Button btn;
        public LinearLayout linearLayout;
        onClickListener onclickListener;

        public MyViewHolder(View itemView, onClickListener onclicklistener) {
            super(itemView);
            this.onclickListener = onclicklistener;
            foodname = itemView.findViewById(R.id.itemname);
            cost = itemView.findViewById(R.id.post_price);
            description = itemView.findViewById(R.id.clientmobile);
            profilePic = itemView.findViewById(R.id.post_image);
            rating = itemView.findViewById(R.id.rating);
//            btn = (Button) itemView.findViewById(R.id.check);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
        public void onClick(final int position, final TodayProvider profile)
        {
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, profile.itemname+" "+position+" is clicked", Toast.LENGTH_SHORT).show();
//                    System.out.println("position clicked is "+position);
//
//                    foodsearch s = new foodsearch();
//                    System.out.println("position clicked is "+position);
//
//                    s.buttonclicked(position,profiles);
//
//
//
//
//                }
//            });
        }

    }
    public interface onClickListener{

        void nodeonclick(int position);
    }
}
