package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcorderAdapter extends FirebaseRecyclerAdapter<clsOrderModel,RcorderAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public RcorderAdapter(@NonNull FirebaseRecyclerOptions<clsOrderModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull clsOrderModel model) {
        holder.txtpname.setText(model.clsEcommModel.getPname());
        holder.txtprice.setText(model.clsEcommModel.getPrice());
        Glide.with(context)
                .load(model.clsEcommModel.getImg())
                .into(holder.imgprdt);

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                View view=LayoutInflater.from(context).inflate(R.layout.lytordersheet,null,false);
                TextView txtpname,txtqty,txtprice,txttpayment,txtrec,txtspe,txtdes,txtmo,txtadd;
                ImageView imgprgt;
                imgprgt=view.findViewById(R.id.imgprdt);
                txtpname=view.findViewById(R.id.txtpname);
                txtqty=view.findViewById(R.id.txtqty);
                txtprice=view.findViewById(R.id.txtprc);
                txttpayment=view.findViewById(R.id.txttpay);
                txtrec=view.findViewById(R.id.txtrecomm);
                txtspe=view.findViewById(R.id.txtspeci);
                txtdes=view.findViewById(R.id.txtdesc);
                txtmo=view.findViewById(R.id.txtmo);
                txtadd=view.findViewById(R.id.txtadd);
                Glide.with(context)
                        .load(model.getClsEcommModel().getImg())
                        .into(imgprgt);
                txtpname.setText(model.getClsEcommModel().getPname());
                txtqty.setText(model.getClsEcommModel().getQty());
                txtprice.setText(model.getClsEcommModel().getPrice());
                float a= Float.parseFloat(model.clsEcommModel.getPrice());
                float b= Float.parseFloat(model.getClsEcommModel().getQty());
                txttpayment.setText(a*b+"");
                txtrec.setText(model.getClsEcommModel().getRecomm());
                txtspe.setText(model.getClsEcommModel().getDpec());
                txtdes.setText(model.getClsEcommModel().getDes());
                txtmo.setText(model.getMo());
                txtadd.setText(model.getAddress());
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                FrameLayout btncancel=view.findViewById(R.id.btncancel);
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytecom, parent, false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;    }

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
