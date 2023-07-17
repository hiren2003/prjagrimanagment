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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcFeedAdapter extends FirebaseRecyclerAdapter<clsFeedModel,RcFeedAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public RcFeedAdapter(@NonNull FirebaseRecyclerOptions<clsFeedModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcFeedAdapter.ViewHolder holder, int position, @NonNull clsFeedModel model) {
        holder.txtuname.setText(model.getUname());
        holder.txtdate.setText(model.getDate());
        holder.txtdes.setText(model.getDes());
        Glide.with(context)
                .load(model.getPrfpc())
                .into(holder.prfpc);
        Glide.with(context)
                .load(model.getPost())
                .into(holder.imgpost);
    }
    @NonNull
    @Override
    public RcFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lytfeed,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView prfpc,imgpost;
        TextView txtuname,txtdes,txtdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prfpc=itemView.findViewById(R.id.profilepc);
            imgpost=itemView.findViewById(R.id.imgfeed);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtuname=itemView.findViewById(R.id.txtuname);
            txtdes=itemView.findViewById(R.id.txtdes);
        }
    }
}
