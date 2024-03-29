package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class EditprofileActivity extends AppCompatActivity {
    Spinner states;
    EditText edtname, edtemail, edtaddress;
    TextView edtmo;
    RadioButton rdmale, rdfemale;
    ImageView prfpc,rldate,back;
    Dialog dialog;
    TextView txtdate,txt;
    ProgressBar progressBar;
    RelativeLayout  rlupdate;
    SharedPreferences sharedPreferences;
    ActivityResultLauncher<String> launcher;
    boolean ispicChanged = false;
    RadioGroup rdbgrp;
    Uri uri;
    String mo;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        edtname = findViewById(R.id.name);
        edtemail = findViewById(R.id.email);
        edtmo = findViewById(R.id.mobile);
        back = findViewById(R.id.back);
        edtaddress = findViewById(R.id.address);
        rdmale = findViewById(R.id.rdmale);
        rdfemale = findViewById(R.id.rdfemale);
        prfpc = findViewById(R.id.prfpc);
        txtdate = findViewById(R.id.txtdate);
        rldate = findViewById(R.id.rldate);
        rlupdate = findViewById(R.id.btnupdtedetail);
        states = findViewById(R.id.state);
        rdbgrp=findViewById(R.id.rdbgrp);
        progressBar=findViewById(R.id.progressBar2);
        txt=findViewById(R.id.txt);
        String[] arr = getResources().getStringArray(R.array.india_states);
        states.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr));
        Intent intent=getIntent();
        mo=intent.getStringExtra("mo");
        type=intent.getIntExtra("type",0);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        if(type==1){
            rlupdate.setVisibility(View.GONE);
            rldate.setEnabled(false);
            prfpc.setClickable(false);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users_List").child(mo);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    clsUserModel model=snapshot.getValue(clsUserModel.class);
                    Glide.with(getBaseContext())
                            .load(model.getUrl())
                            .into(prfpc);
                    edtname.setText(model.getUname());
                    edtmo.setText("+91 "+model.getMo());
                    edtaddress.setText(model.getAddress());
                    edtemail.setText(model.getEmail());
                    txtdate.setText(model.getDob());
                    if (model.getGender().toString().trim().equals("Male")) {
                        rdmale.setChecked(true);
                    } else {
                        rdfemale.setChecked(true);
                    }
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].toString().trim().equals(model.getState().toString().trim())){
                            states.setSelection(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            mo=sharedPreferences.getString("mo","");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users_List").child(mo);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    clsUserModel model=snapshot.getValue(clsUserModel.class);
                    Glide.with(getBaseContext())
                            .load(model.getUrl())
                            .into(prfpc);
                    edtname.setText(model.getUname());
                    edtmo.setText(model.getMo());
                    edtaddress.setText(model.getAddress());
                    edtemail.setText(model.getEmail());
                    txtdate.setText(model.getDob());
                    if (model.getGender().toString().trim().equals("Male")) {
                        rdmale.setChecked(true);
                    } else {
                        rdfemale.setChecked(true);
                    }
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].toString().trim().equals(model.getState().toString().trim())){
                            states.setSelection(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                if (result==null){

                }
                else {
                    prfpc.setImageURI(result);
                    uri = result;
                    ispicChanged=true;
                }
            }
        });


        prfpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        rldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditprofileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtdate.setText(dayOfMonth + "/" + (++month) + "/" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        rlupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                txt.setVisibility(View.GONE);
                SharedPreferences.Editor sedit = sharedPreferences.edit();
                StorageReference referencetostorage = FirebaseStorage.getInstance().getReference().child("User_Profiles").child(edtmo.getText().toString());
                if (ispicChanged == false) {
                    FirebaseDatabase.getInstance().getReference().child("Users_List").child(edtmo.getText().toString()).setValue(new clsUserModel(edtname.getText().toString(), edtmo.getText().toString(), sharedPreferences.getString("url", "null"), edtemail.getText().toString(), txtdate.getText().toString(), rdmale.isChecked() ? "Male" : "Female", edtaddress.getText().toString(), states.getSelectedItem().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.setVisibility(View.GONE);
                            txt.setVisibility(View.VISIBLE);
                            sedit.putString("uname", edtname.getText().toString());
                            sedit.putString("mo", edtmo.getText().toString());
                            sedit.putString("email", edtemail.getText().toString());
                            sedit.putString("add", edtaddress.getText().toString());
                            sedit.putString("dob", txtdate.getText().toString());
                            sedit.putInt("gender", rdmale.isChecked() ? 1 : 0);
                            sedit.putInt("state", states.getSelectedItemPosition());
                            sedit.putBoolean("iscomplete",true);
                            if(edtaddress.getText().toString().trim().isEmpty()){
                                sedit.putBoolean("hasadd",false);
                            }
                            else{
                                sedit.putBoolean("hasadd",true);
                            }
                            sedit.apply();
                            sedit.commit();
                        }
                    });
                    finish();
                }

                else {
                    referencetostorage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            referencetostorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseDatabase.getInstance().getReference().child("Users_List").child(edtmo.getText().toString()).setValue(new clsUserModel(edtname.getText().toString(), edtmo.getText().toString(), uri.toString(), edtemail.getText().toString(), txtdate.getText().toString(), rdmale.isChecked() ? "Mdtale" : "Female", edtaddress.getText().toString(), states.getSelectedItem().toString()));
                                    sedit.putString("uname", edtname.getText().toString());
                                    sedit.putString("mo", edtmo.getText().toString());
                                    sedit.putString("email", edtemail.getText().toString());
                                    sedit.putString("add", edtaddress.getText().toString());
                                    sedit.putString("dob", txtdate.getText().toString());
                                    sedit.putInt("gender", rdmale.isChecked() ? 1 : 0);
                                    sedit.putString("url", uri.toString());
                                    sedit.putInt("state", states.getSelectedItemPosition());
                                    sedit.putBoolean("iscomplete",true);
                                    if(edtaddress.getText().toString().trim().isEmpty()){
                                        sedit.putBoolean("hasadd",false);
                                    }
                                    else{
                                        sedit.putBoolean("hasadd",true);
                                    }
                                    sedit.apply();
                                    sedit.commit();
                                    progressBar.setVisibility(View.GONE);
                                    txt.setVisibility(View.VISIBLE);
                                    finish();
                                }
                            });
                        }
                    });
                }


            }
        });


    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }
}