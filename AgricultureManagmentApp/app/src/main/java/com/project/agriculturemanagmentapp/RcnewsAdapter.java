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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RcnewsAdapter extends FirebaseRecyclerAdapter<clsNewsModel,RcnewsAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean isAdmin;
    public RcnewsAdapter(@NonNull FirebaseRecyclerOptions<clsNewsModel> options,Context context,boolean isAdmin) {
        super(options);
        this.context=context;
        this.isAdmin=isAdmin;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcnewsAdapter.ViewHolder holder, int position, @NonNull clsNewsModel model) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtheadline.setText(model.getHeadline());
        holder.txtdes.setText(model.getDescription());
        Glide.with(context)
                .load(model.getImg())
                .into(holder.imageView);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(model.link));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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
                        dg.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dg.dismiss();
                        FirebaseDatabase.getInstance().getReference().child("news").child(model.getKey()).removeValue();
                    }
                });
                return false;
            }
        });
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