package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

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
                String key=FirebaseDatabase.getInstance().getReference().child("Gov_scheme").push().getKey();
                StorageReference reference =  FirebaseStorage.getInstance().getReference().child("Gov_scheme").child(key);
               reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                           FirebaseDatabase.getInstance().getReference().child("Gov_scheme").child(key).setValue(new clsgovmodel(key,edtname.getText().toString(),uri.toString(),states.getSelectedItem().toString(),edtdes.getText().toString(),edturl.getText().toString()));
                            finish();
                            }
                        });
                    }
                });
            }
        });
    }
}