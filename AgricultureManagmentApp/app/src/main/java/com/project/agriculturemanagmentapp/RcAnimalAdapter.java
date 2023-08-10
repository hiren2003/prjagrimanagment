package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcAnimalAdapter extends FirebaseRecyclerAdapter<clsAnimalModel,RcAnimalAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     *
     */
    Context context;
    public RcAnimalAdapter(@NonNull FirebaseRecyclerOptions<clsAnimalModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcAnimalAdapter.ViewHolder holder, int position, @NonNull clsAnimalModel model) {
        Glide.with(context)
                .load(model.img)
                .into(holder.imgprdt);
        holder.txtprdt.setText(model.type);
        holder.txtqty.setText(model.spiece);
        holder.txtprc.setText(model.price);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                View view=LayoutInflater.from(context).inflate(R.layout.lytanimalsheet,null,false);
                bottomSheetDialog.setContentView(view);
                ImageView imgprfpc=view.findViewById(R.id.profilepc);
                ImageView imgprdt=view.findViewById(R.id.imgprdt);
                TextView txtuname=view.findViewById(R.id.txtuname);
                TextView txtdate=view.findViewById(R.id.txtdate);
                TextView txttype=view.findViewById(R.id.txttype);
                TextView txtspeice=view.findViewById(R.id.txtspeice);
                TextView txtage=view.findViewById(R.id.txtage);
                TextView txtweight=view.findViewById(R.id.txtweight);
                TextView txtproduction=view.findViewById(R.id.txtproduction);
                TextView txtprice=view.findViewById(R.id.txtprice);
                TextView txtdes=view.findViewById(R.id.txtdes);
                TextView txtcity=view.findViewById(R.id.txtvlg);
                FrameLayout btncancel=view.findViewById(R.id.btncancel);
                ImageButton btnwhatsapp=view.findViewById(R.id.btnwhatsapp);
                ImageButton btncall=view.findViewById(R.id.btncall);
                Glide.with(context)
                        .load(model.getImg())
                        .into(imgprdt);
                Glide.with(context)
                        .load(model.getPrfpc())
                        .circleCrop()
                        .into(imgprfpc);
                txtuname.setText(model.getUname());
                txtdate.setText(model.getDate());
                txttype.setText(model.getType());
                txtspeice.setText(model.getSpiece());
                txtage.setText(model.getAgeyear()+context.getResources().getString(R.string.year)+model.getAgemonth()+context.getResources().getString(R.string.month));
                txtweight.setText(model.getWeight()+context.getResources().getString(R.string.KiloGram2));
                txtprice.setText("₹"+model.getPrice());
                txtproduction.setText(model.getMproduction());
                txtdes.setText(model.getDes());
                txtcity.setText(model.getVillage()+","+model.getTehsil()+","+model.getDistrict()+","+model.getState());
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                    }
                });
                btnwhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mo="+91"+model.getMo();
                        String msg="Hello "+model.getUname()+","+context.getResources().getString(R.string.Interest2)+" "+model.getType();
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
                bottomSheetDialog.show();
            }
        });
    }

    @NonNull
    @Override
    public RcAnimalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytcprdt,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;    }
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
