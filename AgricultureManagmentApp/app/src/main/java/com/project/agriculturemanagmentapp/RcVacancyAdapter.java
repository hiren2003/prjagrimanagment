package com.project.agriculturemanagmentapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcVacancyAdapter extends FirebaseRecyclerAdapter<clsVacancyModel,RcVacancyAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean isMy;
    public RcVacancyAdapter(@NonNull FirebaseRecyclerOptions<clsVacancyModel> options,Context context,boolean isMy) {
        super(options);
        this.context=context;
        this.isMy=isMy;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcVacancyAdapter.ViewHolder holder, int position, @NonNull clsVacancyModel model) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        if (isMy){
            holder.imgdlt.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgcall.setVisibility(View.VISIBLE);
            holder.imgwhat.setVisibility(View.VISIBLE);
        }
        holder.txtwtype.setText(model.getWtype());
        holder.txtdur.setText(model.getWdur()+context.getResources().getString(R.string.Month));
        holder.txtworktype.setText(model.getTwork());
        holder.txtvlg.setText(model.getVillage()+","+model.getTehsil()+","+model.getDistrict()+","+model.getState()+".");
        holder.txtdate.setText(context.getResources().getString(R.string.date)+" : "+model.getDate());
        holder.txtdes.setText(model.getDes());
        holder.txtWage.setText("₹"+model.getEamt());
        holder.txtuname.setText(model.getUname());
        Glide.with(context)
                .load(model.getPrfpc())
                .circleCrop()
                .into(holder.prfpc);
        holder.imgwhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo="+91"+model.getOcan();
                String msg="Hello "+model.getUname()+","+context.getResources().getString(R.string.Interest);
                String url = "https://api.whatsapp.com/send?phone="+mo+"&text="+msg;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
        holder.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo="+91"+model.getOcan();
                Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mo));
                context.startActivity(intent);
            }
        });
        holder.imgdlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo = context.getSharedPreferences("data",Context.MODE_PRIVATE).getString("mo", "1234567890");
                FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").child(model.getKey()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Labour_Vacancy").child(model.getKey()).removeValue();
            }
        });

    }

    @NonNull
    @Override
    public RcVacancyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lytlbrvacancy,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtoname,txtwtype,txtdur,txtworktype,txtvlg,txtdes,txtdate,txtWage;
        ImageView imgwhat,imgcall,prfpc,imgdlt;
        TextView txtuname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtwtype=itemView.findViewById(R.id.txtwtype);
            txtdur=itemView.findViewById(R.id.txtduration);
            txtworktype=itemView.findViewById(R.id.worktype);
            txtvlg=itemView.findViewById(R.id.txtvlg);
            txtdes=itemView.findViewById(R.id.txtdes);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtWage=itemView.findViewById(R.id.txtWages);
            imgwhat=itemView.findViewById(R.id.imgwhatsapp);
            imgcall=itemView.findViewById(R.id.imgcall);
            prfpc=itemView.findViewById(R.id.profilepc);
            txtuname=itemView.findViewById(R.id.txtuname);
            imgdlt=itemView.findViewById(R.id.imgdlt);
        }
    }
}
