package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_ecomm extends AppCompatActivity {
    Uri selectedimg=null;
    Button btnchooseimg;
    ImageView imgprdt;
    Button btnsavedata;
    Spinner spntype;
    TextInputEditText edtdes, edtspe, edtrecomm, edtprice, edtpname,edtsgst,edtcgst,edtdiscount;
    SharedPreferences sharedPreferences;
    float discount;
    String imgurl="";
    boolean isEdit;
    String key;
    String[] arr;
TextView txttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ecomm);
        spntype = findViewById(R.id.category);
        btnchooseimg = findViewById(R.id.btnchooseimage);
        btnsavedata = findViewById(R.id.btnsavedata);
        imgprdt = findViewById(R.id.imgprdt);
        edtdes = findViewById(R.id.edtdes);
        edtspe = findViewById(R.id.edtspe);
        edtrecomm = findViewById(R.id.edtrecom);
        edtpname = findViewById(R.id.edtpname);
        edtprice = findViewById(R.id.edtprc);
        edtcgst=findViewById(R.id.edtcgst);
        edtsgst=findViewById(R.id.edtsgst);
        txttitle=findViewById(R.id.txttitle);
        edtdiscount=findViewById(R.id.edtdiscount);
         arr = getResources().getStringArray(R.array.arrcategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr);
        spntype.setAdapter(adapter);
        Intent intent=getIntent();
         isEdit=intent.getBooleanExtra("IsEdit",false);
         key=intent.getStringExtra("key");
        if (isEdit){
            txttitle.setText("Update item");
            btnsavedata.setText("Update item"

            );
         FirebaseDatabase.getInstance().getReference("/ECommerce/All/"+key).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 clsEcommModel model=snapshot.getValue(clsEcommModel.class);
             if (model!=null){
                 edtpname.setText(model.getPname());
                 edtprice.setText(model.getPrice()+"");
                 edtcgst.setText(model.getCgst()+"");
                 edtsgst.setText(model.getSgst()+"");
                 edtdiscount.setText(model.getDiscount()+"");
                 edtdes.setText(model.getDes());
                 edtspe.setText(model.getDpec());
                 edtrecomm.setText(model.getRecomm());
                 imgprdt.setVisibility(View.VISIBLE);
                 Glide.with(getApplicationContext())
                         .load(model.getImg())
                         .into(imgprdt);
                 imgurl=model.getImg();
                 for (int i=0;i< arr.length;i++){
                     if (arr[i].toString().equals(model.getType())){
                         spntype.setSelection(i);
                         break;
                     }
                 }

             }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
        }
        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgprdt.setImageURI(result);
                if (result != null) {
                    imgprdt.setVisibility(View.VISIBLE);
                }
                selectedimg = result;
            }
        });
        btnchooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });

        btnsavedata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (edtpname.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Pname), false);
                    edtpname.requestFocus();
                } else if (edtprice.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Price), false);
                    edtprice.requestFocus();
                }
                else if (edtcgst.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_CGST), false);
                    edtprice.requestFocus();
                }
                else if (edtsgst.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_SGST), false);
                    edtprice.requestFocus();
                }
                else if (edtdes.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Description), false);
                    edtdes.requestFocus();
                } else if (edtspe.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Specification), false);
                    edtspe.requestFocus();
                } else if (edtrecomm.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Recommandation), false);
                    edtrecomm.requestFocus();
                } else if (selectedimg == null&&!isEdit) {
                    show_toast(getResources().getString(R.string.Please_Enter_Image), false);
                    launcher.launch("image/*");
                } else {
                    if (edtdiscount.getText().toString().trim().isEmpty()){
                        discount=0;
                    }
                    else{
                        discount=Float.parseFloat(edtdiscount.getText().toString());
                    }
                    Dialog dg = new Dialog(add_ecomm.this);
                    dg.setContentView(R.layout.lyt_loading_dg);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
                    if (!isEdit){
                         key = FirebaseDatabase.getInstance().getReference().child("ECommerce").push().getKey();
                    }
                    StorageReference reference = FirebaseStorage.getInstance().getReference().child("Ecommerce").child(key);
                    if (selectedimg!=null){
                        reference.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        clsEcommModel model=new clsEcommModel(key, edtpname.getText().toString(), uri.toString(), edtprice.getText().toString(), edtspe.getText().toString(), edtdes.getText().toString(), edtrecomm.getText().toString(), "0",Float.parseFloat(edtcgst.getText().toString()),Float.parseFloat(edtsgst.getText().toString()),discount,spntype.getSelectedItem().toString());
                                        FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                show_toast(getResources().getString(R.string.successfullyuploaded), true);
                                                dg.dismiss();
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                show_toast(getResources().getString(R.string.unsuccessfullyuploaded), false);
                                                dg.dismiss();
                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        show_toast(getResources().getString(R.string.unsuccessfullyuploaded), false);
                                        dg.dismiss();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                show_toast(getResources().getString(R.string.unsuccessfullyuploaded), false);
                                dg.dismiss();
                            }
                        });
                    }else{
                        clsEcommModel model=new clsEcommModel(key, edtpname.getText().toString(), imgurl, edtprice.getText().toString(), edtspe.getText().toString(), edtdes.getText().toString(), edtrecomm.getText().toString(), "0",Float.parseFloat(edtcgst.getText().toString()),Float.parseFloat(edtsgst.getText().toString()),discount,spntype.getSelectedItem().toString());
                        FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                show_toast(getResources().getString(R.string.successfullyuploaded), true);
                                dg.dismiss();
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                show_toast(getResources().getString(R.string.unsuccessfullyuploaded), false);
                                dg.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }

    public void show_toast(String msg, boolean isgreen) {
        Toast ts = new Toast(getBaseContext());
        View view;
        if (isgreen) {
            view = getLayoutInflater().inflate(R.layout.lyt_green_toast, (ViewGroup) findViewById(R.id.container));
        } else {
            view = getLayoutInflater().inflate(R.layout.lyt_red_toast, (ViewGroup) findViewById(R.id.container));
        }
        TextView txtmessage = view.findViewById(R.id.txtmsg);
        txtmessage.setText(msg);
        ts.setView(view);
        ts.setGravity(Gravity.TOP, 0, 30);
        ts.setDuration(Toast.LENGTH_SHORT);
        ts.show();

    }
}