package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_animal extends AppCompatActivity {
RcAnimalAdapter rcAnimalAdapter;
RecyclerView rcanimal;
ArrayList<clsAnimalModel> arrayList;
LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_animal);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        rcanimal=findViewById(R.id.rcanimal);
        lottieAnimationView=findViewById(R.id.loty3);
        arrayList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Resell").child("animals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    arrayList.add(dataSnapshot.getValue(clsAnimalModel.class));
                }
               if (arrayList.isEmpty()){
                   lottieAnimationView.setVisibility(View.VISIBLE);
               }else{
                   lottieAnimationView.setVisibility(View.GONE);
                   rcanimal.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                   rcAnimalAdapter=new RcAnimalAdapter(admin_animal.this,false,true,arrayList);
                   rcanimal.setAdapter(rcAnimalAdapter);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}