package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Locale;

public class add_feed extends AppCompatActivity {
    public static  final  int  CAMERA_PERM_CODE =101;
    public static  final  int  CAMERA_REQUEST_CODE =101;
    ActivityResultLauncher<String> launcher;
    String url;
    StorageReference reference;
    Uri uri1;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView txt,open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        setLanguage();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.feedcolor));
        txt = findViewById(R.id.txt);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String mo = sharedPreferences.getString("mo", "1234567890");
        ImageView img = findViewById(R.id.imgfeed);
        RelativeLayout upload = findViewById(R.id.btnupload);
        RelativeLayout cancel = findViewById(R.id.btncancel);
        TextInputEditText textInputEditText = findViewById(R.id.edtdes);
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                img.setImageURI(result);
                uri1 = result;
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri1 == null) {
                    Toast.makeText(add_feed.this, getResources().getString(R.string.Select_Image), Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.GONE);
                    String key = firebaseDatabase.getReference().child("Feed").push().getKey().toString();
                    reference = firebaseStorage.getReference().child("feedimg").child(mo).child(key);
                    reference.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String key2 = firebaseDatabase.getReference().child("User").child(mo).child("Feed").push().getKey().toString();
                                    firebaseDatabase.getReference().child("Feed").child(key).setValue(new clsFeedModel(sharedPreferences.getString("url", "123"), sharedPreferences.getString("uname", "unknown"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR), uri.toString(), textInputEditText.getText().toString(), key2, key));
                                    FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Feed").child(key2).setValue(new clsFeedModel(sharedPreferences.getString("url", "123"), sharedPreferences.getString("uname", "unknown"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR), uri.toString(), textInputEditText.getText().toString(), key2, key)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressBar.setVisibility(View.GONE);
                                            txt.setVisibility(View.VISIBLE);
                                            Toast.makeText(add_feed.this, getResources().getString(R.string.Data_Added_Sucessfully), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                askCameraPermission();
//            }
//        });
    }

//    private void askCameraPermission() {
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
//        }else{
//            openCamera();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            openCamera();
//        } else {
//            Toast.makeText(this, "camara is  required", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    private void openCamera() {
//        Toast.makeText(this,"camara open  required",Toast.LENGTH_LONG).show();
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        setLanguage();
    }

    public void setLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String lang = sharedPreferences.getString("getlen", "en");
        Locale locale = new Locale(lang, "rnIN");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
}