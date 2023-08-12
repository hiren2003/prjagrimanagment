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
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RccommentAdapter extends FirebaseRecyclerAdapter<clsCommentModel,RccommentAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    String parent_key;
    public RccommentAdapter(@NonNull FirebaseRecyclerOptions<clsCommentModel> options,Context context,String parent_key) {
        super(options);
        this.context=context;
        this.parent_key=parent_key;
    }

    @Override
    protected void onBindViewHolder(@NonNull RccommentAdapter.ViewHolder holder, int position, @NonNull clsCommentModel model) {
     holder.txtuname.setText(model.getUname());
        holder.txtcomment.setText(model.getComment());
        Glide.with(context)
                .load(model.getPrfpc())
                .circleCrop()
                .into(holder.prfpc);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Feed_Comments").child(parent_key).child(model.getKey()).removeValue();
            }
        });
    }

    @NonNull
    @Override
    public RccommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lytrccmt,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtuname,txtcomment;
        ImageView prfpc,btndelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuname=itemView.findViewById(R.id.uname);
            txtcomment=itemView.findViewById(R.id.txtcomment);
            prfpc=itemView.findViewById(R.id.prfpc);
            btndelete=itemView.findViewById(R.id.btndelete);
        }
    }
}
