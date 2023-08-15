package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class add_tools_accesories extends AppCompatActivity {
    ImageView imgprdt;
    Uri selectedimg;
    Button btnchooseimg,btnsavedata;
    String key="";
    SharedPreferences sharedPreferences;
    Spinner spncat;
    TextInputEditText edtpname,edtprc,edtmonth,edtstate, edtdistrict, edttehsil, edtvillage, edtdescription, edtmo, edtsellername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tools_accesories);
        imgprdt = findViewById(R.id.imgprdt);
        btnchooseimg = findViewById(R.id.btnchooseimage);
        edtprc = findViewById(R.id.edtprc);
        edtstate = findViewById(R.id.edtstate);
        edtdistrict = findViewById(R.id.edtdist);
        edtmonth=findViewById(R.id.edtMonth);
        edttehsil = findViewById(R.id.edttehsil);
        edtsellername = findViewById(R.id.edtsellername);
        edtvillage = findViewById(R.id.edtvlg);
        btnsavedata = findViewById(R.id.btnsavedata);
        edtdescription = findViewById(R.id.edtdes);
        edtmo = findViewById(R.id.edtmo);
        spncat=findViewById(R.id.category);
        spncat.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.toolstype)));
        edtpname=findViewById(R.id.edtpname);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        ActivityResultLauncher<String> launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgprdt.setImageURI(result);
                imgprdt.setVisibility(View.VISIBLE);
                selectedimg=result;
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

                ProgressDialog progressDialog = new ProgressDialog(add_tools_accesories.this);
                progressDialog.setMessage(getResources().getString(R.string.Data_Uploading));
                progressDialog.create();
                // progressDialog.show();
                Dialog dgload = new Dialog(add_tools_accesories.this);
                View view = LayoutInflater.from(add_tools_accesories.this).inflate(R.layout.lytloading, null, false);
                LottieAnimationView lottieAnimationView = view.findViewById(R.id.lotyanim);
                dgload.setContentView(view);
                dgload.show();
                key = FirebaseDatabase.getInstance().getReference().child("Tools&Accessories").push().getKey();
                StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference().child("Tools&Accessories").child(key);
                firebaseStorage.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        firebaseStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                clsToolsAccessoriesModel clsToolsAccessoriesModel=new clsToolsAccessoriesModel(
                                        key,
                                        edtpname.getText().toString(),
                                        edtsellername.getText().toString(),
                                        edtmo.getText().toString(),
                                        edtprc.getText().toString(),
                                        edtstate.getText().toString(),
                                        edtdistrict.getText().toString(),
                                        edttehsil.getText().toString(),
                                        edtvillage.getText().toString(),
                                        edtdescription.getText().toString(),
                                        uri.toString(),
                                        sharedPreferences.getString("uname", "unknown"),
                                        sharedPreferences.getString("url", "null"),
                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                        edtmonth.getText().toString(),
                                        spncat.getSelectedItem().toString()
                                        );
                                FirebaseDatabase.getInstance().getReference().child("Tools&Accessories").child(key).setValue(clsToolsAccessoriesModel);
                                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Tools&Accessories").child(key).setValue(clsToolsAccessoriesModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dgload.dismiss();
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