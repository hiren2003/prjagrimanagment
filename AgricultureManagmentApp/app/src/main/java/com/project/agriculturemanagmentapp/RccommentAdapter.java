package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RccommentAdapter extends RecyclerView.Adapter<RccommentAdapter.ViewHolder> {
    Context context;
    String parent_key;
    ArrayList<clsCommentModel> clsCommentModelArrayList;

    public RccommentAdapter(Context context, String parent_key, ArrayList<clsCommentModel> clsCommentModelArrayList) {
        this.context = context;
        this.parent_key = parent_key;
        this.clsCommentModelArrayList = clsCommentModelArrayList;
    }



    @Override
    public int getItemCount() {
        return clsCommentModelArrayList.size();
    }


    @NonNull
    @Override
    public RccommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lytrccmt,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Setting Up Animation
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        // Decide - Show Comment Or Not
        SharedPreferences sharedPreferences= context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if(clsCommentModelArrayList.get(position).getMo().toString().equals(sharedPreferences.getString("mo", "1234567890").toString())){
            holder.btndelete.setVisibility(View.VISIBLE);
        }
        // Setting Comment Text
        holder.txtcomment.setText(clsCommentModelArrayList.get(position).getComment());

        //Setting Profile Picture And UserName
        FirebaseDatabase.getInstance().getReference().child("Users_List").child(clsCommentModelArrayList.get(position).getMo()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsUserModel clsUserModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                holder.txtuname.setText(clsUserModel.getUname());
                Glide.with(context)
                        .load(clsUserModel.getUrl())
                        .circleCrop()
                        .into(holder.prfpc);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Delete the Comment
        holder.btndelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(parent_key).child(clsCommentModelArrayList.get(position).getKey()).removeValue();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtuname,txtcomment;
        ImageView prfpc,btndelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuname=itemView.findViewById(R.id.uname);
            txtcomment=itemView.findViewById(R.id.txtcomment);
            prfpc=itemView.findViewById(R.id.prfpc);
            btndelete=itemView.findViewById(R.id.btndelete);
        }
    }
}
