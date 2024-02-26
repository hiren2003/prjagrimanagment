package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class news extends AppCompatActivity {
    RcnewsAdapter rcnewsAdapter;
    ExtendedFloatingActionButton fltadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));


        RecyclerView recyclerView = findViewById(R.id.rcnews);
        fltadd=findViewById(R.id.fltaddnews);

        FirebaseDatabase.getInstance().getReference().child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsNewsModel> newsModelArrayList=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    newsModelArrayList.add(datasnapshot.getValue(clsNewsModel.class));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                rcnewsAdapter = new RcnewsAdapter(news.this,true,newsModelArrayList);
                recyclerView.setAdapter(rcnewsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    fltadd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(news.this, add_news.class));
        }
    });
    }
}