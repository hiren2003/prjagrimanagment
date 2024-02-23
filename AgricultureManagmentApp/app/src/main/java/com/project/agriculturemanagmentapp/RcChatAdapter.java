package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class RcChatAdapter extends RecyclerView.Adapter<RcChatAdapter.ViewHolder> {
    Context context;
    ArrayList<clsChatModel> chatModelArrayList;

    public RcChatAdapter(Context context, ArrayList<clsChatModel> chatModelArrayList) {
        this.context = context;
        this.chatModelArrayList = chatModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_ai_conversation_tabs,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SharedPreferences sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String mo=sharedPreferences.getString("mo","1234567890");
        if(chatModelArrayList.get(position).getSmo().equals(mo)){
            holder.rvright.setVisibility(View.VISIBLE);
            holder.rvleft.setVisibility(View.GONE);
            holder.txtdate2.setText(chatModelArrayList.get(position).getDate());
            holder.txttime2.setText(chatModelArrayList.get(position).getTime());
            if (chatModelArrayList.get(position).getImg().equals("")){
                holder.txtquery2.setText(chatModelArrayList.get(position).getMsg());
            }
            else{
                holder.imgbitmap2.setVisibility(View.VISIBLE);
                holder.txtdate.setText(chatModelArrayList.get(position).getDate());
                holder.txttime.setText(chatModelArrayList.get(position).getTime());
                holder.txtquery2.setText(chatModelArrayList.get(position).getMsg());
                Glide.with(context)
                        .load(chatModelArrayList.get(position).getImg())
                        .into(holder.imgbitmap2);
            }
        }else{
            if (chatModelArrayList.get(position).getImg().equals("")){
                holder.txtquery.setText(chatModelArrayList.get(position).getMsg());
            }
            else{
                holder.txtquery.setText(chatModelArrayList.get(position).getMsg());
                holder.imgbitmap.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(chatModelArrayList.get(position).getImg())
                        .into(holder.imgbitmap);
            }
        }
        FirebaseDatabase.getInstance().getReference().child("Users_List").child(chatModelArrayList.get(position).getSmo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsUserModel userModel=snapshot.getValue(clsUserModel.class);
                if(chatModelArrayList.get(position).getSmo().equals(mo)) {
                    Glide.with(context)
                            .load(userModel.getUrl())
                            .circleCrop()
                            .into(holder.profpc2);
                }
                else{
                    Glide.with(context)
                            .load(userModel.getUrl())
                            .circleCrop()
                            .into(holder.profpc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.rvright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_msg_info);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                TextView txtmsg=dg.findViewById(R.id.txtmsg);
                TextView txtdate=dg.findViewById(R.id.txtdate);
                TextView txttime=dg.findViewById(R.id.txttime);
                ImageView img=dg.findViewById(R.id.img);
                txtmsg.setText(chatModelArrayList.get(position).getMsg());
                txtdate.setText(chatModelArrayList.get(position).getDate());
                txttime.setText(chatModelArrayList.get(position).getTime());
                Glide.with(context)
                        .load(chatModelArrayList.get(position).getImg())
                        .into(img);
                dg.show();
            }
        });
        holder.rvleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_msg_info);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                TextView txtmsg=dg.findViewById(R.id.txtmsg);
                TextView txtdate=dg.findViewById(R.id.txtdate);
                TextView txttime=dg.findViewById(R.id.txttime);
                ImageView img=dg.findViewById(R.id.img);
                txtmsg.setText(chatModelArrayList.get(position).getMsg());
                txtdate.setText(chatModelArrayList.get(position).getDate());
                txttime.setText(chatModelArrayList.get(position).getTime());
                Glide.with(context)
                        .load(chatModelArrayList.get(position).getImg())
                        .into(img);
                dg.show();
            }
        });
        holder.rvright.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dg=new Dialog(context);
                dg.setContentView(R.layout.lyt_dlt_msg);
                dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                TextView txtdforeveryone=dg.findViewById(R.id.txtdforeveryone);
                txtdforeveryone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("User").child(chatModelArrayList.get(position).getSmo()).child("Chats").child(chatModelArrayList.get(position).getRmo()).child(chatModelArrayList.get(position).getKey()).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("User").child(chatModelArrayList.get(position).getRmo()).child("Chats").child(chatModelArrayList.get(position).getSmo()).child(chatModelArrayList.get(position).getKey()).removeValue();
                        FirebaseStorage.getInstance().getReference().child("Chats").child(chatModelArrayList.get(position).getSmo()).child(chatModelArrayList.get(position).getKey()).delete();
                       dg.dismiss();
                    }
                });
                dg.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgbitmap,profpc,imgbitmap2,profpc2;
        TextView txtquery,txtquery2,txtdate,txttime,txtdate2,txttime2;
        RelativeLayout rvright,rvleft;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgbitmap=itemView.findViewById(R.id.imgbitmap);
            txtquery=itemView.findViewById(R.id.txtquery);
            profpc=itemView.findViewById(R.id.imgprofpc);
            rvright=itemView.findViewById(R.id.rvright);
            imgbitmap2=itemView.findViewById(R.id.imgbitmap2);
            txtquery2=itemView.findViewById(R.id.txtquery2);
            profpc2=itemView.findViewById(R.id.imgprofpc2);
            rvleft=itemView.findViewById(R.id.rvleft);
            txtdate=itemView.findViewById(R.id.txtdate);
            txttime=itemView.findViewById(R.id.txttime);
            txtdate2=itemView.findViewById(R.id.txtdate2);
            txttime2=itemView.findViewById(R.id.txttime2);
        }
    }
}
