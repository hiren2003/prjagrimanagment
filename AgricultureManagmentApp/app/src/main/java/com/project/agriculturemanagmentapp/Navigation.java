package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Navigation extends AppCompatActivity {
ImageView imgprfpc,profile;
TextView txtuname,txtumo;
SharedPreferences sharedPreferences;
RelativeLayout rvlang,rvgv,rvrate,cous,rvshareapp,rvloout,rvtc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.green));
        txtumo=findViewById(R.id.txtumo);
        txtuname=findViewById(R.id.txtuname);
        profile=findViewById(R.id.profile);
        imgprfpc=findViewById(R.id.imgprfpc);
        rvlang=findViewById(R.id.rvlang);
        rvgv=findViewById(R.id.rvgv);
        rvrate=findViewById(R.id.rvrate);
        cous=findViewById(R.id.cous);
        rvshareapp=findViewById(R.id.rvshareapp);
        rvloout=findViewById(R.id.rvloout);
        rvtc=findViewById(R.id.rvtc);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        Glide.with(this)
                .load(sharedPreferences.getString("url","null"))
                .circleCrop()
                .error(getDrawable(R.drawable.baseline_warning_24))
                .into(imgprfpc);
        txtuname.setText(sharedPreferences.getString("uname","null"));
        txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
        rvlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this,Language.class));
                finish();
            }
        });
        rvgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this, govermentScheme.class));
                finish();            }
        });
        rvtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this, Term_Conditions.class));
            }
        });
        rvloout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sedit=sharedPreferences.edit();
                sedit.putBoolean("islogin",false);
                sedit.commit();
                sedit.apply();
                startActivity(new Intent(Navigation.this, splashActivity.class));
                Navigation.this.finish();
                System.exit(0);
            }
        });
        cous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this, Contact_us.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation.this,EditprofileActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(sharedPreferences.getString("url","null"))
                .circleCrop()
                .error(getDrawable(R.drawable.baseline_warning_24))
                .into(imgprfpc);
        txtuname.setText(sharedPreferences.getString("uname","null"));
        txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
    }
}