package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

public class add_gov_scheme extends AppCompatActivity {
    TextInputEditText edtname, edturl, edtdes;
    ImageView imageView;
    ActivityResultLauncher<String> launcher;
    Uri uri;
    Spinner states;
Button btnsavedata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gov_scheme);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        edtname = findViewById(R.id.edtname);
        edturl = findViewById(R.id.edtlnk);
        edtdes = findViewById(R.id.edtdes);
        imageView = findViewById(R.id.prfpc);
        states = findViewById(R.id.state);
        btnsavedata=findViewById(R.id.btnsavedata);
        String[] arr = getResources().getStringArray(R.array.india_states);
        states.setAdapter(new ArrayAdapter<String>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr));

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageView.setImageURI(result);
                uri = result;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });

        btnsavedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtname.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Title),false);
                }
                else if(edturl.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Link),false);
                }
                else if(edtdes.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Please_Enter_Description),false);
                }
                else if (uri == null) {
                    show_toast(getResources().getString(R.string.Please_Enter_Image),false);
                    launcher.launch("image/*");
                }
                else{
                    Dialog dg=new Dialog(add_gov_scheme.this);
                    dg.setContentView(R.layout.lyt_loading_dg);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
                    String key=FirebaseDatabase.getInstance().getReference().child("Gov_scheme").push().getKey();
                    StorageReference reference =  FirebaseStorage.getInstance().getReference().child("Gov_scheme").child(key);
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseDatabase.getInstance().getReference().child("Gov_scheme").child(key).setValue(new clsgovmodel(key,edtname.getText().toString(),uri.toString(),states.getSelectedItem().toString(),edtdes.getText().toString(),edturl.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dg.dismiss();
                                            show_toast(getResources().getString(R.string.Upload_Successfully),true);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dg.dismiss();
                                            show_toast(getResources().getString(R.string.Upload_Cancelled),false);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dg.dismiss();
                                    show_toast(getResources().getString(R.string.Upload_Cancelled),false);
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