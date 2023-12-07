package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RcnewsAdapter extends RecyclerView.Adapter<RcnewsAdapter.ViewHolder> {
    Context context;
    boolean isAdmin;
    ArrayList<clsNewsModel> newsModelArrayList;

    public RcnewsAdapter(Context context, boolean isAdmin, ArrayList<clsNewsModel> newsModelArrayList) {
        this.context = context;
        this.isAdmin = isAdmin;
        this.newsModelArrayList = newsModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Animation
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        //Set Data For Each item
        holder.txtheadline.setText(newsModelArrayList.get(position).getHeadline());
        holder.txtdes.setText(newsModelArrayList.get(position).getDescription());
        Glide.with(context)
                .load(newsModelArrayList.get(position).getImg())
                .into(holder.imageView);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirect To Url
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(newsModelArrayList.get(position).link));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Delete the News-Admin Only
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lytdelete);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                Button yes=dg.findViewById(R.id.yes);
                Button no=dg.findViewById(R.id.no);
                if (isAdmin){
                    dg.show();
                }
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dont Delete
                        dg.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Delete
                        dg.dismiss();
                        FirebaseDatabase.getInstance().getReference().child("news").child(newsModelArrayList.get(position).getKey()).removeValue();
                    }
                });
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }

    @NonNull
    @Override
    public RcnewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytnews, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cd;
        ImageView imageView;
        TextView txtheadline,txtdes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cd=itemView.findViewById(R.id.cd);
            imageView=itemView.findViewById(R.id.imageview2);
            txtdes=itemView.findViewById(R.id.txtdes);
            txtheadline=itemView.findViewById(R.id.txtheadline);
        }
    }
}
