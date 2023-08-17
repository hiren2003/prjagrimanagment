package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class news extends AppCompatActivity {
    RcnewsAdapter rcnewsAdapter;
    ExtendedFloatingActionButton fltadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView recyclerView = findViewById(R.id.rcnews);
        fltadd=findViewById(R.id.fltaddnews);
        FirebaseRecyclerOptions<clsNewsModel> options = new FirebaseRecyclerOptions.Builder<clsNewsModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("news"), clsNewsModel.class)
                .build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rcnewsAdapter = new RcnewsAdapter(options, getBaseContext());
        recyclerView.setAdapter(rcnewsAdapter);
    fltadd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(news.this, add_news.class));
        }
    });
    }
    @Override
    public void onStart() {
        super.onStart();
        rcnewsAdapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rcnewsAdapter.stopListening();
    }

}