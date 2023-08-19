package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class admin_home extends AppCompatActivity {
CardView cduser,cdnews,cdgov,cdorder,cdaddprdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        cduser=findViewById(R.id.user);
        cdnews=findViewById(R.id.addnews);
        cdgov=findViewById(R.id.addgovscheme);
        cdorder=findViewById(R.id.orderlist);
        cdaddprdt=findViewById(R.id.addprdt);
        cduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this,User_list.class));
            }
        });
        cdnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this,news.class));
            }
        });
        cdgov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, govermentScheme.class));
            }
        });
        cdorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(admin_home.this, orderbydate.class));
            }
        });
        cdaddprdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, add_ecomm.class));}
        });
    }
}