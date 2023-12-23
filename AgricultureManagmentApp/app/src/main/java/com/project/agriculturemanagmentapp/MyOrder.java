package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity {
RcorderAdapter rcorderAdapter;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        RecyclerView rcprdt=findViewById(R.id.rccprdt);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
    FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Myorder").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
            for (DataSnapshot datasnapshot:
                 snapshot.getChildren()) {
                orderModelArrayList.add(datasnapshot.getValue(clsOrderModel.class));
            }
            rcorderAdapter=new RcorderAdapter(MyOrder.this,orderModelArrayList);
            rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            rcprdt.setAdapter(rcorderAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }
}