package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Locale;

public class Write_share extends AppCompatActivity {
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
        setContentView(R.layout.activity_write_share);
        setLanguage();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));
        txt = findViewById(R.id.txt);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String mo = sharedPreferences.getString("mo", "1234567890");
        RelativeLayout upload = findViewById(R.id.btnupload);
        RelativeLayout cancel = findViewById(R.id.btncancel);
        TextInputEditText textInputEditText = findViewById(R.id.edtdes);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.GONE);
                    String key = firebaseDatabase.getReference().child("Feed").push().getKey().toString();
                String key2 = firebaseDatabase.getReference().child("User").child(mo).child("Feed").push().getKey().toString();
                firebaseDatabase.getReference().child("Feed").child(key).setValue(new clsFeedModel(sharedPreferences.getString("url", "123"), sharedPreferences.getString("uname", "unknown"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),sharedPreferences.getString("url", "123"),textInputEditText.getText().toString(),key2, key,"3"));
                FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Feed").child(key2).setValue(new clsFeedModel(sharedPreferences.getString("url", "123"), sharedPreferences.getString("uname", "unknown"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),sharedPreferences.getString("url", "123"), textInputEditText.getText().toString(),key2, key,"3")).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);
                        Toast.makeText(Write_share.this, getResources().getString(R.string.Data_Added_Sucessfully), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

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

}
