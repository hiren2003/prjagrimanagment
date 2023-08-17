package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class EditprofileActivity extends AppCompatActivity {
    Spinner states;
    EditText edtname, edtemail, edtmo, edtaddress;
    RadioButton rdmale, rdfemale;
    ImageView prfpc;
    TextView txtdate;
    RelativeLayout rldate, rlupdate;
    SharedPreferences sharedPreferences;
    ActivityResultLauncher<String> launcher;
    boolean ispicChanged = false;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.Light_green));
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
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
        edtname = findViewById(R.id.name);
        edtemail = findViewById(R.id.email);
        edtmo = findViewById(R.id.mobile);
        edtaddress = findViewById(R.id.address);
        rdmale = findViewById(R.id.rdmale);
        rdfemale = findViewById(R.id.rdfemale);
        prfpc = findViewById(R.id.prfpc);
        txtdate = findViewById(R.id.txtdate);
        rldate = findViewById(R.id.rldate);
        rlupdate = findViewById(R.id.btnupdtedetail);
        states = findViewById(R.id.state);
        Glide.with(this)
                .load(sharedPreferences.getString("url", "null"))
                .into(prfpc);
        String[] arr = getResources().getStringArray(R.array.india_states);
        states.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr));
        edtname.setText(sharedPreferences.getString("uname", " "));
        edtmo.setText(sharedPreferences.getString("mo", " "));
        edtaddress.setText(sharedPreferences.getString("add", " "));
        edtemail.setText(sharedPreferences.getString("email", " "));
        txtdate.setText(sharedPreferences.getString("dob", "Select Date"));
        int isMale = sharedPreferences.getInt("gender", 2);
        if (isMale == 1) {
            rdmale.setChecked(true);
        } else if (isMale == 0) {
            rdfemale.setChecked(true);
        }
        states.setSelection(sharedPreferences.getInt("state", 0));
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
                SharedPreferences.Editor sedit = sharedPreferences.edit();
                StorageReference referencetostorage = FirebaseStorage.getInstance().getReference().child("User_Profiles").child(edtmo.getText().toString());
                if (ispicChanged == false) {
                    FirebaseDatabase.getInstance().getReference().child("Users_List").child(edtmo.getText().toString()).setValue(new clsUserModel(edtname.getText().toString(), edtmo.getText().toString(), sharedPreferences.getString("url", "null"), edtemail.getText().toString(), txtdate.getText().toString(), rdmale.isChecked() ? "Mdtale" : "Female", edtaddress.getText().toString(), states.getSelectedItem().toString()));
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
                    finish();
                } else {
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
                                    finish();

                                }
                            });
                        }
                    });
                }


            }
        });


    }
}