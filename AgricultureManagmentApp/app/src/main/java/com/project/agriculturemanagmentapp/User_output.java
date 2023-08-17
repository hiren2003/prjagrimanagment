package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class User_output extends AppCompatActivity {
RecyclerView recyclerView;
int type;
String mo;
RcFeedAdapter rcFeedAdapter;
RcVacancyAdapter rcVacancyAdapter;
RcLabourAdapter rcLabourAdapter;
RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
RcAnimalAdapter rcAnimalAdapter;
RcorderAdapter rcorderAdapter;
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
NestedScrollView scrcview;
RcEcommAdapter rcEcommAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_output);
        recyclerView=findViewById(R.id.rcview);
        scrcview=findViewById(R.id.srcview);
        Intent intent=getIntent();
        mo=intent.getStringExtra("mo");
        type=intent.getIntExtra("type",0);
        if(type==2){
            FirebaseRecyclerOptions<clsFeedModel> options=new FirebaseRecyclerOptions.Builder<clsFeedModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Feed"), clsFeedModel.class)
                    .build();
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            rcFeedAdapter=new RcFeedAdapter(options,getBaseContext(),true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(rcFeedAdapter);
            rcFeedAdapter.startListening();
        } else if (type==3) {
            FirebaseRecyclerOptions<clsVacancyModel> options=new FirebaseRecyclerOptions.Builder<clsVacancyModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy"),clsVacancyModel.class)
                    .build();
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            rcVacancyAdapter=new RcVacancyAdapter(options,getBaseContext(),true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(rcVacancyAdapter);
            rcVacancyAdapter.startListening();

        }
        else if (type==4) {
            FirebaseRecyclerOptions<clsLaborModel> options= new FirebaseRecyclerOptions.Builder<clsLaborModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Labor_data").child(mo), clsLaborModel.class)
                    .build();
            rcLabourAdapter=new RcLabourAdapter(options,getBaseContext());
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(rcLabourAdapter);
            rcLabourAdapter.startListening();
        } else if (type==5) {
            scrcview.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            RecyclerView myanimal=findViewById(R.id.rcmyanimal);
            RecyclerView myproduct=findViewById(R.id.rcmyproduct);
            RecyclerView mytools=findViewById(R.id.rcmytools);
            myanimal.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            myproduct.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            mytools.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            FirebaseRecyclerOptions optionanimal=new FirebaseRecyclerOptions.Builder<clsAnimalModel>()
                    .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Resell").child("animal"),clsAnimalModel.class)
                    .build();
            FirebaseRecyclerOptions optionproduct=new FirebaseRecyclerOptions.Builder<ClsCultivationProductModel>()
                    .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Resell").child("Cultivatio_Product"),ClsCultivationProductModel.class)
                    .build();
            FirebaseRecyclerOptions optiontools=new FirebaseRecyclerOptions.Builder<clsToolsAccessoriesModel>()
                    .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Resell").child("Tools&Accessories"),clsToolsAccessoriesModel.class).build();
            rcAnimalAdapter=new RcAnimalAdapter(optionanimal,getBaseContext(),true);
            rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(optionproduct,getBaseContext(),true);
            rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(optiontools,getBaseContext(),true);
            myanimal.setAdapter(rcAnimalAdapter);
            mytools.setAdapter(rcToolsAccesoriesAdapter);
            myproduct.setAdapter(rcCultivatonPrdtAdpter);
            rcToolsAccesoriesAdapter.startListening();
            rcAnimalAdapter.startListening();
            rcCultivatonPrdtAdpter.startListening();
        } else if (type==6) {
            FirebaseRecyclerOptions<clsEcommModel> options=new FirebaseRecyclerOptions.Builder<clsEcommModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Cart"), clsEcommModel.class)
                    .build();
            rcEcommAdapter=new RcEcommAdapter(options,getBaseContext(),2);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(rcEcommAdapter);
            rcEcommAdapter.startListening();
        }
        else if (type==7) {
            FirebaseRecyclerOptions<clsOrderModel> options=new FirebaseRecyclerOptions.Builder<clsOrderModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Myorder"), clsOrderModel.class)
                    .build();
            rcorderAdapter=new RcorderAdapter(options,User_output.this);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(rcorderAdapter);
            rcorderAdapter.startListening();
        }
        else if (type==8) {
            FirebaseRecyclerOptions<clsEcommModel> options=new FirebaseRecyclerOptions.Builder<clsEcommModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Cancelled"), clsEcommModel.class)
                    .build();
            rcEcommAdapter=new RcEcommAdapter(options,getBaseContext(),3);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(rcEcommAdapter);
            rcEcommAdapter.startListening();
        }
        else {
            finish();;
        }


    }

}