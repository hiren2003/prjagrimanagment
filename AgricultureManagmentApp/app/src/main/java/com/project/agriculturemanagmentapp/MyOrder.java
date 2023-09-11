package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MyOrder extends AppCompatActivity {
RcorderAdapter rcorderAdapter;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        RecyclerView rcprdt=findViewById(R.id.rccprdt);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        FirebaseRecyclerOptions<clsOrderModel> options=new FirebaseRecyclerOptions.Builder<clsOrderModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Myorder"), clsOrderModel.class)
                .build();
        rcorderAdapter=new RcorderAdapter(options,MyOrder.this);
        rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcprdt.setAdapter(rcorderAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcorderAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcorderAdapter.stopListening();
    }
}