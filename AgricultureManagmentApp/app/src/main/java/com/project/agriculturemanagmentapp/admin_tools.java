package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class admin_tools extends AppCompatActivity {
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);
        RecyclerView recyclerView=findViewById(R.id.rctoolacce);
        FirebaseRecyclerOptions<clsToolsAccessoriesModel> options=new FirebaseRecyclerOptions.Builder<clsToolsAccessoriesModel>()
                .setQuery( FirebaseDatabase.getInstance().getReference().child("Tools&Accessories"),clsToolsAccessoriesModel.class)
                .build();
        rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(options,admin_tools.this,true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(rcToolsAccesoriesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcToolsAccesoriesAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcToolsAccesoriesAdapter.stopListening();
    }
}