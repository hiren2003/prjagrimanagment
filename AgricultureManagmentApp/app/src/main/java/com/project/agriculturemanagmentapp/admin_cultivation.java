package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class admin_cultivation extends AppCompatActivity {
RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cultivation);
        RecyclerView rccprdt=findViewById(R.id.rccprdt);
        FirebaseRecyclerOptions<ClsCultivationProductModel> options=new FirebaseRecyclerOptions.Builder<ClsCultivationProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Cultivation Product"), ClsCultivationProductModel.class)
                .build();
        rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(options,admin_cultivation.this,true);
        rccprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rccprdt.setAdapter(rcCultivatonPrdtAdpter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcCultivatonPrdtAdpter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcCultivatonPrdtAdpter.stopListening();
    }
}