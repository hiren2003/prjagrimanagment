package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class govermentScheme extends AppCompatActivity {
    RecyclerView rc;
    ExtendedFloatingActionButton addscheme;
    RcGovAdapter rcGovAdapter;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goverment_scheme);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        rc = findViewById(R.id.rcgov);
        addscheme = findViewById(R.id.addscheme);
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().getReference().child("Gov_scheme").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsgovmodel> clsgovmodelArrayList =new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    clsgovmodelArrayList.add(datasnapshot.getValue(clsgovmodel.class));
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(govermentScheme.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                rc.setLayoutManager(linearLayoutManager);
                if(type==1){
                    rcGovAdapter=new RcGovAdapter(govermentScheme.this,true,clsgovmodelArrayList);
                }
                else{
                    rcGovAdapter=new RcGovAdapter(govermentScheme.this,false,clsgovmodelArrayList);
                }
                rc.setAdapter(rcGovAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(type==1){
            addscheme.setVisibility(View.VISIBLE);
        }
        addscheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add news
                startActivity(new Intent(govermentScheme.this, add_gov_scheme.class));
            }
        });
    }
}