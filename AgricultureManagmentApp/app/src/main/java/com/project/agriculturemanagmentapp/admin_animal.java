package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class admin_animal extends AppCompatActivity {
RcAnimalAdapter rcAnimalAdapter;
RecyclerView rcanimal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_animal);
        rcanimal=findViewById(R.id.rcanimal);
        FirebaseRecyclerOptions<clsAnimalModel> options=new FirebaseRecyclerOptions.Builder<clsAnimalModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("animals"), clsAnimalModel.class)
                .build();
        rcanimal.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcAnimalAdapter=new RcAnimalAdapter(options,admin_animal.this,true);
        rcanimal.setAdapter(rcAnimalAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcAnimalAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcAnimalAdapter.stopListening();
    }
}