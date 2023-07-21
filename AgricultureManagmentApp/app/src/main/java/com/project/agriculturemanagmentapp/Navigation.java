package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Navigation extends AppCompatActivity {
ImageView imgprfpc;
TextView txtuname,txtumo;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        txtumo=findViewById(R.id.txtumo);
        txtuname=findViewById(R.id.txtuname);
        imgprfpc=findViewById(R.id.imgprfpc);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        Glide.with(this)
                .load(sharedPreferences.getString("url","null"))
                .circleCrop()
                .error(getDrawable(R.drawable.baseline_warning_24))
                .into(imgprfpc);
        txtuname.setText(sharedPreferences.getString("uname","null"));
        txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
    }
}