package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcChatAdapter extends FirebaseRecyclerAdapter<clsChatModel,RcChatAdapter.ViewHolder> {
    Context context;

    public RcChatAdapter(@NonNull FirebaseRecyclerOptions<clsChatModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytaiconversation,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull clsChatModel model) {
        FirebaseDatabase.getInstance().getReference().child("Users_List").child(model.getSmo()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsUserModel clsUserModel = snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                Glide.with(context)
                        .load(clsUserModel.getUrl())
                        .circleCrop()
                        .into(holder.profpc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (model.getImg().equals("")){
            holder.txtquery.setText(model.getMsg());
        }
        else{
            holder.txtquery.setText(model.getMsg());
            Glide.with(context)
                    .load(model.getImg())
                    .circleCrop()
                    .into(holder.imgbitmap);
        }
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
