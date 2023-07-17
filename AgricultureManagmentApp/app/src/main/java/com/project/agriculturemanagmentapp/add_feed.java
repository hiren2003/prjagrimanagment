package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class add_feed extends AppCompatActivity {
    ActivityResultLauncher<String> launcher;
    String url;
    StorageReference reference;
    Uri uri1;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        String mo=sharedPreferences.getString("mo","1234567890");
        ImageView img=findViewById(R.id.imgfeed);
        RelativeLayout upload=findViewById(R.id.btnupload);
        TextInputEditText textInputEditText=findViewById(R.id.edtdes);
        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                img.setImageURI(result);
                uri1=result;
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
                reference=firebaseStorage.getReference().child("feedimg");
                reference.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String key=firebaseDatabase.getReference().child("Feed").push().getKey().toString();
                                firebaseDatabase.getReference().child("Feed").child(key).setValue(new clsFeedModel(url, sharedPreferences.getString("uname","unknown"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"/"+ Calendar.getInstance().get(Calendar.MONTH)+"/"+ Calendar.getInstance().get(Calendar.YEAR),url,textInputEditText.getText().toString(),key));
                            }
                        });
                    }
                });
            }
        });
    }

}