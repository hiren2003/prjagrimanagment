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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

public class RcCultivatonPrdtAdpter extends RecyclerView.Adapter<RcCultivatonPrdtAdpter.ViewHolder> {
    Context context;
    boolean isMyproduct;
    ArrayList<ClsCultivationProductModel> clsCultivationProductModelArrayList;

    public RcCultivatonPrdtAdpter(Context context, boolean isMyproduct, ArrayList<ClsCultivationProductModel> clsCultivationProductModelArrayList) {
        this.context = context;
        this.isMyproduct = isMyproduct;
        this.clsCultivationProductModelArrayList = clsCultivationProductModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Animation
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        SharedPreferences sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Showing Update And Delete Option Based On SharedPreferences Value
                Dialog dgop = new Dialog(context);
                dgop.setContentView(R.layout.lyt_edit_option_sheet);
                dgop.getWindow().setBackgroundDrawableResource(R.drawable.drb_round_edges);
                LinearLayout btnupdate = dgop.findViewById(R.id.lnupdate);
                LinearLayout btndelete = dgop.findViewById(R.id.lndelete);
                if (isMyproduct){
                    dgop.show();
                }
                // Show Dialog for delete
                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dgop.cancel();
                        Dialog dg=new Dialog(context);
                        dg.setContentView(R.layout.lyt_delete_dg);
                        dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                        AppCompatButton yes = dg.findViewById(R.id.yes);
                        ImageView no = dg.findViewById(R.id.no);
                        dg.show();
                        // Do Not Delete
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dg.dismiss();
                            }
                        });
                        //Delete the Post
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseDatabase.getInstance().getReference().child("Resell").child("Cultivation Product").child(clsCultivationProductModelArrayList.get(position).getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Cultivatio_Product").child(clsCultivationProductModelArrayList.get(position).getKey()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("cultivationprd").child(clsCultivationProductModelArrayList.get(position).getKey()).delete();
                                dg.dismiss();

                            }
                        });
                    }
                });
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Show Dialog for Update and Fetch and Set Existing Value
                        dgop.cancel();
                        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
                        View view2=LayoutInflater.from(context).inflate(R.layout.activity_add_cultivation_product,null,false);
                        TextInputEditText edtpname, edtspeice, edtqty, edtprc, edtstate, edtdistrict, edttehsil, edtvillage, edtdescription, edtmo, edtsellername;
                        ImageView imgprdt,imgcat;
                        Button btnchooseimg,btnsavedata;
                        Spinner spntype;
                        CardView cd=view2.findViewById(R.id.cd);
                        TextView txtname=view2.findViewById(R.id.txtname);
                        cd.setVisibility(View.GONE);
                        txtname.setVisibility(View.GONE);
                        spntype = view2.findViewById(R.id.category);
                        imgprdt = view2.findViewById(R.id.imgprdt);
                        btnchooseimg = view2.findViewById(R.id.btnchooseimage);
                        edtpname = view2.findViewById(R.id.edtpname);
                        edtspeice = view2.findViewById(R.id.edtspecie);
                        edtqty = view2.findViewById(R.id.edtqty);
                        edtprc = view2.findViewById(R.id.edtprc);
                        edtstate = view2.findViewById(R.id.edtstate);
                        edtdistrict = view2.findViewById(R.id.edtdist);
                        edttehsil = view2.findViewById(R.id.edttehsil);
                        edtsellername = view2.findViewById(R.id.edtsellername);
                        edtvillage = view2.findViewById(R.id.edtvlg);
                        btnsavedata = view2.findViewById(R.id.btnsavedata);
                        edtdescription = view2.findViewById(R.id.edtdes);
                        imgcat =view2.findViewById(R.id.imgcat);
                        edtmo = view2.findViewById(R.id.edtmo);
                        imgprdt.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(clsCultivationProductModelArrayList.get(position).getImg())
                                .into(imgprdt);
                        btnchooseimg.setVisibility(View.GONE);
                        edtpname.setText(clsCultivationProductModelArrayList.get(position).getPname());
                        edtspeice.setText(clsCultivationProductModelArrayList.get(position).getSpecie());
                        edtqty.setText(clsCultivationProductModelArrayList.get(position).getQty());
                        edtprc.setText(clsCultivationProductModelArrayList.get(position).getPrice());
                        edtstate.setText(clsCultivationProductModelArrayList.get(position).getState());
                        edttehsil.setText(clsCultivationProductModelArrayList.get(position).getTehsil());
                        edtdistrict.setText(clsCultivationProductModelArrayList.get(position).getDistrict());
                        edtsellername.setText(clsCultivationProductModelArrayList.get(position).getSname());
                        edtvillage.setText(clsCultivationProductModelArrayList.get(position).getVillage());
                        edtdescription.setText(clsCultivationProductModelArrayList.get(position).getDes());
                        edtmo.setText(clsCultivationProductModelArrayList.get(position).getMo());
                        if (clsCultivationProductModelArrayList.get(position).getCategory().equals("Grains")) {
                            imgcat.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.garins));
                        } else if (clsCultivationProductModelArrayList.get(position).getCategory().equals("Fruits")) {
                            imgcat.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.fruits2));
                        } else if (clsCultivationProductModelArrayList.get(position).getCategory().equals("Pulses")) {
                            imgcat.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pulses));
                        } else if (clsCultivationProductModelArrayList.get(position).getCategory().equals("Vegatable")) {
                            imgcat.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.vegatable));

                        } else {
                            imgcat.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.otherprdt));
                        }
                        btnsavedata.setText(context.getResources().getString(R.string.Update));
                        btnsavedata.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Save Updated Value
                                String payment = String.valueOf((Integer.parseInt(edtqty.getText().toString()) * Integer.parseInt(edtprc.getText().toString())));
                                ClsCultivationProductModel clsCultivationProductModel = new ClsCultivationProductModel(
                                        clsCultivationProductModelArrayList.get(position).getCategory(),
                                        edtpname.getText().toString(),
                                        edtspeice.getText().toString(),
                                        edtqty.getText().toString(),
                                        edtprc.getText().toString(),
                                        payment,
                                        edtvillage.getText().toString(),
                                        edtdistrict.getText().toString(),
                                        edttehsil.getText().toString(),
                                        edtvillage.getText().toString(),
                                        edtdescription.getText().toString(),
                                        clsCultivationProductModelArrayList.get(position).getImg(),
                                        edtmo.getText().toString(),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        clsCultivationProductModelArrayList.get(position).getKey(),
                                        edtsellername.getText().toString(),
                                        sharedPreferences.getString("url", "1234567890")
                                );
                                FirebaseDatabase.getInstance().getReference().child("Resell").child("Cultivation Product").child(clsCultivationProductModelArrayList.get(position).getKey()).setValue(clsCultivationProductModel);
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Cultivatio_Product").child(clsCultivationProductModelArrayList.get(position).getKey()).setValue(clsCultivationProductModel);
                                bottomSheetDialog.cancel();
                            }
                        });
                        bottomSheetDialog.setContentView(view2);
                        bottomSheetDialog.show();
                    }
                });
                return false;
            }
        });
        // Setting Data For Each Item in Grid
        holder.txtprdt.setText(clsCultivationProductModelArrayList.get(position).getPname());
        holder.txtprc.setText(context.getResources().getString(R.string.prc)+clsCultivationProductModelArrayList.get(position).getPrice()+"/K.G.");
        holder.txtqty.setText(clsCultivationProductModelArrayList.get(position).getQty()+" K.G.");
        Glide.with(context)
                .load(clsCultivationProductModelArrayList.get(position).getImg())
                .into(holder.imgprdt);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opening BottomSheetDialog to Show
                //Opening BottomSheetDialog to Show Detail
                View view=LayoutInflater.from(context).inflate(R.layout.activity_show_cultivation_product,null,false);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.setContentView(view);
                {
                    ImageButton btnwhatsapp,btncall;
                    TextView txtcategory,txtpname,txtspecie,txtqty,txtprc,txtpayment,txtstate,txttehsil,txtpnn,txtdistrict,txtvillage,txtdes,txtuname,txtdate,txtsname;
                    String key;
                    LinearLayout llprofileprdt;
                    ImageView imgprdt,prfpc;
                    ClsCultivationProductModel clsCultivationProductModel;
                    llprofileprdt=view.findViewById(R.id.llprofileprdt);
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
                    txtsname=view.findViewById(R.id.txtsname);
                    txtstate=view.findViewById(R.id.txtstate);
                    txttehsil=view.findViewById(R.id.txttehsil);
                    txtdistrict=view.findViewById(R.id.txtdistrict);
                    txtvillage=view.findViewById(R.id.txtVillage);
                    txtdes=view.findViewById(R.id.txtdes);
                    txtpnn=view.findViewById(R.id.txtpnn);
                    prfpc=view.findViewById(R.id.profilepc);
                    FrameLayout btncancel=view.findViewById(R.id.btncancel);
                    txtuname.setText(clsCultivationProductModelArrayList.get(position).getUmo());
                    llprofileprdt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context,MyProfile.class).putExtra("mo",clsCultivationProductModelArrayList.get(position).getUmo()));
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("Users_List").child(clsCultivationProductModelArrayList.get(position).getUmo()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Profile Picture And UserName
                            clsUserModel clsUserModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                            txtuname.setText(clsUserModel.getUname());
                            Glide.with(context)
                                    .load(clsUserModel.getUrl())
                                    .circleCrop()
                                    .into(prfpc);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //Close BottomSheetDialog

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.cancel();
                        }
                    });
                    //Redirect to Whatsapp
                    btnwhatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mo="+91"+clsCultivationProductModelArrayList.get(position).getMo();
                            String msg="Hello "+clsCultivationProductModelArrayList.get(position).getSname()+",\n"+context.getResources().getString(R.string.Interest2)+" "+clsCultivationProductModelArrayList.get(position).getPname();
                            String url = "https://api.whatsapp.com/send?phone="+mo+"&text="+msg;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            context.startActivity(i);
                        }
                    });
                    //Dial the Number
                    btncall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mo="+91"+clsCultivationProductModelArrayList.get(position).getMo();
                            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mo));
                            context.startActivity(intent);
                        }
                    });
                    //Setting Bottomsheet Values
                    Glide.with(context)
                            .load(clsCultivationProductModelArrayList.get(position).getImg())
                            .into(imgprdt);
                    txtcategory.setText(clsCultivationProductModelArrayList.get(position).getCategory());
                    txtpname.setText(clsCultivationProductModelArrayList.get(position).getPname());
                    txtspecie.setText(clsCultivationProductModelArrayList.get(position).getSpecie());
                    txtqty.setText(clsCultivationProductModelArrayList.get(position).getQty());
                    txtprc.setText(clsCultivationProductModelArrayList.get(position).getPrice());
                    txtpnn.setText(clsCultivationProductModelArrayList.get(position).getMo());
                    txtpayment.setText(clsCultivationProductModelArrayList.get(position).getPayment());
                    txtstate.setText(clsCultivationProductModelArrayList.get(position).getState());
                    txttehsil.setText(clsCultivationProductModelArrayList.get(position).getTehsil());
                    txtdistrict.setText(clsCultivationProductModelArrayList.get(position).getDistrict());
                    txtvillage.setText(clsCultivationProductModelArrayList.get(position).getVillage());
                    txtdes.setText(clsCultivationProductModelArrayList.get(position).getDes());
                    txtdate.setText(clsCultivationProductModelArrayList.get(position).getDate());
                    txtsname.setText(clsCultivationProductModelArrayList.get(position).getSname());

                }
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return clsCultivationProductModelArrayList.size();
    }

    @NonNull
    @Override
    public RcCultivatonPrdtAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_resell,parent,false);
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
