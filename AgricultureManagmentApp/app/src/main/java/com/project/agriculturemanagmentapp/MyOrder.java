package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MyOrder extends AppCompatActivity {
RcEcommAdapter rcEcommAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setContentView(R.layout.activity_cart);
        RecyclerView rcprdt=findViewById(R.id.rccprdt);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        FirebaseRecyclerOptions<clsEcommModel> options=new FirebaseRecyclerOptions.Builder<clsEcommModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Myorder"), clsEcommModel.class)
                .build();
        rcEcommAdapter=new RcEcommAdapter(options,MyOrder.this,3);
        rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcprdt.setAdapter(rcEcommAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcEcommAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcEcommAdapter.stopListening();
    }
}