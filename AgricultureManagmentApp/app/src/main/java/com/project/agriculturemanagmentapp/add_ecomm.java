package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class add_ecomm extends AppCompatActivity {
    Uri selectedimg;
    Button btnchooseimg;
    ImageView imgprdt;
    Button btnsavedata;
    Spinner spntype;
    TextInputEditText edtdes,edtspe,edtrecomm,edtprice,edtpname;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ecomm);
        spntype=findViewById(R.id.category);
        btnchooseimg=findViewById(R.id.btnchooseimage);
        btnsavedata=findViewById(R.id.btnsavedata);
        imgprdt=findViewById(R.id.imgprdt);
        edtdes=findViewById(R.id.edtdes);
        edtspe=findViewById(R.id.edtspe);
        edtrecomm=findViewById(R.id.edtspe);
        edtpname=findViewById(R.id.edtpname);
        edtprice=findViewById(R.id.edtprc);
        ActivityResultLauncher<String> launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgprdt.setImageURI(result);
                imgprdt.setVisibility(View.VISIBLE);
                selectedimg=result;
            }
        });
        btnchooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        String [] arr=getResources().getStringArray(R.array.arrcategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr);
        spntype.setAdapter(adapter);
        btnsavedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key= FirebaseDatabase.getInstance().getReference().child("ECommerce").push().getKey();
                StorageReference reference= FirebaseStorage.getInstance().getReference().child("Ecommerce").child(key);
                reference.putFile(selectedimg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(key).setValue(new clsEcommModel(key,edtpname.getText().toString(),uri.toString(),edtprice.getText().toString(),edtspe.getText().toString(),edtdes.getText().toString(),edtrecomm.getText().toString(),"0"));
                                FirebaseDatabase.getInstance().getReference().child("ECommerce").child(spntype.getSelectedItem().toString()).child(key).setValue(new clsEcommModel(key,edtpname.getText().toString(),uri.toString(),edtprice.getText().toString(),edtspe.getText().toString(),edtdes.getText().toString(),edtrecomm.getText().toString(),"0"));
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}