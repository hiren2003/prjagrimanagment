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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RcuserAdapter extends FirebaseRecyclerAdapter<clsUserModel,RcuserAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public RcuserAdapter(@NonNull FirebaseRecyclerOptions<clsUserModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcuserAdapter.ViewHolder holder, int position, @NonNull clsUserModel model) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtpname.setText(model.getUname()); 
        holder.txtprice.setText("+91 "+model.getMo());
        Glide.with(context)
                .load(model.getUrl())
                .into(holder.imgprdt);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                Intent intent=new Intent(context, User_option.class);
                intent.putExtra("mo",model.getMo());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public RcuserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytecom, parent, false);
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
