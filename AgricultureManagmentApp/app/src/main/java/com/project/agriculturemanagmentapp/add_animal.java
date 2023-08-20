package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnFailureListener;
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
    TextInputEditText edtsname;
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
        edtsname=findViewById(R.id.edtsellername);
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
                if (edtspeice.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Speices), false);
                    edtspeice.requestFocus();
                } else if (edtyear.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Year), false);
                    edtyear.requestFocus();
                } else if (edtmonth.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Month), false);
                    edtmonth.requestFocus();
                } else if (edtmilk.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Production), false);
                    edtmilk.requestFocus();
                } else if (edtweight.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Wight), false);
                    edtweight.requestFocus();
                } else if (edtmo.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Mo), false);
                    edtmo.requestFocus();
                } else if (edtmo.getText().toString().trim().length() != 10) {
                    show_toast(getResources().getString(R.string.Invalid_MobileNumber), false);
                    edtmo.requestFocus();
                } else if (edtsname.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Seller), false);
                    edtsname.requestFocus();
                } else if (edtprc.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Price), false);
                    edtprc.requestFocus();
                } else if (edtstate.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_State), false);
                    edtstate.requestFocus();
                } else if (edtdistrict.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_District), false);
                    edtdistrict.requestFocus();
                } else if (edttehsil.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Tehsil), false);
                    edttehsil.requestFocus();
                } else if (edtvillage.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Village), false);
                    edtvillage.requestFocus();
                } else if (selectedimg == null) {
                    show_toast(getResources().getString(R.string.Please_Enter_Image), false);
                    launcher.launch("image/*");
                }
                else{
                    Dialog dg=new Dialog(add_animal.this);
                    dg.setContentView(R.layout.lytloading);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
                    String key=FirebaseDatabase.getInstance().getReference().child("animals").push().getKey();
                    StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference().child("animals").child(key);
                    firebaseStorage.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            firebaseStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    SharedPreferences sharedPreferences1=getSharedPreferences("data",MODE_PRIVATE);
                                    clsAnimalModel clsAnimalModel=new clsAnimalModel(
                                            key,
                                            spntype.getSelectedItem().toString(),
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
                                            uri.toString(),
                                            sharedPreferences1.getString("uname","null"),
                                            sharedPreferences1.getString("url","unknown"),
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR)
                                            ,edtsname.getText().toString()
                                    );
                                    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences1.getString("mo","1234567890")).child("Resell").child("animal").child(key).setValue(clsAnimalModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference().child("animals").child(key).setValue(clsAnimalModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    show_toast(getResources().getString(R.string.successfullyuploaded),true);
                                                    dg.dismiss();
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    show_toast(getResources().getString(R.string.unsuccessfullyuploaded),false);
                                                    dg.dismiss();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            show_toast(getResources().getString(R.string.unsuccessfullyuploaded),false);
                                            dg.dismiss();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    show_toast(getResources().getString(R.string.unsuccessfullyuploaded),false);
                                    dg.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            show_toast(getResources().getString(R.string.unsuccessfullyuploaded),false);
                            dg.dismiss();
                        }
                    });
                }

            }
        });
    }
    public void show_toast(String msg, boolean isgreen) {
        Toast ts = new Toast(getBaseContext());
        View view;
        if (isgreen) {
            view = getLayoutInflater().inflate(R.layout.lyttoastgreen, (ViewGroup) findViewById(R.id.container));
        } else {
            view = getLayoutInflater().inflate(R.layout.lyttoast, (ViewGroup) findViewById(R.id.container));
        }
        TextView txtmessage = view.findViewById(R.id.txtmsg);
        txtmessage.setText(msg);
        ts.setView(view);
        ts.setGravity(Gravity.TOP, 0, 30);
        ts.setDuration(Toast.LENGTH_SHORT);
        ts.show();
    }
}