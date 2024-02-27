package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class animal_category_Activity extends AppCompatActivity {
Button btn;
MaterialCardView sheep,cow,buffalo,goat,ox,chicken,hourse,camel,other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_category);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        btn=findViewById(R.id.btn);
        sheep=findViewById(R.id.sheep);
        cow=findViewById(R.id.cow);
        buffalo=findViewById(R.id.buffalo);
        goat=findViewById(R.id.goat);
        ox=findViewById(R.id.ox);
        chicken=findViewById(R.id.chicken);
        hourse=findViewById(R.id.horse);
        camel=findViewById(R.id.camel);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cow.isChecked() && !sheep.isChecked() && !buffalo.isChecked() && !goat.isChecked() && !ox.isChecked() && !chicken.isChecked() && !hourse.isChecked() && !camel.isChecked()){
                    Toast toast=Toast.makeText(getApplicationContext(),"Please select category",Toast.LENGTH_SHORT);
                }else if (cow.isChecked()) {
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 1));
                } else if (sheep.isChecked()) {
                    startActivity(new Intent(animal_category_Activity.this,add_animal.class).putExtra("category",2));
                }else if(buffalo.isChecked()){
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 3));
                } else if (goat.isChecked()) {
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 4));
                }else if (ox.isChecked()){
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 5));
                }else if (chicken.isChecked()){
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 6));
                }else if (hourse.isChecked()){
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 7));
                }else if (camel.isChecked()){
                    startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 8));
                }

            }
        });
        cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 1));
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
                startActivity(new Intent(animal_category_Activity.this,add_animal.class).putExtra("category",2));
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
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 3));
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
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 4));
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
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 5));
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
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 6));
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
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 7));
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
                startActivity(new Intent(animal_category_Activity.this, add_animal.class).putExtra("category", 8));
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(animal_category_Activity.this,Resell_Category.class));
        finish();
    }
}