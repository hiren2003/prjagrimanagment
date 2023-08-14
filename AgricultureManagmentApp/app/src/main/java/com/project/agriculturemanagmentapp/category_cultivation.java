package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class category_cultivation extends AppCompatActivity {
CheckBox rdbfruits,rdbgrains,rdbpulses,rdbvegatable,rdbothers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_cultivation);
        rdbfruits=findViewById(R.id.rdbfruits);
        rdbgrains=findViewById(R.id.rdbgrains);
        rdbpulses=findViewById(R.id.rdbpulses);
        rdbvegatable=findViewById(R.id.rdbvegatable);
        rdbothers=findViewById(R.id.rdbothers);
        rdbvegatable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!rdbvegatable.isChecked()){
                    uncheck();
                }
                else{
                    uncheck();
                    rdbvegatable.setChecked(true);
                    startActivity(new Intent(category_cultivation.this,add_cultivation_product.class).putExtra("category",4));
                }
            }
        });
        rdbgrains.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!rdbgrains.isChecked()){
                    uncheck();
                }
                else{
                    uncheck();
                    rdbgrains.setChecked(true);
                    startActivity(new Intent(category_cultivation.this,add_cultivation_product.class).putExtra("category",1));
                }
            }
        }); rdbothers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!rdbothers.isChecked()){
                    uncheck();
                }
                else{
                    uncheck();
                    rdbothers.setChecked(true);
                    startActivity(new Intent(category_cultivation.this,add_cultivation_product.class).putExtra("category",0));
                }
            }
        }); rdbpulses.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!rdbpulses.isChecked()){
                    uncheck();
                }
                else{
                    uncheck();
                    rdbpulses.setChecked(true);
                    startActivity(new Intent(category_cultivation.this,add_cultivation_product.class).putExtra("category",3));
                }
            }
        }); rdbfruits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!rdbfruits.isChecked()){
                    uncheck();
                }
                else{
                    uncheck();
                    rdbfruits.setChecked(true);
                    startActivity(new Intent(category_cultivation.this,add_cultivation_product.class).putExtra("category",4));
                }
            }
        });

    }
    public void uncheck(){
        rdbothers.setChecked(false);
        rdbvegatable.setChecked(false);
        rdbpulses.setChecked(false);
        rdbgrains.setChecked(false);
        rdbfruits.setChecked(false);

    }
}