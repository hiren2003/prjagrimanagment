package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Window;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_tools extends AppCompatActivity {
RecyclerView rctools;
ArrayList<clsToolsAccessoriesModel> arrayList;
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        rctools=findViewById(R.id.rctools);
        arrayList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Resell").child("Tools&Accessories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    arrayList.add(dataSnapshot.getValue(clsToolsAccessoriesModel.class));
                }
                rctools.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(admin_tools.this,true,arrayList);
                rctools.setAdapter(rcToolsAccesoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}