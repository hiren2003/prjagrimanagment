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

public class admin_cultivation extends AppCompatActivity {
RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cultivation);
        RecyclerView rccprdt=findViewById(R.id.rccprdt);
        FirebaseDatabase.getInstance().getReference("Cultivation Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ClsCultivationProductModel> clsCultivationProductModelArrayList=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    clsCultivationProductModelArrayList.add(datasnapshot.getValue(ClsCultivationProductModel.class));
                }
                rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(admin_cultivation.this,true,clsCultivationProductModelArrayList);
                rccprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rccprdt.setAdapter(rcCultivatonPrdtAdpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}