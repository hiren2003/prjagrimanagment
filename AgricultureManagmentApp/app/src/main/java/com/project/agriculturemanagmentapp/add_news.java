package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class add_news extends AppCompatActivity {
    Spinner lang;
    Uri selectedimg;
    ImageView imageView;
    TextInputEditText edtheadline,edtlnk,edtdes;
    Button btnpnews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));
        lang=findViewById(R.id.lang);
        imageView=findViewById(R.id.imgnews);
        btnpnews=findViewById(R.id.btnpublishnews);
        edtheadline=findViewById(R.id.edtheadline);
        edtlnk=findViewById(R.id.edtlnk);
        edtdes=findViewById(R.id.edtdes);
        String[] arr=getResources().getStringArray(R.array.language);
        lang.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arr));
        ActivityResultLauncher<String> launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                selectedimg=result;
                imageView.setImageURI(result);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        btnpnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtheadline.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Title),false);
                    edtheadline.requestFocus();
                }
                else if(edtlnk.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Link),false);
                    edtlnk.requestFocus();
                }
                else if(edtdes.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Description),false);
                    edtdes.requestFocus();
                } else if (selectedimg==null) {
                    show_toast(getResources().getString(R.string.Please_Enter_Image),false);
                    launcher.launch("image/*");
                }
                else{
                    String key= FirebaseDatabase.getInstance().getReference().child("news").push().getKey();
                    StorageReference reference= FirebaseStorage.getInstance().getReference().child("news").child(key);
                    reference.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseDatabase.getInstance().getReference().child("news").child(key).setValue(new clsNewsModel(key,edtheadline.getText().toString(),edtlnk.getText().toString(),edtdes.getText().toString(),uri.toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            show_toast(getResources().getString(R.string.Upload_Successfully), true);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            show_toast(getResources().getString(R.string.Upload_Cancelled),false);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    show_toast(getResources().getString(R.string.Upload_Cancelled),false);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            show_toast(getResources().getString(R.string.Upload_Cancelled),false);
                        }
                    });
                }

            }
        });

    }public void show_toast(String msg, boolean isgreen) {
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