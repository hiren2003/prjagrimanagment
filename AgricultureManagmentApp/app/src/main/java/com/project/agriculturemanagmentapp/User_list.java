package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_list extends AppCompatActivity {
    RecyclerView rcuser;
    RcuserAdapter rcuserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        rcuser = findViewById(R.id.rcuser);
        FirebaseDatabase.getInstance().getReference().child("Users_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsUserModel> userModelArrayList=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    userModelArrayList.add(dataSnapshot.getValue(clsUserModel.class));
                }
                rcuserAdapter = new RcuserAdapter( getBaseContext(),userModelArrayList,true);
                rcuser.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rcuser.setAdapter(rcuserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}