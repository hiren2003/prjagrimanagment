package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Show_Follower extends AppCompatActivity {
ViewPager vpfollow;
TabLayout tbfollow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_follower);
        Intent intent=getIntent();
        tbfollow=findViewById(R.id.tbfollow);
        vpfollow=findViewById(R.id.vpfollow);
        VpFollowerAdapter vpFollowerAdapter=new VpFollowerAdapter(getSupportFragmentManager(),intent.getStringExtra("mo"),getApplicationContext());
        vpfollow.setAdapter(vpFollowerAdapter);
        tbfollow.setupWithViewPager(vpfollow);

    }
}