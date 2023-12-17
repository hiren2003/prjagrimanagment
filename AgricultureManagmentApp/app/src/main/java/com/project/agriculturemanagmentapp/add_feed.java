package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class add_feed extends AppCompatActivity {
    public static  final  int  CAMERA_PERM_CODE =101;
    public static  final  int  CAMERA_REQUEST_CODE =101;
    ActivityResultLauncher<String> launcher;
    String url;
    StorageReference reference;
    ImageView img;
    ArrayList<Uri> imagelist;
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
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));
        txt = findViewById(R.id.txt);
        imagelist = new ArrayList<>();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String mo = sharedPreferences.getString("mo", "1234567890");
        img = findViewById(R.id.imgfeed);
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
                //ChooseImage();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri1 == null) {
                    show_toast(getResources().getString(R.string.Please_Enter_Image), false);
                    launcher.launch("image/*");
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
                                    firebaseDatabase.getReference().child("Feed").child(key).setValue(new clsFeedModel(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR), uri.toString(), textInputEditText.getText().toString(), key2, key, "1", sharedPreferences.getString("mo", "1234567890")));
                                    FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Feed").child(key2).setValue(new clsFeedModel(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR), uri.toString(), textInputEditText.getText().toString(), key2, key, "1", sharedPreferences.getString("mo", "1234567890"))).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            show_toast(getResources().getString(R.string.Upload_Successfully), true);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            show_toast(getResources().getString(R.string.Upload_Cancelled), false);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    show_toast(getResources().getString(R.string.Upload_Cancelled), false);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            show_toast(getResources().getString(R.string.Upload_Cancelled), false);
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
    }
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
    private void ChooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose Maximum 4 Image"),105);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==105&&resultCode==RESULT_OK&&data!=null){
            ClipData mClipData = data.getClipData();
            int cout = data.getClipData().getItemCount();
            if (cout <= 4 ){
                for (int i = 0; i < cout; i++) {
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    imagelist.add(imageurl);
                    System.out.println(imageurl.toString());
                }
                img.setImageURI(imagelist.get(2));
            }
            else{
                Toast.makeText(this, "Choose Maximum 4 Image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}