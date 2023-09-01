package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class category_cultivation extends AppCompatActivity {
MaterialCardView rdbfruits,rdbpulses,rdbvegatable,rdbgrains,rdbothers;
Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_cultivation);
        rdbfruits=findViewById(R.id.rdbfruits);
        rdbgrains=findViewById(R.id.rdbgrains);
        rdbpulses=findViewById(R.id.rdbpulses);
        rdbvegatable=findViewById(R.id.rdbvegatable);
        rdbothers=findViewById(R.id.rdbothers);
        next=findViewById(R.id.btn);
        rdbgrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbgrains.setChecked(!rdbgrains.isChecked());
                rdbfruits.setChecked(false);
                rdbpulses.setChecked(false);
                rdbvegatable.setChecked(false);
                rdbothers.setChecked(false);
            }
        });
        rdbfruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbfruits.setChecked(!rdbfruits.isChecked());
                rdbothers.setChecked(false);
                rdbpulses.setChecked(false);
                rdbvegatable.setChecked(false);
                rdbgrains.setChecked(false);
            }
        });
        rdbpulses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbpulses.setChecked(!rdbpulses.isChecked());
                rdbothers.setChecked(false);
                rdbgrains.setChecked(false);
                rdbvegatable.setChecked(false);
                rdbfruits.setChecked(false);
            }
        });
        rdbvegatable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbvegatable.setChecked(!rdbvegatable.isChecked());
                rdbothers.setChecked(false);
                rdbpulses.setChecked(false);
                rdbgrains.setChecked(false);
                rdbfruits.setChecked(false);
            }
        });


        rdbothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbothers.setChecked(!rdbothers.isChecked());
                rdbgrains.setChecked(false);
                rdbpulses.setChecked(false);
                rdbvegatable.setChecked(false);
                rdbfruits.setChecked(false);

            }
        });

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!rdbothers.isChecked() && !rdbfruits.isChecked() && !rdbgrains.isChecked() && !rdbpulses.isChecked() && !rdbvegatable.isChecked()){
                Toast toast=Toast.makeText(getApplicationContext(),"Please select category",Toast.LENGTH_SHORT);
            }else if (rdbgrains.isChecked()) {
                startActivity(new Intent(category_cultivation.this, add_cultivation_product.class).putExtra("category", 1));
            } else if (rdbfruits.isChecked()) {
                startActivity(new Intent(category_cultivation.this,add_cultivation_product.class).putExtra("category",2));
            }else if(rdbpulses.isChecked()){
                startActivity(new Intent(category_cultivation.this, add_cultivation_product.class).putExtra("category", 3));
            } else if (rdbvegatable.isChecked()) {
                startActivity(new Intent(category_cultivation.this, add_cultivation_product.class).putExtra("category", 4));
            }else if (rdbothers.isChecked()){
                startActivity(new Intent(category_cultivation.this, add_cultivation_product.class).putExtra("category", 0));
            }
        }

    });



    }


        @Override
        public void onBackPressed() {
            startActivity(new Intent(category_cultivation.this,Resell_Category.class));
            finish();
        }


}