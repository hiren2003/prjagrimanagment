package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class show_ecom_prdt extends AppCompatActivity {
    String key;
    ImageView imageView;
    TextView txtpname, txtprice, txtspec, txtdes, txtrecom;
    ImageButton btncart, btnrmcart, btnorder, btncancelorder;
    clsEcommModel model;
    TextInputEditText edtqty;
    int btn = 0;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ecom_prdt);
        imageView = findViewById(R.id.imgprdt);
        txtpname = findViewById(R.id.txtpname);
        txtprice = findViewById(R.id.txtprice);
        txtspec = findViewById(R.id.txtspec);
        txtdes = findViewById(R.id.txtdec);
        btncart = findViewById(R.id.btncart);
        edtqty=findViewById(R.id.edtqty2);
        txtrecom = findViewById(R.id.txtrecom);
        btnrmcart = findViewById(R.id.btnremovecart);
        btnorder = findViewById(R.id.btnorder);
        btncancelorder = findViewById(R.id.btncancelorder);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        btn = intent.getIntExtra("btn", 0);
        if (btn == 1) {
            btncart.setVisibility(View.VISIBLE);
        } else if (btn == 2) {
            btnorder.setVisibility(View.VISIBLE);
            edtqty.setVisibility(View.VISIBLE);
            btnrmcart.setVisibility(View.VISIBLE);
        } else if (btn == 3) {
            btncancelorder.setVisibility(View.VISIBLE);
            edtqty.setVisibility(View.VISIBLE);
        }
        reference = FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(clsEcommModel.class);
                Glide.with(show_ecom_prdt.this)
                                .load(model.getImg())
                        .into(imageView);
                txtpname.setText(model.getPname());
                txtprice.setText("₹"+model.getPrice());
                txtdes.setText(model.getDes());
                txtspec.setText(model.getDpec());
                txtrecom.setText(model.getRecomm());
                edtqty.setText(model.getQty());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.qty=edtqty.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(model.getKey()).setValue(model);
                finish();
            }
        });
        btnrmcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(model.getKey()).removeValue();
                finish();
            }
        });
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.qty=edtqty.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String date = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(java.util.Calendar.MONTH) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Myorder").child(model.getKey()).setValue(model);
                FirebaseDatabase.getInstance().getReference().child("Orders").child(date).child(model.getKey()).setValue(model);
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(model.getKey()).removeValue();
                finish();            }
        });
        btncancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);
                String date = day + "/" + month + "/" + year;
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Myorder").child(model.getKey()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Orders").child(date.toString()).child(model.getKey()).removeValue();
                finish();
            }
        });
    }
}