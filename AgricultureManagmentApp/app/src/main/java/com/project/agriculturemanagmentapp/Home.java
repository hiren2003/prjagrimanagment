package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Home extends AppCompatActivity {
MeowBottomNavigation btmnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btmnv=findViewById(R.id.btmnv);
        btmnv.show(3,true);
        btmnv.add(new MeowBottomNavigation.Model(1,R.drawable.instagram));
        btmnv.add(new MeowBottomNavigation.Model(2, R.drawable.labour));
        btmnv.add(new MeowBottomNavigation.Model(3,R.drawable.house));
        btmnv.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_sell_24));
        btmnv.add(new MeowBottomNavigation.Model(5,R.drawable.online_store));

    }
}