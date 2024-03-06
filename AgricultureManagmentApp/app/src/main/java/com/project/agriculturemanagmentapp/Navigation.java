package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Navigation extends AppCompatActivity {
ImageView imgprfpc,back;
Button rvloout;
TextView txtuname,txtumo,close;
SharedPreferences sharedPreferences;
RelativeLayout rvlang,rvgv,rvrate,cous,rvshareapp,rvtc,rvnews,rvsave,profile,imgorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));
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
        back=findViewById(R.id.back);
        rvsave=findViewById(R.id.rvsave);
        imgorder=findViewById(R.id.imgorder);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        String mo=sharedPreferences.getString("mo","1234567890");
        FirebaseDatabase.getInstance().getReference("/Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,String> map=new HashMap<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    map.put(datasnapshot.getKey().toString(),datasnapshot.getValue().toString());
                    if (datasnapshot.getKey().toString().equals(sharedPreferences.getString("mo",""))){
                        rvnews.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Glide.with(this)
                .load(sharedPreferences.getString("url","null"))
                .circleCrop()
                .error(getDrawable(R.drawable.baseline_warning_24))
                .into(imgprfpc);
        txtuname.setText(sharedPreferences.getString("uname","null"));
        txtumo.setText("+91 "+sharedPreferences.getString("mo","null"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this,Language.class));
                finish();
            }
        });
        imgorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this,MyOrder.class));
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
                sedit.clear();
                sedit.commit();
                sedit.putBoolean("islogin",false);
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
                startActivity(new Intent(Navigation.this,MyProfile.class).putExtra("selfaccount",true));
            }
        });
        rvnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Navigation.this, admin_home.class));
            }
        });
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        rvrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_toast("Thank-you For Rating us",true);
            }
        });
        rvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Navigation.this, Saved.class));
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
            view = getLayoutInflater().inflate(R.layout.lyt_green_toast, (ViewGroup) findViewById(R.id.container));
        } else {
            view = getLayoutInflater().inflate(R.layout.lyt_red_toast, (ViewGroup) findViewById(R.id.container));
        }
        TextView txtmessage = view.findViewById(R.id.txtmsg);
        txtmessage.setText(msg);
        ts.setView(view);
        ts.setGravity(Gravity.TOP, 0, 30);
        ts.setDuration(Toast.LENGTH_SHORT);
        ts.show();
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