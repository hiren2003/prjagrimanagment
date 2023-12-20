package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RcuserAdapter extends RecyclerView.Adapter<RcuserAdapter.ViewHolder>{
    Context context;
    ArrayList<clsUserModel> userModelArrayList;
    boolean isAdmin;

    public RcuserAdapter(Context context, ArrayList<clsUserModel> userModelArrayList,  boolean isAdmin) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
        this.isAdmin=isAdmin;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtpname.setText(userModelArrayList.get(position).getUname());
        holder.txtprice.setText("+91 "+userModelArrayList.get(position).getMo());
        Glide.with(context)
                .load(userModelArrayList.get(position).getUrl())
                .into(holder.imgprdt);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
               if (isAdmin){
                   Intent intent=new Intent(context, User_option.class);
                   intent.putExtra("mo",userModelArrayList.get(position).getMo());
                   intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);
               }
               else {
                   Intent intent=new Intent(context,MyProfile.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("mo",userModelArrayList.get(position).getMo());
                   context.startActivity(intent);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    @NonNull
    @Override
    public RcuserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_ecom_tb, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtpname, txtprice;
        ImageView imgprdt;
        CardView cd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtpname = itemView.findViewById(R.id.txtpname);
            txtprice = itemView.findViewById(R.id.txtprice);
            imgprdt = itemView.findViewById(R.id.imgprdt);
            cd = itemView.findViewById(R.id.cdlyt);
        }
    }
}
