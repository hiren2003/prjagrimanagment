package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;

public class animal_category_Activity extends AppCompatActivity {
Button btn;
MaterialCardView sheep,cow,buffalo,goat,ox,chicken,hourse,camel,other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_category);
        btn=findViewById(R.id.btn);
        sheep=findViewById(R.id.sheep);
        cow=findViewById(R.id.cow);
        buffalo=findViewById(R.id.buffalo);
        goat=findViewById(R.id.goat);
        ox=findViewById(R.id.ox);
        chicken=findViewById(R.id.chicken);
        hourse=findViewById(R.id.horse);
        camel=findViewById(R.id.camel);
//        other=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(animal_category_Activity.this, add_animal.class));
                finish();
            }
        });
        cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cow.setChecked(!cow.isChecked());
                sheep.setChecked(false);
                buffalo.setChecked(false);
                goat.setChecked(false);
                hourse.setChecked(false);
                camel.setChecked(false);
                ox.setChecked(false);
                chicken.setChecked(false);
            }
        });
        sheep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheep.setChecked(!sheep.isChecked());
                buffalo.setChecked(false);
                goat.setChecked(false);
                hourse.setChecked(false);
                camel.setChecked(false);
                ox.setChecked(false);
                chicken.setChecked(false);
                cow.setChecked(false);
            }
        });
        buffalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buffalo.setChecked(!buffalo.isChecked());
                sheep.setChecked(false);
                goat.setChecked(false);
                hourse.setChecked(false);
                camel.setChecked(false);
                ox.setChecked(false);
                chicken.setChecked(false);
                cow.setChecked(false);
            }
        });
        goat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goat.setChecked(!goat.isChecked());
                buffalo.setChecked(false);
                sheep.setChecked(false);
                hourse.setChecked(false);
                camel.setChecked(false);
                ox.setChecked(false);
                chicken.setChecked(false);
                cow.setChecked(false);
            }
        });
        ox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ox.setChecked(!ox.isChecked());
                buffalo.setChecked(false);
                goat.setChecked(false);
                hourse.setChecked(false);
                camel.setChecked(false);
                sheep.setChecked(false);
                chicken.setChecked(false);
                cow.setChecked(false);
            }
        });
        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chicken.setChecked(!chicken.isChecked());
                buffalo.setChecked(false);
                goat.setChecked(false);
                hourse.setChecked(false);
                camel.setChecked(false);
                ox.setChecked(false);
                sheep.setChecked(false);
                cow.setChecked(false);
            }
        });
        hourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourse.setChecked(!hourse.isChecked());
                buffalo.setChecked(false);
                goat.setChecked(false);
                sheep.setChecked(false);
                camel.setChecked(false);
                ox.setChecked(false);
                chicken.setChecked(false);
                cow.setChecked(false);
            }
        });
        camel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camel.setChecked(!camel.isChecked());
                buffalo.setChecked(false);
                goat.setChecked(false);
                hourse.setChecked(false);
                sheep.setChecked(false);
                ox.setChecked(false);
                chicken.setChecked(false);
                cow.setChecked(false);
            }
        });

    }
}