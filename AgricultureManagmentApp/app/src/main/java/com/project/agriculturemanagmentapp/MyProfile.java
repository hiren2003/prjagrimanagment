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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ViewPager viewPager=findViewById(R.id.vpfeed);
        TabLayout tabLayout=findViewById(R.id.tbllytfeed);
        TextView txtumo=findViewById(R.id.txtumo);
        TextView txtuname=findViewById(R.id.txtuname);
        ImageView imgprfpc=findViewById(R.id.imgprfpc);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
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
}