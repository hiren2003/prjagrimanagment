package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcImageGridAdapter extends RecyclerView.Adapter<RcImageGridAdapter.ViewHolder> {
    Context context;
    boolean isMyFeed;
    ArrayList<clsFeedModel> feedModelArrayList;

    public RcImageGridAdapter(Context context, boolean isMyFeed, ArrayList<clsFeedModel> feedModelArrayList) {
        this.context = context;
        this.isMyFeed = isMyFeed;
        this.feedModelArrayList = feedModelArrayList;
    }

    @Override
    public int getItemCount() {
        return feedModelArrayList.size();
    }

    @NonNull
    @Override
    public RcImageGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_img, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if (feedModelArrayList.get(position).getMediatype().equals("1")){
           holder.textView.setVisibility(View.GONE);
           Glide.with(context)
                   .load(feedModelArrayList.get(position).getPost())
                   .into(holder.imageView);
       }
       else{
           holder.imageView.setVisibility(View.GONE);
           holder.textView.setText(feedModelArrayList.get(position).getDes());
       }
        holder.rctab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Posts.class);
                intent.putExtra("mo",feedModelArrayList.get(position).getUmo());
                intent.putExtra("position",feedModelArrayList.size()-1-position);
                intent.putExtra("SelfAccount",isMyFeed);
                context.startActivity(intent);
            }
        });
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView  imageView;
        TextView textView;
        RelativeLayout rctab;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.txttweet);
            rctab=itemView.findViewById(R.id.gridtab);

        }
    }
}
