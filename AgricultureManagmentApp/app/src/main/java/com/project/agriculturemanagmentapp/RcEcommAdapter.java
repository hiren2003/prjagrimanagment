package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcEcommAdapter extends RecyclerView.Adapter<RcEcommAdapter.ViewHolder> {

    Context context;

    int btn;
    ArrayList<clsEcommModel> ecommModelArrayList;

    public RcEcommAdapter(Context context, int btn, ArrayList<clsEcommModel> ecommModelArrayList) {
        this.context = context;
        this.btn = btn;
        this.ecommModelArrayList = ecommModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (ecommModelArrayList.get(position).getDiscount()==0.0){
            holder.txtdiscount.setVisibility(View.GONE);
        }
        //Animation
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        //Setting Value For Each Grid Item
        holder.txtpname.setText(ecommModelArrayList.get(position).getPname());
        holder.txtprice.setText("₹"+ecommModelArrayList.get(position).getPrice());
        holder.txtdiscount.setText(ecommModelArrayList.get(position).getDiscount()+"% "+context.getString(R.string.less));
        Glide.with(context)
                .load(ecommModelArrayList.get(position).getImg())
                .into(holder.imgprdt);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Detailed Description in Another Activity
                context.startActivity(new Intent(context, show_ecom_prdt.class).putExtra("key", ecommModelArrayList.get(position).getKey()).putExtra("btn",btn));
            }
        });
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(btn==4){
                    Dialog dg=new Dialog(context);
                    dg.setContentView(R.layout.lyt_delete_dg);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    AppCompatButton yes = dg.findViewById(R.id.yes);
                    ImageView no = dg.findViewById(R.id.no);
                    dg.show();
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
                            FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(ecommModelArrayList.get(position).getKey()).removeValue();
                        }
                    });
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public RcEcommAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_animal_tab, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return ecommModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtpname, txtprice,txtqty,txtdiscount;
        ShapeableImageView imgprdt;
        LinearLayout cd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtpname = itemView.findViewById(R.id.txtpname);
            txtprice = itemView.findViewById(R.id.txtprice);
            imgprdt = itemView.findViewById(R.id.imgprdt);
            cd = itemView.findViewById(R.id.cdlyt);
            txtdiscount=itemView.findViewById(R.id.txtdiscount);
        }
    }
}
