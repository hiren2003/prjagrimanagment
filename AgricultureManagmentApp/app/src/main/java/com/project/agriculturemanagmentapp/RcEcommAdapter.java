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
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcEcommAdapter extends FirebaseRecyclerAdapter<clsEcommModel, RcEcommAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;

    int btn;

    public RcEcommAdapter(@NonNull FirebaseRecyclerOptions<clsEcommModel> options, Context context,int btn) {
        super(options);
        this.context = context;
        this.btn=btn;

    }

    @Override
    protected void onBindViewHolder(@NonNull RcEcommAdapter.ViewHolder holder, int position, @NonNull clsEcommModel model) {
        holder.txtpname.setText(model.getPname());
        holder.txtprice.setText("₹"+model.getPrice());
        Glide.with(context)
                .load(model.getImg())
                .into(holder.imgprdt);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, show_ecom_prdt.class).putExtra("key", model.getKey()).putExtra("btn",btn));
            }
        });
    }

    @NonNull
    @Override
    public RcEcommAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytecom, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtpname, txtprice;
        ImageView imgprdt;
        CardView cd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtpname = itemView.findViewById(R.id.txtpname);
            txtprice = itemView.findViewById(R.id.txtprice);
            imgprdt = itemView.findViewById(R.id.imgprdt);
            cd = itemView.findViewById(R.id.cdlyt);
        }
    }
}
