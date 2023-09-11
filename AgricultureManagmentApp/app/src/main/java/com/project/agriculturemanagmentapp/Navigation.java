package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Navigation extends AppCompatActivity {
ImageView imgprfpc;
Button profile;
TextView txtuname,txtumo,close;
SharedPreferences sharedPreferences;
RelativeLayout rvlang,rvgv,rvrate,cous,rvshareapp,rvloout,rvtc,rvnews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.drawer));
        txtumo=findViewById(R.id.txtumo);
        txtuname=findViewById(R.id.txtuname);
        profile=findViewById(R.id.profile);
        rvnews=findViewById(R.id.rvnews);
        imgprfpc=findViewById(R.id.imgprfpc);
        rvlang=findViewById(R.id.rvlang);
        rvgv=findViewById(R.id.rvgv);
        rvrate=findViewById(R.id.rvrate);
        cous=findViewById(R.id.cous);
        rvshareapp=findViewById(R.id.rvshareapp);
        rvloout=findViewById(R.id.rvloout);
        rvtc=findViewById(R.id.rvtc);
        close=findViewById(R.id.close);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        String mo=sharedPreferences.getString("mo","1234567890");
        if(mo.equals("7229005896")||mo.equals("9824945298")||mo.equals("9879295483")||mo.equals("9737063396")){
            rvnews.setVisibility(View.VISIBLE);
        }
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
        rvnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this, admin_home.class));
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_toast("Thank-you For Rating us",true);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
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
    public void show_toast(String msg, boolean isgreen) {
        Toast ts = new Toast(getBaseContext());
        View view;
        if (isgreen) {
            view = getLayoutInflater().inflate(R.layout.lyttoastgreen, (ViewGroup) findViewById(R.id.container));
        } else {
            view = getLayoutInflater().inflate(R.layout.lyttoast, (ViewGroup) findViewById(R.id.container));
        }
        TextView txtmessage = view.findViewById(R.id.txtmsg);
        txtmessage.setText(msg);
        ts.setView(view);
        ts.setGravity(Gravity.TOP, 0, 30);
        ts.setDuration(Toast.LENGTH_SHORT);
        ts.show();
    }
}