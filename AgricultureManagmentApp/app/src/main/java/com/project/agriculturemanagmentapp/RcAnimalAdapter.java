package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.Gravity;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParser;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcAnimalAdapter extends FirebaseRecyclerAdapter<clsAnimalModel, RcAnimalAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    boolean isMyproduct;

    public RcAnimalAdapter(@NonNull FirebaseRecyclerOptions<clsAnimalModel> options, Context context, boolean isMyproduct) {
        super(options);
        this.isMyproduct = isMyproduct;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RcAnimalAdapter.ViewHolder holder, int position, @NonNull clsAnimalModel model) {
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.SheetDialog);
                View view2 = LayoutInflater.from(context).inflate(R.layout.lyteditoption, null, false);
                LinearLayout btnupdate = view2.findViewById(R.id.lnupdate);
                LinearLayout btndelete = view2.findViewById(R.id.lndelete);
                bottomSheetDialog.setContentView(view2);
                if (isMyproduct) {
                    bottomSheetDialog.show();
                }
                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                        Dialog dg = new Dialog(context);
                        dg.setContentView(R.layout.lytdelete);
                        dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                        Button yes = dg.findViewById(R.id.yes);
                        Button no = dg.findViewById(R.id.no);
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
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("animal").child(model.getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference().child("animals").child(model.getKey()).removeValue();
                                dg.dismiss();

                            }
                        });
                    }
                });
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                        View view2 = LayoutInflater.from(context).inflate(R.layout.activity_add_animal, null, false);
                        bottomSheetDialog.setContentView(view2);
                        Button btnchooseimg;
                        ImageView imgprdt;
                        Button btnsavedata;
                        TextInputEditText edtspeice, edtprc, edtstate, edtdistrict, edttehsil, edtvillage, edtdescription, edtmo, edtyear, edtmonth, edtmilk, edtweight, etdsname;
                        Spinner spntype;
                        spntype = view2.findViewById(R.id.category);
                        imgprdt = view2.findViewById(R.id.imgprdt);
                        btnchooseimg = view2.findViewById(R.id.btnchooseimage);
                        edtspeice = view2.findViewById(R.id.edtspecie);
                        etdsname = view2.findViewById(R.id.edtsellername);
                        edtprc = view2.findViewById(R.id.edtprc);
                        edtstate = view2.findViewById(R.id.edtstate);
                        edtdistrict = view2.findViewById(R.id.edtdist);
                        edttehsil = view2.findViewById(R.id.edttehsil);
                        edtvillage = view2.findViewById(R.id.edtvlg);
                        btnsavedata = view2.findViewById(R.id.btnsavedata);
                        edtdescription = view2.findViewById(R.id.edtdes);
                        edtyear = view2.findViewById(R.id.edtyear);
                        edtmonth = view2.findViewById(R.id.edtMonth);
                        edtmo = view2.findViewById(R.id.edtmo);
                        edtweight = view2.findViewById(R.id.edtWeight);
                        edtmilk = view2.findViewById(R.id.edtMilk);
                        spntype.setVisibility(View.GONE);
                        btnchooseimg.setVisibility(View.GONE);
                        imgprdt.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(model.getImg())
                                .into(imgprdt);
                        edtspeice.setText(model.getSpiece());
                        edtprc.setText(model.getPrice());
                        edtstate.setText(model.getState());
                        edttehsil.setText(model.getTehsil());
                        edtdistrict.setText(model.getDistrict());
                        edtvillage.setText(model.getVillage());
                        edtdescription.setText(model.getDes());
                        edtmo.setText(model.getMo());
                        edtyear.setText(model.getAgeyear());
                        edtmonth.setText(model.getAgemonth());
                        edtmilk.setText(model.getMproduction());
                        edtweight.setText(model.getWeight());
                        etdsname.setText(model.getSname());
                        btnsavedata.setText(context.getResources().getString(R.string.Update));
                        btnsavedata.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (edtspeice.getText().toString().trim().isEmpty()) {
                                    edtspeice.setError(context.getResources().getString(R.string.Please_Enter_Speices));
                                    edtspeice.requestFocus();
                                } else if (edtyear.getText().toString().trim().isEmpty()) {
                                    edtyear.setError(context.getResources().getString(R.string.Please_Enter_Year));
                                    edtyear.requestFocus();
                                } else if (edtmonth.getText().toString().trim().isEmpty()) {
                                    edtmonth.setError(context.getResources().getString(R.string.Please_Enter_Month));
                                    edtmonth.requestFocus();
                                } else if (edtmilk.getText().toString().trim().isEmpty()) {
                                    edtmilk.setError(context.getResources().getString(R.string.Please_Enter_Production));
                                    edtmilk.requestFocus();
                                } else if (edtweight.getText().toString().trim().isEmpty()) {
                                    edtweight.setError(context.getResources().getString(R.string.Please_Enter_Wight));
                                    edtweight.requestFocus();
                                } else if (edtmo.getText().toString().trim().isEmpty()) {
                                    edtmo.setError(context.getResources().getString(R.string.Please_Enter_Mo));
                                    edtmo.requestFocus();
                                } else if (edtmo.getText().toString().trim().length() != 10) {
                                    edtmo.setError(context.getResources().getString(R.string.Invalid_MobileNumber));
                                    edtmo.requestFocus();
                                } else if (etdsname.getText().toString().trim().isEmpty()) {
                                    etdsname.setError(context.getResources().getString(R.string.Please_Enter_Seller));
                                    etdsname.requestFocus();
                                } else if (edtprc.getText().toString().trim().isEmpty()) {
                                    edtprc.setError(context.getResources().getString(R.string.Please_Enter_Price));
                                    edtprc.requestFocus();
                                } else if (edtstate.getText().toString().trim().isEmpty()) {
                                    edtstate.setError(context.getResources().getString(R.string.Please_Enter_State));
                                    edtstate.requestFocus();
                                } else if (edtdistrict.getText().toString().trim().isEmpty()) {
                                    edtdistrict.setError(context.getResources().getString(R.string.Please_Enter_District));
                                    edtdistrict.requestFocus();
                                } else if (edttehsil.getText().toString().trim().isEmpty()) {
                                    edttehsil.setError(context.getResources().getString(R.string.Please_Enter_Tehsil));
                                    edttehsil.requestFocus();
                                } else if (edtvillage.getText().toString().trim().isEmpty()) {
                                    edtvillage.setError(context.getResources().getString(R.string.Please_Enter_Village));
                                    edtvillage.requestFocus();
                                } else {
                                    clsAnimalModel clsAnimalModel = new clsAnimalModel(
                                            model.getKey(),
                                            model.getType(),
                                            edtspeice.getText().toString().toString(),
                                            edtyear.getText().toString(),
                                            edtmonth.getText().toString(),
                                            edtmilk.getText().toString(),
                                            edtweight.getText().toString(),
                                            edtmo.getText().toString(),
                                            edtprc.getText().toString(),
                                            edtstate.getText().toString(),
                                            edtdistrict.getText().toString(),
                                            edttehsil.getText().toString(),
                                            edtvillage.getText().toString(),
                                            edtdescription.getText().toString(),
                                            model.getImg(),
                                            sharedPreferences.getString("uname", "null"),
                                            sharedPreferences.getString("url", "unknown"),
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR)
                                            , etdsname.getText().toString()
                                    );
                                    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("animal").child(model.getKey()).setValue(clsAnimalModel);
                                    FirebaseDatabase.getInstance().getReference().child("animals").child(model.getKey()).setValue(clsAnimalModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, context.getResources().getString(R.string.Data_Added_Sucessfully), Toast.LENGTH_SHORT).show();
                                            bottomSheetDialog.cancel();
                                        }
                                    });
                                }

                            }
                        });
                        bottomSheetDialog.show();
                    }
                });
                return false;
            }
        });
        Glide.with(context)
                .load(model.img)
                .into(holder.imgprdt);
        holder.txtprdt.setText(model.type);
        holder.txtqty.setText(model.spiece);
        holder.txtprc.setText(model.price);
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.SheetDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.lytanimalsheet, null, false);
                bottomSheetDialog.setContentView(view);
                ImageView imgprfpc = view.findViewById(R.id.profilepc);
                ImageView imgprdt = view.findViewById(R.id.imgprdt);
                TextView txtuname = view.findViewById(R.id.txtuname);
                TextView txtdate = view.findViewById(R.id.txtdate);
                TextView txttype = view.findViewById(R.id.txttype);
                TextView txtspeice = view.findViewById(R.id.txtspeice);
                TextView txtage = view.findViewById(R.id.txtage);
                TextView txtweight = view.findViewById(R.id.txtweight);
                TextView txtproduction = view.findViewById(R.id.txtproduction);
                TextView txtprice = view.findViewById(R.id.txtprc);
                TextView txtdes = view.findViewById(R.id.txtdes);
                TextView txtsname = view.findViewById(R.id.txtsname);
                TextView txtcity = view.findViewById(R.id.txtvlg);
                FrameLayout btncancel = view.findViewById(R.id.btncancel);
                ImageButton btnwhatsapp = view.findViewById(R.id.btnwhatsapp);
                ImageButton btncall = view.findViewById(R.id.btncall);
                TextView txtmo = view.findViewById(R.id.txtmo);
                Glide.with(context)
                        .load(model.getImg())
                        .into(imgprdt);
                Glide.with(context)
                        .load(model.getPrfpc())
                        .circleCrop()
                        .into(imgprfpc);
                txtmo.setText(model.getMo());
                txtuname.setText(model.getUname());
                txtdate.setText(model.getDate());
                txttype.setText(model.getType());
                txtsname.setText(model.getSname());
                txtspeice.setText(model.getSpiece());
                txtage.setText(model.getAgeyear() + context.getResources().getString(R.string.year) + model.getAgemonth() + context.getResources().getString(R.string.month));
                txtweight.setText(model.getWeight() + context.getResources().getString(R.string.KiloGram2));
                txtprice.setText("₹" + model.getPrice());
                txtproduction.setText(model.getMproduction());
                txtdes.setText(model.getDes());
                txtcity.setText(model.getVillage() + "," + model.getTehsil() + "," + model.getDistrict() + "," + model.getState());
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.cancel();
                    }
                });
                btnwhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mo = "+91" + model.getMo();
                        String msg = "Hello " + model.getUname() + "," + context.getResources().getString(R.string.Interest2) + " " + model.getType();
                        String url = "https://api.whatsapp.com/send?phone=" + mo + "&text=" + msg;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    }
                });
                btncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mo = "+91" + model.getMo();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mo));
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
        View view = LayoutInflater.from(context).inflate(R.layout.lytcprdt, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtprdt, txtqty, txtprc;
        ImageView imgprdt;
        CardView cd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtprdt = itemView.findViewById(R.id.txtpname);
            txtqty = itemView.findViewById(R.id.txtqty);
            txtprc = itemView.findViewById(R.id.txtprc);
            imgprdt = itemView.findViewById(R.id.imgprdt);
            cd = itemView.findViewById(R.id.cdlyt);
        }
    }

}
