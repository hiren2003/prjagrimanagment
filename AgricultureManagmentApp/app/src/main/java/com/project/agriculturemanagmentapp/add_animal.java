package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    String category = "";

    String key = "";
    Uri selectedimg;
    TextView txtname;
    Button btnchooseimg;
    ImageView imgprdt, imgcat;
    Button btnsavedata;
    Intent intent;
    SharedPreferences sharedPreferences;
    TextInputEditText edtsname;
    TextInputEditText edtspeice, edtprc, edtstate, edtdistrict, edttehsil, edtvillage, edtdescription, edtmo, edtyear, edtmonth, edtmilk, edtweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        spntype = findViewById(R.id.category);
        imgprdt = findViewById(R.id.imgprdt);
        btnchooseimg = findViewById(R.id.btnchooseimage);
        arranimal = getResources().getStringArray(R.array.animal_type);
        edtspeice = findViewById(R.id.edtspecie);
        edtprc = findViewById(R.id.edtprc);
        edtstate = findViewById(R.id.edtstate);
        edtdistrict = findViewById(R.id.edtdist);
        edttehsil = findViewById(R.id.edttehsil);
        edtvillage = findViewById(R.id.edtvlg);
        btnsavedata = findViewById(R.id.btnsavedata);
        edtsname = findViewById(R.id.edtsellername);
        edtdescription = findViewById(R.id.edtdes);
        edtyear = findViewById(R.id.edtyear);
        edtmonth = findViewById(R.id.edtMonth);
        edtmo = findViewById(R.id.edtmo);
        edtweight = findViewById(R.id.edtWeight);
        edtmilk = findViewById(R.id.edtMilk);
        txtname = findViewById(R.id.txtname);
        imgcat = findViewById(R.id.imgcat);
        spntype.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arranimal));
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        intent = getIntent();
        int cat = intent.getIntExtra("category", 0);
        if (cat == 1) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cow));
            txtname.setText(arranimal[3]);
            category = arranimal[3];
        } else if (cat == 2) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sheep));
            category = arranimal[0];
            txtname.setText(arranimal[0]);
        } else if (cat == 3) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buffalo));
            category = arranimal[4];
            txtname.setText(arranimal[4]);
        } else if (cat == 4) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.goat));
            category = arranimal[1];
            txtname.setText(arranimal[1]);
        } else if (cat == 5) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ox));
            category = arranimal[7];
            txtname.setText(arranimal[7]);
        } else if (cat == 6) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.chiken));
            category = arranimal[2];
            txtname.setText(arranimal[2]);
        } else if (cat == 7) {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hourse));
            category = arranimal[6];
            txtname.setText(arranimal[6]);
        } else {
            imgcat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.camel));
            category = arranimal[5];
            txtname.setText(arranimal[5]);
        }
        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgprdt.setImageURI(result);
                imgprdt.setVisibility(View.VISIBLE);
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
                } else {
                    Dialog dg = new Dialog(add_animal.this);
                    dg.setContentView(R.layout.lyt_loading_dg);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
                    String key = FirebaseDatabase.getInstance().getReference().child("animals").push().getKey();
                    StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference().child("animals").child(key);
                    firebaseStorage.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            firebaseStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    int mon=Calendar.getInstance().get(Calendar.MONTH);
                                    mon++;
                                    SharedPreferences sharedPreferences1 = getSharedPreferences("data", MODE_PRIVATE);
                                    clsAnimalModel clsAnimalModel = new clsAnimalModel(
                                            key,
                                            category,
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
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + mon + "/" + Calendar.getInstance().get(Calendar.YEAR)
                                            , edtsname.getText().toString(),
                                            sharedPreferences1.getString("mo", "1234567890")
                                    );
                                    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences1.getString("mo", "1234567890")).child("Resell").child("animal").child(key).setValue(clsAnimalModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference().child("Resell").child("animals").child(key).setValue(clsAnimalModel).addOnSuccessListener(new OnSuccessListener<Void>() {
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