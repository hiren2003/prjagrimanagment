package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcAiConversationAdapter extends RecyclerView.Adapter<RcAiConversationAdapter.ViewHolder> {
    ArrayList<clsaiconversation> arrayList;
    Context context;

    public RcAiConversationAdapter(ArrayList<clsaiconversation> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RcAiConversationAdapter.ViewHolder holder, int position) {
        SharedPreferences sharedPreferences= context.getSharedPreferences("data",Context.MODE_PRIVATE);
        if (!arrayList.get(position).isai){
            Glide.with(context)
                    .load(sharedPreferences.getString("url","none"))
                    .circleCrop()
                    .into(holder.profpc);
        }
       if (arrayList.get(position).getImguri()!=null){
           holder.imgbitmap.setVisibility(View.VISIBLE);
           holder.imgbitmap.setImageURI(arrayList.get(position).getImguri());
       }
       if(arrayList.get(position).getQuery().trim()!=""){
           holder.txtquery.setText(arrayList.get(position).getQuery());
       }
    }

    @NonNull
    @Override
    public RcAiConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.lyt_ai_conversation_tabs,parent,false);
       ViewHolder viewHolder= new ViewHolder(view);
       return  viewHolder;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgbitmap,profpc;
        TextView txtquery;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgbitmap=itemView.findViewById(R.id.imgbitmap);
            txtquery=itemView.findViewById(R.id.txtquery);
            profpc=itemView.findViewById(R.id.imgprofpc);
        }
    }
}
