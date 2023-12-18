package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {
ViewPager vpmy;
TabLayout tbmy;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView txtumo;
    TextView txtuname;
    ImageView imgprfpc,profile,messager;
    SharedPreferences sharedPreferences;
    VpProfileAdapter VpProfileAdapter;
    boolean SelfAccount;
    Intent intent;
    String Mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
         viewPager=findViewById(R.id.vpfeed);
         tabLayout=findViewById(R.id.tbllytfeed);
         txtumo=findViewById(R.id.txtumo);
         txtuname=findViewById(R.id.txtuname);
         imgprfpc=findViewById(R.id.imgprfpc);
         messager=findViewById(R.id.message);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        profile=findViewById(R.id.profile);
         intent=getIntent();
         SelfAccount=intent.getBooleanExtra("selfaccount",false);
         if (SelfAccount){
             Mo=sharedPreferences.getString("mo","null");
             Glide.with(this)
                     .load(sharedPreferences.getString("url","null"))
                     .circleCrop()
                     .error(getDrawable(R.drawable.baseline_warning_24))
                     .into(imgprfpc);
             txtuname.setText(sharedPreferences.getString("uname","null"));
             txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
             VpProfileAdapter=new VpProfileAdapter(getSupportFragmentManager(),this,Mo,true);
         }
         else{
             Mo=intent.getStringExtra("mo");
             profile.setVisibility(View.GONE);
             VpProfileAdapter=new VpProfileAdapter(getSupportFragmentManager(),this,Mo,false);
         }
        viewPager.setAdapter(VpProfileAdapter);
        tabLayout.setupWithViewPager(viewPager);
             profile.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (SelfAccount){
                         startActivity(new Intent(MyProfile.this,EditprofileActivity.class));
                     }
                 }
             });
         messager.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                     startActivity(new Intent(MyProfile.this,chat.class).putExtra("rmo",Mo));

             }
         });
        FirebaseDatabase.getInstance().getReference().child("Users_List").child(Mo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsUserModel clsUserModel = snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                Glide.with(MyProfile.this)
                        .load(clsUserModel.getUrl())
                        .circleCrop()
                        .into(imgprfpc);
                txtuname.setText(clsUserModel.getUname());
                txtumo.setText("+91"+clsUserModel.getMo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}