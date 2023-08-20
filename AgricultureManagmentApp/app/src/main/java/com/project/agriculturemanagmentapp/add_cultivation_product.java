package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
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
    TextView txtname;
    Spinner spntype;
    String key = "";
    Uri selectedimg;
    Button btnchooseimg;
    ImageView imgprdt, imgcat;
    String cat;
    Button btnsavedata;
    SharedPreferences sharedPreferences;
    Intent intent;
    TextInputEditText edtpname, edtspeice, edtqty, edtprc, edtstate, edtdistrict, edttehsil, edtvillage, edtdescription, edtmo, edtsellername;
    String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cultivation_product);
        spntype = findViewById(R.id.category);
        imgprdt = findViewById(R.id.imgprdt);
        btnchooseimg = findViewById(R.id.btnchooseimage);
        arrprdttp = getResources().getStringArray(R.array.arrprdttp);
        edtpname = findViewById(R.id.edtpname);
        edtspeice = findViewById(R.id.edtspecie);
        edtqty = findViewById(R.id.edtqty);
        edtprc = findViewById(R.id.edtprc);
        edtstate = findViewById(R.id.edtstate);
        edtdistrict = findViewById(R.id.edtdist);
        txtname=findViewById(R.id.txtname);
        edttehsil = findViewById(R.id.edttehsil);
        edtsellername = findViewById(R.id.edtsellername);
        edtvillage = findViewById(R.id.edtvlg);
        btnsavedata = findViewById(R.id.btnsavedata);
        edtdescription = findViewById(R.id.edtdes);
        imgcat = findViewById(R.id.imgcat);
        edtmo = findViewById(R.id.edtmo);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        intent = getIntent();
        int cat = intent.getIntExtra("category", 0);
        if (cat == 1) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.garins));
            txtname.setText(arrprdttp[3]);
            category = arrprdttp[3];
        } else if (cat == 2) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fruits2));
            category = arrprdttp[1];
            txtname.setText(arrprdttp[1]);
        } else if (cat == 3) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pulses));
            category = arrprdttp[2];
            txtname.setText(arrprdttp[2]);
        } else if (cat == 4) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.vegatable));
            category = arrprdttp[0];
            txtname.setText(arrprdttp[0]);
        } else {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.otherprdt));
            category = arrprdttp[4];
            txtname.setText(arrprdttp[4]);
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
                if (edtpname.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Pname), false);
                    edtpname.requestFocus();
                } else if (edtspeice.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Speices), false);
                    edtspeice.requestFocus();
                } else if (edtqty.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Qty), false);
                    edtqty.requestFocus();
                } else if (edtprc.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Price), false);
                    edtprc.requestFocus();
                } else if (edtsellername.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Seller), false);
                    edtsellername.requestFocus();
                } else if (edtmo.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Mo), false);
                    edtmo.requestFocus();
                } else if (edtmo.getText().toString().trim().length() != 10) {
                    show_toast(getResources().getString(R.string.Invalid_MobileNumber), false);
                    edtmo.requestFocus();
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
                } else {
                    Dialog dg = new Dialog(add_cultivation_product.this);
                    dg.setContentView(R.layout.lytloading);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
                    key = FirebaseDatabase.getInstance().getReference().child("Cultivation Product").push().getKey();
                    StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference().child("cultivationprd").child(key);
                    firebaseStorage.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            firebaseStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String payment = String.valueOf((Integer.parseInt(edtqty.getText().toString()) * Integer.parseInt(edtprc.getText().toString())));
                                    ClsCultivationProductModel clsCultivationProductModel = new ClsCultivationProductModel(
                                            category,
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
                                            sharedPreferences.getString("url", "null"),
                                            sharedPreferences.getString("uname", "unknown"),
                                            edtmo.getText().toString(),
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                            key,
                                            edtsellername.getText().toString()
                                    );
                                    FirebaseDatabase.getInstance().getReference().child("Cultivation Product").child(key).setValue(clsCultivationProductModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Cultivatio_Product").child(key).setValue(clsCultivationProductModel).addOnSuccessListener(new OnSuccessListener<Void>() {
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