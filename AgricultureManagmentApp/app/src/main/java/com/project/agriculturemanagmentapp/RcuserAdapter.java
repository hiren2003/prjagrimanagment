package com.project.agriculturemanagmentapp;

import android.app.Activity;
import android.app.ActivityOptions;
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
    boolean gotoChat;

    public RcuserAdapter(Context context, ArrayList<clsUserModel> userModelArrayList, boolean isAdmin, boolean gotoChat) {
        this.context = context;
        this.userModelArrayList = userModelArrayList;
        this.isAdmin = isAdmin;
        this.gotoChat = gotoChat;
    }

    public void updateList(ArrayList<clsUserModel> updatedList){
        this.userModelArrayList=updatedList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtname.setText(userModelArrayList.get(position).getUname());
        Glide.with(context)
                .load(userModelArrayList.get(position).getUrl())
                .circleCrop()
                .into(holder.imgprdt);
        holder.imgprdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gotoChat){
                    Intent intent=new Intent(context, chat.class);
                    intent.putExtra("rmo",userModelArrayList.get(position).getMo());
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
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
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname;
        ImageView imgprdt;
        CardView cd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgprdt = itemView.findViewById(R.id.imgprfpc);
            txtname=itemView.findViewById(R.id.txtname);
        }
    }
}
