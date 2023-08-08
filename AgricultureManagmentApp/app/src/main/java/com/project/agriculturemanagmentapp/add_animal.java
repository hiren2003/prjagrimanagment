package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class add_animal extends AppCompatActivity {
    String[] arranimal;
    Spinner spntype;


    String key="";
    Uri selectedimg;
    Button btnchooseimg;
    ImageView imgprdt;
    Button btnsavedata;
    SharedPreferences sharedPreferences;
    TextInputEditText edtspeice,edtprc,edtstate,edtdistrict,edttehsil,edtvillage,edtdescription,edtmo,edtyear,edtmonth,edtmilk,edtweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        spntype = findViewById(R.id.category);
        imgprdt = findViewById(R.id.imgprdt);
        btnchooseimg = findViewById(R.id.btnchooseimage);
        arranimal = getResources().getStringArray(R.array.animal_type);
        edtspeice=findViewById(R.id.edtspecie);
        edtprc=findViewById(R.id.edtprc);
        edtstate=findViewById(R.id.edtstate);
        edtdistrict=findViewById(R.id.edtdist);
        edttehsil=findViewById(R.id.edttehsil);
        edtvillage=findViewById(R.id.edtvlg);
        btnsavedata=findViewById(R.id.btnsavedata);
        edtdescription=findViewById(R.id.edtdes);
        edtyear=findViewById(R.id.edtyear);
        edtmonth=findViewById(R.id.edtMonth);
        edtmo=findViewById(R.id.edtmo);
        edtweight=findViewById(R.id.edtWeight);
        edtmilk=findViewById(R.id.edtMilk);
        spntype.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arranimal));
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

                    }
                });


    }
}