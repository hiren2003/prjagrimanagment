package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_output extends AppCompatActivity {
RecyclerView recyclerView;
int type;
String mo;
SharedPreferences sharedPreferences;
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
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        if(type==2){
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Feed").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsFeedModel> feedModelArrayList = new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        feedModelArrayList.add(datasnapshot.getValue(clsFeedModel.class));
                    }
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    rcFeedAdapter=new RcFeedAdapter(getApplicationContext(),true,feedModelArrayList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(rcFeedAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (type==3) {
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsVacancyModel> vacancyModelArrayList =new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        vacancyModelArrayList.add(datasnapshot.getValue(clsVacancyModel.class));
                    }
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    rcVacancyAdapter=new RcVacancyAdapter(getApplicationContext(),true,vacancyModelArrayList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(rcVacancyAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (type==4) {
            FirebaseDatabase.getInstance().getReference().child("Labor_data").child(sharedPreferences.getString("mo","1234567890")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsLaborModel> laborModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        laborModelArrayList.add(datasnapshot.getValue(clsLaborModel.class));
                    }
                   rcLabourAdapter=new RcLabourAdapter(getApplicationContext(),laborModelArrayList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(rcLabourAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (type==5) {
            scrcview.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            RecyclerView myanimal=findViewById(R.id.rcmyanimal);
            RecyclerView myproduct=findViewById(R.id.rcmyproduct);
            RecyclerView mytools=findViewById(R.id.rcmytools);
            myanimal.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            myproduct.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            mytools.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Resell").child("animal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsAnimalModel> animalModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                         snapshot.getChildren()) {
                        animalModelArrayList.add(datasnapshot.getValue(clsAnimalModel.class));
                    }
                    rcAnimalAdapter=new RcAnimalAdapter(getApplicationContext(),true,animalModelArrayList);
                    myanimal.setAdapter(rcAnimalAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Resell").child("Cultivatio_Product").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ClsCultivationProductModel> cultivationProductModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        cultivationProductModelArrayList.add(datasnapshot.getValue(ClsCultivationProductModel.class));
                    }
                    rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(getApplicationContext(),true,cultivationProductModelArrayList);
                    myproduct.setAdapter(rcCultivatonPrdtAdpter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Resell").child("Tools&Accessories").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsToolsAccessoriesModel> toolsAccessoriesModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        toolsAccessoriesModelArrayList.add(datasnapshot.getValue(clsToolsAccessoriesModel.class));
                    }
                    rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(getApplicationContext(),true,toolsAccessoriesModelArrayList);
                    mytools.setAdapter(rcToolsAccesoriesAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (type==6) {
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Cart").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsEcommModel> ecommModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        ecommModelArrayList.add(datasnapshot.getValue(clsEcommModel.class));
                    }
                    rcEcommAdapter=new RcEcommAdapter(getBaseContext(),2,ecommModelArrayList);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(rcEcommAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if (type==7) {
            FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Myorder").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        orderModelArrayList.add(datasnapshot.getValue(clsOrderModel.class));
                    }
                    rcorderAdapter=new RcorderAdapter(User_output.this,orderModelArrayList);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(rcorderAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if (type==8) {
           // FirebaseRecyclerOptions<clsEcommModel> options=new FirebaseRecyclerOptions.Builder<clsEcommModel>()
             //       .setQuery(FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("Cancelled"), clsEcommModel.class)
               //     .build();
            //rcEcommAdapter=new RcEcommAdapter(options,getBaseContext(),3);
            //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
           // recyclerView.setAdapter(rcEcommAdapter);
        }
        else {
            finish();
        }


    }

}