package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.telephony.SmsManager;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RcAnimalAdapter extends RecyclerView.Adapter<RcAnimalAdapter.ViewHolder> {

    Context context;
    boolean isMyproduct,isAdmin;
    ArrayList<clsAnimalModel> clsAnimalModels;

    public RcAnimalAdapter(Context context, boolean isMyproduct, boolean isAdmin, ArrayList<clsAnimalModel> clsAnimalModels) {
        this.context = context;
        this.isMyproduct = isMyproduct;
        this.isAdmin = isAdmin;
        this.clsAnimalModels = clsAnimalModels;
    }

    @NonNull
    @Override
    public RcAnimalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_resell, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        clsAnimalModel model = clsAnimalModels.get(position);
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        holder.cd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dgop = new Dialog(context);
                dgop.setContentView(R.layout.lyt_edit_option_sheet);
                dgop.getWindow().setBackgroundDrawableResource(R.drawable.drb_round_edges);
                LinearLayout btnupdate = dgop.findViewById(R.id.lnupdate);
                LinearLayout btndelete = dgop.findViewById(R.id.lndelete);
                if (isMyproduct) {
                    dgop.show();
                }
                if (isAdmin){
                    Dialog dg = new Dialog(context);
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
                            FirebaseDatabase.getInstance().getReference().child("Resell").child("animals").child(model.getKey()).removeValue();
                            FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("animal").child(model.getKey()).removeValue();
                            SmsManager smsManager=SmsManager.getDefault();
                            smsManager.sendTextMessage("+91" +clsAnimalModels.get(position).getMo(),null,"Your Animal "+clsAnimalModels.get(position).getType()+" ("+clsAnimalModels.get(position).getKey()+") is Deleted By Admin.",null,null);                            dg.dismiss();

                        }
                    });
                }
                btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dgop.dismiss();
                        Dialog dg = new Dialog(context);
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
                                FirebaseDatabase.getInstance().getReference().child("Resell").child("animals").child(model.getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("animal").child(model.getKey()).removeValue();
                                dg.dismiss();
                            }
                        });
                    }
                });
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dgop.dismiss();
                        Intent intent=new Intent(context,add_animal.class);
                        intent.putExtra("Forupdate",true);
                        intent.putExtra("key",model.getKey());
                        context.startActivity(intent);
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
                View view = LayoutInflater.from(context).inflate(R.layout.lyt_view_animal_sheet, null, false);
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
                LinearLayout llprofile=view.findViewById(R.id.llprofileanimal);
                Glide.with(context)
                        .load(model.getImg())
                        .into(imgprdt);

                txtmo.setText(model.getMo());
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
                llprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context,MyProfile.class).putExtra("mo",clsAnimalModels.get(position).getUmo()));
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("Users_List").child(model.getUmo()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        clsUserModel clsUserModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                        txtuname.setText(clsUserModel.getUname());
                        Glide.with(context)
                                .load(clsUserModel.getUrl())
                                .circleCrop()
                                .into(imgprfpc);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
                        String mo = "+91" + model.getMo();
                        String msg = "Hello " + model.getSname() + "," + context.getResources().getString(R.string.Interest2) + " " + model.getType();
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


    @Override
    public int getItemCount() {
        return clsAnimalModels.size();
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
