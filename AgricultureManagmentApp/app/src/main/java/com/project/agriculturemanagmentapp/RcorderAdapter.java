package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcorderAdapter extends RecyclerView.Adapter<RcorderAdapter.ViewHolder> {
    Context context;
    ArrayList<clsOrderModel> orderModelArrayList;
    boolean isAdmin;
    Boolean isCancelled;
    public RcorderAdapter(Context context, ArrayList<clsOrderModel> orderModelArrayList, boolean isAdmin,Boolean isCancelled) {
        this.context = context;
        this.orderModelArrayList = orderModelArrayList;
        this.isAdmin = isAdmin;
        this.isCancelled=isCancelled;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtpname.setText(orderModelArrayList.get(position).clsEcommModel.getPname());
        holder.txtprice.setText(orderModelArrayList.get(position).clsEcommModel.getPrice());
        Glide.with(context)
                .load(orderModelArrayList.get(position).clsEcommModel.getImg())
                .into(holder.imgprdt);

        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ConfirmOrder.class);
                intent.putExtra("key",orderModelArrayList.get(position).getKey());
                intent.putExtra("IsOrder",true);
                intent.putExtra("IsAdmin",isAdmin);
                intent.putExtra("isCancelled",isCancelled);
                context.startActivity(intent);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                View view=LayoutInflater.from(context).inflate(R.layout.lyt_view_order_sheet,null,false);
                TextView txtpname,txtqty,txtprice,txttpayment,txtrec,txtspe,txtdes,txtmo,txtadd;
                ImageView imgprgt;
                AppCompatButton btncancelorder;
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
                        .load(orderModelArrayList.get(position).getClsEcommModel().getImg())
                        .into(imgprgt);
                txtpname.setText(orderModelArrayList.get(position).getClsEcommModel().getPname());
                txtqty.setText(orderModelArrayList.get(position).getClsEcommModel().getQty());
                txtprice.setText(orderModelArrayList.get(position).getClsEcommModel().getPrice());
                float a= Float.parseFloat(orderModelArrayList.get(position).clsEcommModel.getPrice());
                float b= Float.parseFloat(orderModelArrayList.get(position).getClsEcommModel().getQty());
                txttpayment.setText(a*b+"");
                txtrec.setText(orderModelArrayList.get(position).getClsEcommModel().getRecomm());
                txtspe.setText(orderModelArrayList.get(position).getClsEcommModel().getDpec());
                txtdes.setText(orderModelArrayList.get(position).getClsEcommModel().getDes());
                txtmo.setText(orderModelArrayList.get(position).getMo());
                txtadd.setText(orderModelArrayList.get(position).getAddress());
                bottomSheetDialog.setContentView(view);
             //   bottomSheetDialog.show();
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

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_ecom_tb, parent, false);
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
