package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

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
        rc = findViewById(R.id.rcgov);
        addscheme = findViewById(R.id.addscheme);
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        if(type==1){
            addscheme.setVisibility(View.VISIBLE);
        }
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseRecyclerOptions<clsgovmodel> options=new FirebaseRecyclerOptions.Builder<clsgovmodel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Gov_scheme"), clsgovmodel.class)
                .build();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(govermentScheme.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rc.setLayoutManager(linearLayoutManager);
        rcGovAdapter=new RcGovAdapter(options,govermentScheme.this);
        rc.setAdapter(rcGovAdapter);
        addscheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(govermentScheme.this, add_gov_scheme.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcGovAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcGovAdapter.stopListening();
    }
}