package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class User_option extends AppCompatActivity {
String mo;
int type;
ImageView back;
CardView cdprofile,cdfeed,cdvacancy,cdworker,cdresell,cdcart,cdorder,cdcancelorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_option);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        Intent intent=getIntent();
        mo=intent.getStringExtra("mo");
        cdprofile=findViewById(R.id.cdprofile);
        cdfeed=findViewById(R.id.cdfeed);
        cdvacancy=findViewById(R.id.cdvacancy);
        cdworker=findViewById(R.id.cdworker);
        cdresell=findViewById(R.id.resell);
        back=findViewById(R.id.back);
        cdcart=findViewById(R.id.cdcart);
        cdorder=findViewById(R.id.cdorder);
        cdcancelorder=findViewById(R.id.cdcancelled);
        cdprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),EditprofileActivity.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cdfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        cdvacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",3);
                startActivity(intent);
            }
        });
        cdworker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",4);
                startActivity(intent);
            }
        });
        cdresell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",5);
                startActivity(intent);
            }
        });
        cdcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",6);
                startActivity(intent);
            }
        });
        cdorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",7);
                startActivity(intent);
            }
        });
        cdcancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), User_output.class);
                intent.putExtra("mo",mo);
                intent.putExtra("type",8);
                startActivity(intent);
            }
        });
    }
}