package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
                if(result!=null){
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
                if(edtpname.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Pname), false);
                    edtpname.requestFocus();
                }
               else  if(edtmonth.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Duration), false);
                    edtmonth.requestFocus();
                } else  if(edtsellername.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Seller), false);
                    edtsellername.requestFocus();
                }
                 else if (edtmo.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Mo), false);
                    edtmo.requestFocus();
                } else if (edtmo.getText().toString().trim().length() != 10) {
                    show_toast(getResources().getString(R.string.Invalid_MobileNumber), false);
                    edtmo.requestFocus();
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
                    Dialog dg=new Dialog(add_tools_accesories.this);
                    dg.setContentView(R.layout.lyt_loading_dg);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
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
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                                            edtmonth.getText().toString(),
                                            spncat.getSelectedItem().toString(),
                                            sharedPreferences.getString("mo", "1234567890")
                                    );
                                    FirebaseDatabase.getInstance().getReference().child("Resell").child("Tools&Accessories").child(key).setValue(clsToolsAccessoriesModel);
                                    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Resell").child("Tools&Accessories").child(key).setValue(clsToolsAccessoriesModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            show_toast(getResources().getString(R.string.Upload_Successfully),true);
                                            dg.dismiss();
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            show_toast(getResources().getString(R.string.Upload_Cancelled),false);
                                            dg.dismiss();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    show_toast(getResources().getString(R.string.Upload_Cancelled),false);
                                    dg.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            show_toast(getResources().getString(R.string.Upload_Cancelled),false);
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