package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class admin_resell_category extends AppCompatActivity {
    CardView cdanimal,cdagrprdt,cdta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_resell_category);
        cdanimal=findViewById(R.id.cdanimal);
        cdagrprdt=findViewById(R.id.cdagrprdt);
        cdta=findViewById(R.id.cdta);
        cdanimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_resell_category.this, admin_animal.class));
                finish();
            }
        });
        cdagrprdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_resell_category.this, admin_cultivation.class));
                finish();
            }
        });
        cdta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_resell_category.this, admin_tools.class));
                finish();
            }
        });
    }
}