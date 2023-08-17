package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class User_list extends AppCompatActivity {
    RecyclerView rcuser;
    RcuserAdapter rcuserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        rcuser = findViewById(R.id.rcuser);
        FirebaseRecyclerOptions<clsUserModel> options = new FirebaseRecyclerOptions.Builder<clsUserModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users_List"), clsUserModel.class)
                .build();
        rcuserAdapter = new RcuserAdapter(options, getBaseContext());
        rcuser.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcuser.setAdapter(rcuserAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcuserAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcuserAdapter.stopListening();
    }
}