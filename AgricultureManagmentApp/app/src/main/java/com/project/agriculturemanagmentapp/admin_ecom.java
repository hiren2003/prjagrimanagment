package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class admin_ecom extends AppCompatActivity {
RcEcommAdapter rcEcommAdapter;
RecyclerView rcprdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ecom);
        rcprdt=findViewById(R.id.rccprdt);
        FirebaseRecyclerOptions<clsEcommModel> options=new FirebaseRecyclerOptions.Builder<clsEcommModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All"), clsEcommModel.class)
                .build();
        rcEcommAdapter=new RcEcommAdapter(options,admin_ecom.this,4);
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