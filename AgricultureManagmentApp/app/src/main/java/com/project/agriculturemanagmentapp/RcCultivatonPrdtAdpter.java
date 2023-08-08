package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                //  context.startActivity(intent);
                View view=LayoutInflater.from(context).inflate(R.layout.activity_show_cultivation_product,null,false);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(view);
                {
                    ImageButton btnwhatsapp,btncall;
                    TextView txtcategory,txtpname,txtspecie,txtqty,txtprc,txtpayment,txtstate,txttehsil,txtdistrict,txtvillage,txtdes,txtuname,txtdate;
                    String key;
                    ImageView imgprdt,prfpc;
                    ClsCultivationProductModel clsCultivationProductModel;
                    btnwhatsapp=view.findViewById(R.id.btnwhatsapp);
                    btncall=view.findViewById(R.id.btncall);
                    txtcategory=view.findViewById(R.id.txtpcategory);
                    txtpname=view.findViewById(R.id.txtpname);
                    txtspecie=view.findViewById(R.id.txtspeice);
                    txtqty=view.findViewById(R.id.txtpqty);
                    txtprc=view.findViewById(R.id.txtprc);
                    txtpayment=view.findViewById(R.id.txtpayable);
                    imgprdt=view.findViewById(R.id.imgprdt);
                    txtuname=view.findViewById(R.id.txtuname);
                    txtdate=view.findViewById(R.id.txtdate);
                    txtstate=view.findViewById(R.id.txtstate);
                    txttehsil=view.findViewById(R.id.txttehsil);
                    txtdistrict=view.findViewById(R.id.txtdistrict);
                    txtvillage=view.findViewById(R.id.txtVillage);
                    txtdes=view.findViewById(R.id.txtdes);
                    prfpc=view.findViewById(R.id.profilepc);
                    btnwhatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mo="+91"+model.getMo();
                            String msg="Hello "+model.getUname()+","+context.getResources().getString(R.string.Interest2)+" "+model.getPname();
                            String url = "https://api.whatsapp.com/send?phone="+mo+"&text="+msg;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            context.startActivity(i);
                        }
                    });
                    btncall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mo="+91"+model.getMo();
                            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mo));
                            context.startActivity(intent);
                        }
                    });
                    Glide.with(context)
                            .load(model.getImg())
                            .into(imgprdt);
                    Glide.with(context)
                            .load(model.getPrfpc())
                            .circleCrop()
                            .into(prfpc);
                    txtcategory.setText(model.getCategory());
                    txtpname.setText(model.getPname());
                    txtspecie.setText(model.getSpecie());
                    txtqty.setText(model.getQty());
                    txtprc.setText(model.getPrice());
                    txtpayment.setText(model.getPayment());
                    txtstate.setText(model.getState());
                    txttehsil.setText(model.getTehsil());
                    txtdistrict.setText(model.getDistrict());
                    txtvillage.setText(model.getVillage());
                    txtdes.setText(model.getDes());
                    txtuname.setText(model.getUname());
                    txtdate.setText(model.getDate());
                }
                bottomSheetDialog.show();
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
