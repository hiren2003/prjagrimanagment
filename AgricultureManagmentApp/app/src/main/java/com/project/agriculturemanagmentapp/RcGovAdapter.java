package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.io.File;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcGovAdapter extends FirebaseRecyclerAdapter<clsgovmodel,RcGovAdapter.ViewHolder> {
Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RcGovAdapter(@NonNull FirebaseRecyclerOptions<clsgovmodel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcGovAdapter.ViewHolder holder, int position, @NonNull clsgovmodel model) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        Glide.with(context)
                .load(model.url)
                .into(holder.imageView);
        holder.txtdes.setText(model.des);
        holder.txtstate.setText(model.state);
        holder.txttitle.setText(model.name);
        holder.cdlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(model.lnk)));
            }
        });
    }


    @NonNull
    @Override
    public RcGovAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytgov,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttitle,txtstate,txtdes;
        ImageView imageView;
        CardView cdlyt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle=itemView.findViewById(R.id.txttitle);
            txtstate=itemView.findViewById(R.id.txtstate);
            txtdes=itemView.findViewById(R.id.txtdes);
            imageView=itemView.findViewById(R.id.imggov);
            cdlyt=itemView.findViewById(R.id.cdlyt);
        }
    }
}
