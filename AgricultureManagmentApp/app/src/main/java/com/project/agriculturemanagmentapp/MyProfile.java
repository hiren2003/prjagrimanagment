package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {
    ViewPager vpmy;
    TabLayout tbmy;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView txtuname;
    TextView txtfollowercount;
    ImageView imgprfpc;
    AppCompatButton profile,messager;
    SharedPreferences sharedPreferences;
    VpProfileAdapter VpProfileAdapter;
    boolean SelfAccount;
    Boolean isFollowing=false;
    Intent intent;
    String Mo="";
    AppCompatButton btnfollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
         viewPager=findViewById(R.id.vpfeed);
         tabLayout=findViewById(R.id.tbllytfeed);
         txtuname=findViewById(R.id.txtuname);
         imgprfpc=findViewById(R.id.imgprfpc);
         messager=findViewById(R.id.message);
         btnfollow=findViewById(R.id.btnfollowing);
        txtfollowercount=findViewById(R.id.txtfollowercount);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        profile=findViewById(R.id.profile);
        intent=getIntent();
         SelfAccount=intent.getBooleanExtra("selfaccount",false);
        Mo=intent.getStringExtra("mo");
        if (SelfAccount||sharedPreferences.getString("mo","null").equals(Mo)&&!SelfAccount){
             Mo=sharedPreferences.getString("mo","null");
             Glide.with(this)
                     .load(sharedPreferences.getString("url","null"))
                     .circleCrop()
                     .error(getDrawable(R.drawable.baseline_warning_24))
                     .into(imgprfpc);
             btnfollow.setVisibility(View.GONE);
            VpProfileAdapter=new VpProfileAdapter(getSupportFragmentManager(),this,Mo,true);
            viewPager.setAdapter(VpProfileAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(getDrawable(R.drawable.instagram));
            tabLayout.getTabAt(1).setIcon(getDrawable(R.drawable.suitcase));
            tabLayout.getTabAt(2).setIcon(getDrawable(R.drawable.labour));
            tabLayout.getTabAt(3).setIcon(getDrawable(R.drawable.resell));
             txtuname.setText(sharedPreferences.getString("uname","null"));
         }
         else{
             Mo=intent.getStringExtra("mo");
             profile.setVisibility(View.GONE);
            VpProfileAdapter=new VpProfileAdapter(getSupportFragmentManager(),this,Mo,false);
            viewPager.setAdapter(VpProfileAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setIcon(getDrawable(R.drawable.instagram));
            tabLayout.getTabAt(1).setIcon(getDrawable(R.drawable.suitcase));
            tabLayout.getTabAt(2).setIcon(getDrawable(R.drawable.resell));
         }

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
        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        txtfollowercount.setText(snapshot2.getChildrenCount()+" "+getString(R.string.Followers)+","+snapshot.getChildrenCount()+" "+getString(R.string.Following));
                        ArrayList<String> arrayList=new ArrayList<>();
                        for (DataSnapshot dt:snapshot2.getChildren()){
                            arrayList.add(dt.getValue().toString());
                        }
                        if (arrayList.contains(sharedPreferences.getString("mo","null"))){
                            btnfollow.setText(getString(R.string.Following));
                            isFollowing=true;
                        }
                        else{
                            btnfollow.setText(getString(R.string.Follow));
                            isFollowing=false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowing){
                    isFollowing=false;
                    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","null")).child("Following").child(Mo).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Followers").child(sharedPreferences.getString("mo","null")).removeValue();
                }
                else{
                    isFollowing=true;
                    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","null")).child("Following").child(Mo).setValue(Mo);
                    FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Followers").child(sharedPreferences.getString("mo","null")).setValue(sharedPreferences.getString("mo","null"));
            }
        }});
        txtfollowercount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfile.this, Show_Follower.class).putExtra("mo",Mo));

            }
        });
    }

}