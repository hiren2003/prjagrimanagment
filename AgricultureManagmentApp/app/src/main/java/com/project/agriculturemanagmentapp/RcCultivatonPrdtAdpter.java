package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcCultivatonPrdtAdpter extends FirebaseRecyclerAdapter<ClsCultivationProductModel,RcCultivatonPrdtAdpter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public RcCultivatonPrdtAdpter(@NonNull FirebaseRecyclerOptions<ClsCultivationProductModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcCultivatonPrdtAdpter.ViewHolder holder, int position, @NonNull ClsCultivationProductModel model) {
        holder.txtprdt.setText(model.getPname());
        holder.txtprc.setText(context.getResources().getString(R.string.prc)+model.getPrice()+"/K.G.");
        holder.txtqty.setText(model.getQty()+" K.G.");
        Glide.with(context)
                .load(model.getImg())
                .into(holder.imgprdt);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(new Intent(context, show_CultivationProduct.class));
                intent.putExtra("key",model.getKey());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public RcCultivatonPrdtAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytcprdt,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtprdt,txtqty,txtprc;
        ImageView imgprdt;
        CardView cd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtprdt=itemView.findViewById(R.id.txtpname);
            txtqty=itemView.findViewById(R.id.txtqty);
            txtprc=itemView.findViewById(R.id.txtprc);
            imgprdt=itemView.findViewById(R.id.imgprdt);
            cd=itemView.findViewById(R.id.cdlyt);
        }
    }
}
