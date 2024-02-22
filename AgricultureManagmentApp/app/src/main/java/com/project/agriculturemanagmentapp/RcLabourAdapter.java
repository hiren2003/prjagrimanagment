package com.project.agriculturemanagmentapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcLabourAdapter extends RecyclerView.Adapter<RcLabourAdapter.ViewHolder> {
    Context context;
    ArrayList<clsLaborModel> laborModelArrayList;

    public RcLabourAdapter(Context context, ArrayList<clsLaborModel> laborModelArrayList) {
        this.context = context;
        this.laborModelArrayList = laborModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtlname.setText(laborModelArrayList.get(position).getLname());
        holder.txtlmo.setText(laborModelArrayList.get(position).getLmo());
        holder.txtlloc.setText(laborModelArrayList.get(position).getLloc());
        holder.txtldec.setText(laborModelArrayList.get(position).getLdes());
        holder.txtldate.setText(laborModelArrayList.get(position).getLdate());
        holder.txtlwages.setText("₹ "+laborModelArrayList.get(position).getLwages());
        holder.rv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_delete_dg);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                AppCompatButton yes = dg.findViewById(R.id.yes);
                ImageView no = dg.findViewById(R.id.no);
                dg.setCancelable(false);
                dg.show();
                // Do Not Delete
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dg.dismiss();
                    }
                });
                //Delete the Post
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences=context.getSharedPreferences("data", Context.MODE_PRIVATE);
                        String msg=context.getResources().getString(R.string.msg2)+" "+laborModelArrayList.get(position).getLdate()+" "+context.getResources().getString(R.string.msg3);
                        FirebaseDatabase.getInstance().getReference().child("Labor_data").child(sharedPreferences.getString("mo","1234567890")).child(laborModelArrayList.get(position).getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(laborModelArrayList.get(position).getLmo(),null,msg,null,null);
                                dg.dismiss();
                            }
                        });
                        dg.dismiss();

                    }
                });
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return laborModelArrayList.size();
    }

    @NonNull
    @Override
    public RcLabourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_labor,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtlname,txtlmo,txtlloc,txtldec,txtldate,txtlwages;
        CardView rv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtlname=itemView.findViewById(R.id.txtlname);
            txtlmo=itemView.findViewById(R.id.txtmo);
            txtlloc=itemView.findViewById(R.id.txtlo);
            txtldec=itemView.findViewById(R.id.txtdes);
            txtldate=itemView.findViewById(R.id.txtdate);
            txtlwages=itemView.findViewById(R.id.txtWages);
            rv=itemView.findViewById(R.id.rv);
        }
    }
}
