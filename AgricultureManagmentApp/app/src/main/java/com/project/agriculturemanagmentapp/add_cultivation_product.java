package com.project.agriculturemanagmentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class add_cultivation_product extends AppCompatActivity {
String[] arrprdttp;
Spinner spntype;
ImageView imgprdt;
ArrayList<Uri> arrlsturi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cultivation_product);
        spntype=findViewById(R.id.category);
        imgprdt=findViewById(R.id.imgprdt);
        arrlsturi=new ArrayList<>();
        arrprdttp=getResources().getStringArray(R.array.arrprdttp);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arrprdttp);
spntype.setAdapter(adapter);
imgprdt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,201);
    }
});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if(requestCode==201){
         for(int i=1;i<data.getClipData().getItemCount();i++){
             arrlsturi.set(i,data.getClipData().getItemAt(i).getUri());
         }
     }
    }
}