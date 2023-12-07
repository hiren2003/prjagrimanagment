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

public class admin_tools extends AppCompatActivity {
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);
        RecyclerView recyclerView=findViewById(R.id.rctoolacce);
        FirebaseDatabase.getInstance().getReference().child("Tools&Accessories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsToolsAccessoriesModel> toolsAccessoriesModelArrayList =new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    toolsAccessoriesModelArrayList.add(datasnapshot.getValue(clsToolsAccessoriesModel.class));
                }
                rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(admin_tools.this,true,toolsAccessoriesModelArrayList);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(rcToolsAccesoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}