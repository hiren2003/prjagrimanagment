package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

public class MyProfile extends AppCompatActivity {
ViewPager vpmy;
TabLayout tbmy;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView txtumo;
    TextView txtuname;
    ImageView imgprfpc;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
         viewPager=findViewById(R.id.vpfeed);
         tabLayout=findViewById(R.id.tbllytfeed);
         txtumo=findViewById(R.id.txtumo);
         txtuname=findViewById(R.id.txtuname);
         imgprfpc=findViewById(R.id.imgprfpc);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        Button profile=findViewById(R.id.profile);
        VpProfileAdapter vpAdapterFeed=new VpProfileAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(vpAdapterFeed);
        tabLayout.setupWithViewPager(viewPager);
        Glide.with(this)
                .load(sharedPreferences.getString("url","null"))
                .circleCrop()
                .error(getDrawable(R.drawable.baseline_warning_24))
                .into(imgprfpc);
        txtuname.setText(sharedPreferences.getString("uname","null"));
        txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this,EditprofileActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Glide.with(this)
                .load(sharedPreferences.getString("url","null"))
                .circleCrop()
                .error(getDrawable(R.drawable.baseline_warning_24))
                .into(imgprfpc);
        txtuname.setText(sharedPreferences.getString("uname","null"));
        txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
    }
}