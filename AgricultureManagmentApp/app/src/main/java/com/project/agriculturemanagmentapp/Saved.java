package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Saved extends AppCompatActivity {
RecyclerView rcsv;
LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        rcsv=findViewById(R.id.rcsv);
        lottieAnimationView=findViewById(R.id.loty3);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "134567890")).child("Saved").child("Feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsFeedModel> feedModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    feedModelArrayList.add(datasnapshot.getValue(clsFeedModel.class));
                }
                if (feedModelArrayList.isEmpty()){
                    lottieAnimationView.setVisibility(View.VISIBLE);
                }
                else{
                    lottieAnimationView.setVisibility(View.GONE);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    rcsv.setLayoutManager(linearLayoutManager);
                    RcFeedAdapter rcFeedAdapter =new RcFeedAdapter(Saved.this,false,false,feedModelArrayList);
                    rcsv.setAdapter(rcFeedAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}