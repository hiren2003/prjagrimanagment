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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class RcVacancyAdapter extends RecyclerView.Adapter<RcVacancyAdapter.ViewHolder> {
    Context context;
    boolean isMy;
  ArrayList<clsVacancyModel> vacancyModelArrayList ;

    public RcVacancyAdapter(Context context, boolean isMy, ArrayList<clsVacancyModel> vacancyModelArrayList) {
        this.context = context;
        this.isMy = isMy;
        this.vacancyModelArrayList = vacancyModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        if (isMy){
            holder.imgdlt.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgcall.setVisibility(View.VISIBLE);
            holder.imgwhat.setVisibility(View.VISIBLE);
            holder.imgdlt.setVisibility(View.GONE);
        }
        holder.txtwtype.setText(vacancyModelArrayList.get(position).getWtype());
        holder.txtdur.setText(vacancyModelArrayList.get(position).getWdur()+context.getResources().getString(R.string.Month));
        holder.txtworktype.setText(vacancyModelArrayList.get(position).getTwork());
        holder.txtvlg.setText(vacancyModelArrayList.get(position).getVillage()+","+vacancyModelArrayList.get(position).getTehsil()+","+vacancyModelArrayList.get(position).getDistrict()+","+vacancyModelArrayList.get(position).getState()+".");
        holder.txtdate.setText(context.getResources().getString(R.string.date)+" : "+vacancyModelArrayList.get(position).getDate());
        holder.txtdes.setText(vacancyModelArrayList.get(position).getDes());
        holder.txtWage.setText("₹"+vacancyModelArrayList.get(position).getEamt());
        holder.txtoname.setText(vacancyModelArrayList.get(position).getOname());
        FirebaseDatabase.getInstance().getReference().child("Users_List").child(vacancyModelArrayList.get(position).getUmo()).addValueEventListener(new ValueEventListener() {
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
        holder.imgwhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo="+91"+vacancyModelArrayList.get(position).getOcan();
                String msg="Hello "+vacancyModelArrayList.get(position).getOname()+","+context.getResources().getString(R.string.Interest);
                String url = "https://api.whatsapp.com/send?phone="+mo+"&text="+msg;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
        holder.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo="+91"+vacancyModelArrayList.get(position).getOcan();
                Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mo));
                context.startActivity(intent);
            }
        });
        holder.imgdlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_delete_dg);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                AppCompatButton yes = dg.findViewById(R.id.yes);
                ImageView no = dg.findViewById(R.id.no);
                dg.setCancelable(false);
                dg.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mo = context.getSharedPreferences("data",Context.MODE_PRIVATE).getString("mo", "1234567890");
                        FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").child(vacancyModelArrayList.get(position).getKey()).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("Labour_Vacancy").child(vacancyModelArrayList.get(position).getKey()).removeValue();
                        dg.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dg.dismiss();
                    }
                });

            }
        });
        holder.llprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,MyProfile.class).putExtra("mo",vacancyModelArrayList.get(position).getUmo()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vacancyModelArrayList.size();
    }

    @NonNull
    @Override
    public RcVacancyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lyt_vacancy,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtoname,txtwtype,txtdur,txtworktype,txtvlg,txtdes,txtdate,txtWage;
        ImageView imgwhat,imgcall,prfpc,imgdlt;
        TextView txtuname;
        LinearLayout llprofile;
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
            txtoname=itemView.findViewById(R.id.txtoname);
            prfpc=itemView.findViewById(R.id.profilepc);
            txtuname=itemView.findViewById(R.id.txtuname);
            imgdlt=itemView.findViewById(R.id.imgdlt);
            llprofile=itemView.findViewById(R.id.llprofile);
        }
    }
}
