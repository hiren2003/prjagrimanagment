package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.RecursiveAction;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcToolsAccesoriesAdapter extends FirebaseRecyclerAdapter<clsToolsAccessoriesModel,RcToolsAccesoriesAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean isMyproduct;
    public RcToolsAccesoriesAdapter(@NonNull FirebaseRecyclerOptions<clsToolsAccessoriesModel> options, Context context,boolean isMyproduct) {
        super(options);
        this.context=context;
        this.isMyproduct=isMyproduct;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcToolsAccesoriesAdapter.ViewHolder holder, int position, @NonNull clsToolsAccessoriesModel model) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                View view2=LayoutInflater.from(context).inflate(R.layout.lyteditoption,null,false);
                LinearLayout btnupdate=view2.findViewById(R.id.lnupdate);
                LinearLayout btndelete=view2.findViewById(R.id.lndelete);
                bottomSheetDialog.setContentView(view2);

                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                        Dialog dg=new Dialog(context);
                        dg.setContentView(R.layout.lytdelete);
                        dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                        Button yes=dg.findViewById(R.id.yes);
                        Button no=dg.findViewById(R.id.no);
                        dg.show();
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dg.dismiss();
                            }
                        });
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseDatabase.getInstance().getReference().child("Tools&Accessories").child(model.getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Tools&Accessories").child(model.getKey()).removeValue();
                                dg.dismiss();

                            }
                        });
                    }
                });
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
                        View view2=LayoutInflater.from(context).inflate(R.layout.activity_add_tools_accesories,null,false);
                        bottomSheetDialog.setContentView(view2);
                        TextInputEditText edtpname,edtprc,edtmonth,edtstate, edtdistrict, edttehsil, edtvillage, edtdescription, edtmo, edtsellername;
                        Button btnchooseimg,btnsavedata;
                        Spinner spncat;
                        ImageView imgprdt;
                        imgprdt =view2.findViewById(R.id.imgprdt);
                        btnchooseimg =view2.findViewById(R.id.btnchooseimage);
                        edtprc = view2.findViewById(R.id.edtprc);
                        edtstate =view2.findViewById(R.id.edtstate);
                        edtdistrict =view2.findViewById(R.id.edtdist);
                        edtmonth=view2.findViewById(R.id.edtMonth);
                        edttehsil =view2.findViewById(R.id.edttehsil);
                        edtsellername =view2.findViewById(R.id.edtsellername);
                        edtvillage =view2.findViewById(R.id.edtvlg);
                        btnsavedata =view2.findViewById(R.id.btnsavedata);
                        edtdescription =view2.findViewById(R.id.edtdes);
                        edtmo =view2.findViewById(R.id.edtmo);
                        spncat=view2.findViewById(R.id.category);
                        edtpname=view2.findViewById(R.id.edtpname);
                        btnchooseimg.setVisibility(View.GONE);
                        spncat.setVisibility(View.GONE);
                        imgprdt.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                        .load(model.getImg())
                                                .into(imgprdt);
                        edtprc.setText(model.getPrice());
                        edtstate.setText(model.getState());
                        edttehsil.setText(model.getTehsil());
                        edtdistrict.setText(model.getDistrict());
                        edtsellername.setText(model.getSname());
                        edtvillage.setText(model.getVillage());
                        edtdescription.setText(model.getDesc());
                        edtmo.setText(model.getMo());
                        edtmonth.setText(model.getMonth());
                        edtpname.setText(model.getPname());
                        btnsavedata.setText(context.getResources().getString(R.string.Update));
                        btnsavedata.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clsToolsAccessoriesModel clsToolsAccessoriesModel=new clsToolsAccessoriesModel(
                                        model.getKey(),
                                        edtpname.getText().toString(),
                                        edtsellername.getText().toString(),
                                        edtmo.getText().toString(),
                                        edtprc.getText().toString(),
                                        edtstate.getText().toString(),
                                        edtdistrict.getText().toString(),
                                        edttehsil.getText().toString(),
                                        edtvillage.getText().toString(),
                                        edtdescription.getText().toString(),
                                        model.getImg(),
                                        sharedPreferences.getString("uname", "unknown"),
                                        sharedPreferences.getString("url", "null"),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        edtmonth.getText().toString(),
                                        model.getCategory()
                                );
                                FirebaseDatabase.getInstance().getReference().child("Tools&Accessories").child(model.getKey()).setValue(clsToolsAccessoriesModel);
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Tools&Accessories").child(model.getKey()).setValue(clsToolsAccessoriesModel);
                                bottomSheetDialog.cancel();
                            }
                        });
                        bottomSheetDialog.show();
                    }
                });
                if (isMyproduct){
                    bottomSheetDialog.show();
                }
                return false;
            }
        });
        Glide.with(context)
                .load(model.getImg())
                .into(holder.imgprdt);
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtprdt.setText(model.getPname());
        holder.txtprc.setText(context.getResources().getString(R.string.prc)+model.getPrice());
        holder.txtqty.setText(context.getResources().getString(R.string.Usage)+model.getMonth()+"Months");
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(context).inflate(R.layout.lytbtmtools,null,false);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.setContentView(view);
                {
                    ImageButton btnwhatsapp,btncall;
                    TextView txtcategory,txtpname,txtprc,txtpayment,txtstate,txttehsil,txtpnn,txtdistrict,txtvillage,txtdes,txtuname,txtdate,txtsname;
                    String key;
                    ImageView imgprdt,prfpc;
                    ClsCultivationProductModel clsCultivationProductModel;
                    btnwhatsapp=view.findViewById(R.id.btnwhatsapp);
                    btncall=view.findViewById(R.id.btncall);
                    txtcategory=view.findViewById(R.id.txtpcategory);
                    txtpname=view.findViewById(R.id.txtpname);
                    txtprc=view.findViewById(R.id.txtprc);
                    txtpayment=view.findViewById(R.id.txtpayable);
                    imgprdt=view.findViewById(R.id.imgprdt);
                    txtuname=view.findViewById(R.id.txtuname);
                    txtdate=view.findViewById(R.id.txtdate);
                    txtsname=view.findViewById(R.id.txtsname);
                    txtstate=view.findViewById(R.id.txtstate);
                    txttehsil=view.findViewById(R.id.txttehsil);
                    txtdistrict=view.findViewById(R.id.txtdistrict);
                    txtvillage=view.findViewById(R.id.txtVillage);
                    txtdes=view.findViewById(R.id.txtdes);
                    txtpnn=view.findViewById(R.id.txtpnn);
                    prfpc=view.findViewById(R.id.profilepc);
                    FrameLayout btncancel=view.findViewById(R.id.btncancel);
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
                    txtprc.setText(model.getMonth());
                    txtpnn.setText(model.getMo());
                    txtpayment.setText(model.getPrice());
                    txtstate.setText(model.getState());
                    txttehsil.setText(model.getTehsil());
                    txtdistrict.setText(model.getDistrict());
                    txtvillage.setText(model.getVillage());
                    txtdes.setText(model.getDesc());
                    txtuname.setText(model.getUname());
                    txtdate.setText(model.getDate());
                    txtsname.setText(model.getSname());
                }
                bottomSheetDialog.show();
            }
        });

    }

    @NonNull
    @Override
    public RcToolsAccesoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
