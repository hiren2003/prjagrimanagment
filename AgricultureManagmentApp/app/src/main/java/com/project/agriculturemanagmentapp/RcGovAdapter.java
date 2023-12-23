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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcGovAdapter extends RecyclerView.Adapter<RcGovAdapter.ViewHolder> {
Context context;
boolean isAdmin;
ArrayList<clsgovmodel> clsgovmodelArrayList;

    public RcGovAdapter(Context context, boolean isAdmin, ArrayList<clsgovmodel> clsgovmodelArrayList) {
        this.context = context;
        this.isAdmin = isAdmin;
        this.clsgovmodelArrayList = clsgovmodelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Animation
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        //Setting Up News Value
        Glide.with(context)
                .load(clsgovmodelArrayList.get(position).url)
                .into(holder.imageView);
        holder.txtdes.setText(clsgovmodelArrayList.get(position).des);
        holder.txtstate.setText(clsgovmodelArrayList.get(position).state);
        holder.txttitle.setText(clsgovmodelArrayList.get(position).name);
        holder.cdlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirecting to Url
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(clsgovmodelArrayList.get(position).lnk)));
            }
        });
        holder.cdlyt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //For Admin Only-Delete Only
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_delete_dg);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                AppCompatButton yes = dg.findViewById(R.id.yes);
                ImageView no = dg.findViewById(R.id.no);
                if (isAdmin){
                    dg.show();
                }
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //DO Not Delete
                        dg.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Delete News
                        dg.dismiss();
                        FirebaseDatabase.getInstance().getReference().child("Gov_scheme").child(clsgovmodelArrayList.get(position).getKey()).removeValue();
                    }
                });
                return false;
            }
        });
    }

    @NonNull
    @Override
    public RcGovAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_gov_scheme,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return clsgovmodelArrayList.size();
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
