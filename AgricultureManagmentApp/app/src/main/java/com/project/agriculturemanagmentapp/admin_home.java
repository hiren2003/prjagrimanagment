package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_home extends AppCompatActivity {
    CardView cduser, cdnews, cdgov, cdorder, cdaddprdt,eshop,cdanimal;
    int TotalOrder=0,TotalDays=0,ModeCode=0,ModeOnline=0;
    float TotalAmount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        cduser = findViewById(R.id.user);
        cdnews = findViewById(R.id.addnews);
        cdgov = findViewById(R.id.addgovscheme);
        cdorder = findViewById(R.id.orderlist);
        cdanimal=findViewById(R.id.cdanimal);
        cdaddprdt = findViewById(R.id.addprdt);
        eshop = findViewById(R.id.ecomprdt);
        cduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, User_list.class));
            }
        });
        cdnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, news.class));
            }
        });
        cdgov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, govermentScheme.class).putExtra("type", 1));
            }
        });
        cdanimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, admin_resell_category.class));
            }
        });
        eshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, admin_ecom.class));
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
                startActivity(new Intent(admin_home.this, add_ecomm.class));
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    TotalDays++;
                    for (DataSnapshot childdataSnapshot:dataSnapshot.getChildren()){
                        TotalOrder++;
                        clsOrderModel orderModel=childdataSnapshot.getValue(clsOrderModel.class);
                        TotalAmount+=(Float.parseFloat(orderModel.getQty())*Float.parseFloat(orderModel.getClsEcommModel().getPrice()));
                        if(orderModel.getPaymentMode().equals("COD")){
                            ModeCode++;
                        }
                        else{
                            ModeOnline++;
                        }
                    }
                }
                System.out.println("----------------"+TotalAmount);
                System.out.println("----------------"+TotalOrder);
                System.out.println("----------------"+TotalDays);
                System.out.println("----------------"+ModeCode);
                System.out.println("----------------"+ModeOnline);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}