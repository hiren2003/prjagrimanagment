package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class add_cultivation_product extends AppCompatActivity {
    String[] arrprdttp;
    Spinner spntype;


String key="";
    Uri selectedimg;
    Button btnchooseimg;
ImageView imgprdt,imgcat;
String cat;
Button btnsavedata;
SharedPreferences sharedPreferences;
Intent intent;
TextInputEditText edtpname,edtspeice,edtqty,edtprc,edtstate,edtdistrict,edttehsil,edtvillage,edtdescription,edtmo,edtsellername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cultivation_product);
        spntype = findViewById(R.id.category);
        imgprdt = findViewById(R.id.imgprdt);
        btnchooseimg = findViewById(R.id.btnchooseimage);
        arrprdttp = getResources().getStringArray(R.array.arrprdttp);
        edtpname=findViewById(R.id.edtpname);
        edtspeice=findViewById(R.id.edtspecie);
        edtqty=findViewById(R.id.edtqty);
        edtprc=findViewById(R.id.edtprc);
        edtstate=findViewById(R.id.edtstate);
        edtdistrict=findViewById(R.id.edtdist);
        edttehsil=findViewById(R.id.edttehsil);
        edtsellername=findViewById(R.id.edtsellername);
        edtvillage=findViewById(R.id.edtvlg);
        btnsavedata=findViewById(R.id.btnsavedata);
        edtdescription=findViewById(R.id.edtdes);
        imgcat=findViewById(R.id.imgcat);
        edtmo=findViewById(R.id.edtmo);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        intent=getIntent();
        int cat=intent.getIntExtra("category",0);
            if(cat==1){
                imgcat.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.garins));
        }else   if(cat==2){
                imgcat.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.fruits2));
            }
            else   if(cat==3){
                imgcat.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pulses));
            }
            else   if(cat==4){
                imgcat.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.vegatable));
            }else{
                imgcat.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.otherprdt));
            }
        ActivityResultLauncher<String> launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgprdt.setImageURI(result);
                imgprdt.setVisibility(View.VISIBLE);
                selectedimg=result;
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arrprdttp);
        spntype.setAdapter(adapter);
        btnchooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
     btnsavedata.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             ProgressDialog progressDialog=new ProgressDialog(add_cultivation_product.this);
             progressDialog.setMessage(getResources().getString(R.string.Data_Uploading));
             progressDialog.create();
            // progressDialog.show();
             Dialog dgload=new Dialog(add_cultivation_product.this);
             View view= LayoutInflater.from(add_cultivation_product.this).inflate(R.layout.lytloading,null,false);
             LottieAnimationView lottieAnimationView=view.findViewById(R.id.lotyanim);
             dgload.setContentView(view);
             dgload.show();
        key=FirebaseDatabase.getInstance().getReference().child("Cultivation Product").push().getKey();
             StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference().child("cultivationprd").child(key);
             firebaseStorage.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     firebaseStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             String payment= String.valueOf((Integer.parseInt(edtqty.getText().toString())*Integer.parseInt( edtprc.getText().toString())));
                             ClsCultivationProductModel clsCultivationProductModel=new ClsCultivationProductModel(
                                     spntype.getSelectedItem().toString(),
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
                                     uri.toString(),
                                     sharedPreferences.getString("url","null"),
                                     sharedPreferences.getString("uname","unknown"),
                                     edtmo.getText().toString(),
                                     Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                     key,
                                     edtsellername.getText().toString()
                             );
                             FirebaseDatabase.getInstance().getReference().child("Cultivation Product").child(key).setValue(clsCultivationProductModel);
                             FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Resell").child(key).setValue(clsCultivationProductModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     progressDialog.dismiss();
                                      progressDialog.dismiss();
                                      finish();
                                 }
                             });
                         }
                     });
                 }
             });
         }
     });

    }

}