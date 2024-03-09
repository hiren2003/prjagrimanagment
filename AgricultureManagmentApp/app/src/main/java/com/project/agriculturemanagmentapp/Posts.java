package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Posts extends AppCompatActivity {
RecyclerView rcposts;
String Mo;
int position;
boolean SelfAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        rcposts=findViewById(R.id.rcposts);
        Intent intent=getIntent();
        Mo=intent.getStringExtra("mo");
        position=intent.getIntExtra("position",1);
        SelfAccount=intent.getBooleanExtra("SelfAccount",false);

        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsFeedModel> feedModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    feedModelArrayList.add(datasnapshot.getValue(clsFeedModel.class));
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Posts.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                RcFeedAdapter rcFeedAdapter=new RcFeedAdapter(Posts.this,SelfAccount,feedModelArrayList);
                rcposts.setLayoutManager(linearLayoutManager);
                rcposts.setAdapter(rcFeedAdapter);
                rcposts.getLayoutManager().scrollToPosition(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}