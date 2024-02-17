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

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcToolsAccesoriesAdapter extends RecyclerView.Adapter<RcToolsAccesoriesAdapter.ViewHolder> {

    Context context;
    boolean isMyproduct;
    ArrayList<clsToolsAccessoriesModel> clsToolsAccessoriesModelArrayList ;

    public RcToolsAccesoriesAdapter(Context context, boolean isMyproduct, ArrayList<clsToolsAccessoriesModel> clsToolsAccessoriesModelArrayList) {
        this.context = context;
        this.isMyproduct = isMyproduct;
        this.clsToolsAccessoriesModelArrayList = clsToolsAccessoriesModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dgop = new Dialog(context);
                dgop.setContentView(R.layout.lyt_edit_option_sheet);
                dgop.getWindow().setBackgroundDrawableResource(R.drawable.drb_round_edges);
                LinearLayout btnupdate = dgop.findViewById(R.id.lnupdate);
                LinearLayout btndelete = dgop.findViewById(R.id.lndelete);
                if (isMyproduct){
                    dgop.show();
                }

                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dgop.dismiss();
                        Dialog dg=new Dialog(context);
                        dg.setContentView(R.layout.lyt_delete_dg);
                        dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                        AppCompatButton yes = dg.findViewById(R.id.yes);
                        ImageView no = dg.findViewById(R.id.no);
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
                                FirebaseDatabase.getInstance().getReference().child("Tools&Accessories").child(clsToolsAccessoriesModelArrayList.get(position).getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Tools&Accessories").child(clsToolsAccessoriesModelArrayList.get(position).getKey()).removeValue();
                                dg.dismiss();

                            }
                        });
                    }
                });
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dgop.dismiss();
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
                                .load(clsToolsAccessoriesModelArrayList.get(position).getImg())
                                .into(imgprdt);
                        edtprc.setText(clsToolsAccessoriesModelArrayList.get(position).getPrice());
                        edtstate.setText(clsToolsAccessoriesModelArrayList.get(position).getState());
                        edttehsil.setText(clsToolsAccessoriesModelArrayList.get(position).getTehsil());
                        edtdistrict.setText(clsToolsAccessoriesModelArrayList.get(position).getDistrict());
                        edtsellername.setText(clsToolsAccessoriesModelArrayList.get(position).getSname());
                        edtvillage.setText(clsToolsAccessoriesModelArrayList.get(position).getVillage());
                        edtdescription.setText(clsToolsAccessoriesModelArrayList.get(position).getDesc());
                        edtmo.setText(clsToolsAccessoriesModelArrayList.get(position).getMo());
                        edtmonth.setText(clsToolsAccessoriesModelArrayList.get(position).getMonth());
                        edtpname.setText(clsToolsAccessoriesModelArrayList.get(position).getPname());
                        btnsavedata.setText(context.getResources().getString(R.string.Update));
                        btnsavedata.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clsToolsAccessoriesModel clsToolsAccessoriesModel=new clsToolsAccessoriesModel(
                                        clsToolsAccessoriesModelArrayList.get(position).getKey(),
                                        edtpname.getText().toString(),
                                        edtsellername.getText().toString(),
                                        edtmo.getText().toString(),
                                        edtprc.getText().toString(),
                                        edtstate.getText().toString(),
                                        edtdistrict.getText().toString(),
                                        edttehsil.getText().toString(),
                                        edtvillage.getText().toString(),
                                        edtdescription.getText().toString(),
                                        clsToolsAccessoriesModelArrayList.get(position).getImg(),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        edtmonth.getText().toString(),
                                        clsToolsAccessoriesModelArrayList.get(position).getCategory(),
                                        sharedPreferences.getString("mo", "1234567890")
                                );
                                FirebaseDatabase.getInstance().getReference().child("Resell").child("Tools&Accessories").child(clsToolsAccessoriesModelArrayList.get(position).getKey()).setValue(clsToolsAccessoriesModel);
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Tools&Accessories").child(clsToolsAccessoriesModelArrayList.get(position).getKey()).setValue(clsToolsAccessoriesModel);
                                bottomSheetDialog.cancel();
                            }
                        });
                        bottomSheetDialog.show();
                    }
                });

                return false;
            }
        });
        Glide.with(context)
                .load(clsToolsAccessoriesModelArrayList.get(position).getImg())
                .into(holder.imgprdt);
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        holder.txtprdt.setText(clsToolsAccessoriesModelArrayList.get(position).getPname());
        holder.txtprc.setText(context.getResources().getString(R.string.prc)+clsToolsAccessoriesModelArrayList.get(position).getPrice());
        holder.txtqty.setText(context.getResources().getString(R.string.Usage)+clsToolsAccessoriesModelArrayList.get(position).getMonth()+"Months");
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(context).inflate(R.layout.lyt_view_tools_sheet,null,false);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.SheetDialog);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.setContentView(view);
                {
                    ImageButton btnwhatsapp,btncall;
                    TextView txtcategory,txtpname,txtprc,txtpayment,txtstate,txttehsil,txtpnn,txtdistrict,txtvillage,txtdes,txtuname,txtdate,txtsname;
                    String key;
                    ImageView imgprdt,prfpc;
                    ClsCultivationProductModel clsCultivationProductModel;
                    LinearLayout llprofile=view.findViewById(R.id.llprofiletools);
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
                    llprofile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context,MyProfile.class).putExtra("mo",clsToolsAccessoriesModelArrayList.get(position).getUmo()));
                        }
                    });
                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.cancel();
                        }
                    });
                    btnwhatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mo="+91"+clsToolsAccessoriesModelArrayList.get(position).getMo();
                            String msg="Hello "+clsToolsAccessoriesModelArrayList.get(position).getSname()+","+context.getResources().getString(R.string.Interest2)+" "+clsToolsAccessoriesModelArrayList.get(position).getPname();
                            String url = "https://api.whatsapp.com/send?phone="+mo+"&text="+msg;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            context.startActivity(i);
                        }
                    });
                    btncall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String mo="+91"+clsToolsAccessoriesModelArrayList.get(position).getMo();
                            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mo));
                            context.startActivity(intent);
                        }
                    });
                    Glide.with(context)
                            .load(clsToolsAccessoriesModelArrayList.get(position).getImg())
                            .into(imgprdt);
                    txtcategory.setText(clsToolsAccessoriesModelArrayList.get(position).getCategory());
                    txtpname.setText(clsToolsAccessoriesModelArrayList.get(position).getPname());
                    txtprc.setText(clsToolsAccessoriesModelArrayList.get(position).getMonth());
                    txtpnn.setText(clsToolsAccessoriesModelArrayList.get(position).getMo());
                    txtpayment.setText(clsToolsAccessoriesModelArrayList.get(position).getPrice());
                    txtstate.setText(clsToolsAccessoriesModelArrayList.get(position).getState());
                    txttehsil.setText(clsToolsAccessoriesModelArrayList.get(position).getTehsil());
                    txtdistrict.setText(clsToolsAccessoriesModelArrayList.get(position).getDistrict());
                    txtvillage.setText(clsToolsAccessoriesModelArrayList.get(position).getVillage());
                    txtdes.setText(clsToolsAccessoriesModelArrayList.get(position).getDesc());
                    txtdate.setText(clsToolsAccessoriesModelArrayList.get(position).getDate());
                    txtsname.setText(clsToolsAccessoriesModelArrayList.get(position).getSname());
                    FirebaseDatabase.getInstance().getReference().child("Users_List").child(clsToolsAccessoriesModelArrayList.get(position).getUmo()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                }
                bottomSheetDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return clsToolsAccessoriesModelArrayList.size();
    }

    @NonNull
    @Override
    public RcToolsAccesoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
