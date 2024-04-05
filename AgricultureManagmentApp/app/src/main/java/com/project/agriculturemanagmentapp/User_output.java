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
                    rcFeedAdapter=new RcFeedAdapter(User_output.this,true,true,feedModelArrayList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(rcFeedAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (type==3) {
            FirebaseDatabase.getInstance().getReference("/Labour_Vacancy").addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsVacancyModel> vacancyModelArrayList =new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        clsVacancyModel model=datasnapshot.getValue(clsVacancyModel.class);
                        if (model.umo.equals(mo)){
                            vacancyModelArrayList.add(model);
                        }
                    }
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    rcVacancyAdapter=new RcVacancyAdapter(User_output.this,true,vacancyModelArrayList);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(rcVacancyAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (type==4) {
            FirebaseDatabase.getInstance().getReference().child("Labor_data").child(mo.toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsLaborModel> laborModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        laborModelArrayList.add(datasnapshot.getValue(clsLaborModel.class));
                    }
                   rcLabourAdapter=new RcLabourAdapter(User_output.this,laborModelArrayList);
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
            myanimal.setLayoutManager(new LinearLayoutManager(User_output.this,LinearLayoutManager.HORIZONTAL,false));
            myproduct.setLayoutManager(new LinearLayoutManager(User_output.this,LinearLayoutManager.HORIZONTAL,false));
            mytools.setLayoutManager(new LinearLayoutManager(User_output.this,LinearLayoutManager.HORIZONTAL,false));
            FirebaseDatabase.getInstance().getReference("/Resell/animals").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsAnimalModel> animalModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                         snapshot.getChildren()) {
                        clsAnimalModel model=datasnapshot.getValue(clsAnimalModel.class);
                        if (model.getUmo().toString().trim().equals(mo)){
                            animalModelArrayList.add(model);
                        }                    }
                    ArrayList<clsAnimalModel> reversedlist=new ArrayList<>();
                    for (int i = animalModelArrayList.size() - 1; i >= 0; i--) {
                        reversedlist.add(animalModelArrayList.get(i));
                    }
                    rcAnimalAdapter=new RcAnimalAdapter(User_output.this,false,true,reversedlist);
                    myanimal.setAdapter(rcAnimalAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            FirebaseDatabase.getInstance().getReference("/Resell/Cultivation Product").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ClsCultivationProductModel> cultivationProductModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        ClsCultivationProductModel model=datasnapshot.getValue(ClsCultivationProductModel.class);
                        if (model.getUmo().trim().equals(mo)){
                            cultivationProductModelArrayList.add(model);
                        }
                    }
                    ArrayList<ClsCultivationProductModel> reversedlist=new ArrayList<>();
                    for (int i = cultivationProductModelArrayList.size() - 1; i >= 0; i--) {
                        reversedlist.add(cultivationProductModelArrayList.get(i));
                    }
                    rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(User_output.this,false,true,reversedlist);
                    myproduct.setAdapter(rcCultivatonPrdtAdpter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            FirebaseDatabase.getInstance().getReference("/Resell/Tools&Accessories").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsToolsAccessoriesModel> toolsAccessoriesModelArrayList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        clsToolsAccessoriesModel model=datasnapshot.getValue(clsToolsAccessoriesModel.class);
                        if (model.getUmo().equals(mo)){
                            toolsAccessoriesModelArrayList.add(model);
                        }                    }
                    ArrayList<clsToolsAccessoriesModel> reversedlist=new ArrayList<>();
                    for (int i = toolsAccessoriesModelArrayList.size() - 1; i >= 0; i--) {
                        reversedlist.add(toolsAccessoriesModelArrayList.get(i));
                    }
                    rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(User_output.this,false,true,reversedlist);
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
                    ArrayList <String>CartList=new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        CartList.add(datasnapshot.getValue().toString());
                    }
                    FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<clsEcommModel> ecommModelArrayList=new ArrayList<>();
                            for (DataSnapshot datasnapshot:
                                    snapshot.getChildren()) {
                                if (CartList.contains(datasnapshot.getKey().toString())){
                                    ecommModelArrayList.add(datasnapshot.getValue(clsEcommModel.class));
                                }
                            }

                                ArrayList<clsEcommModel> reversedlist=new ArrayList<>();
                                for (int i = ecommModelArrayList.size() - 1; i >= 0; i--) {
                                    reversedlist.add(ecommModelArrayList.get(i));
                                }
                                rcEcommAdapter=new RcEcommAdapter(User_output.this,4,reversedlist);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                recyclerView.setAdapter(rcEcommAdapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else if (type==7) {
            FirebaseDatabase.getInstance().getReference("/Orders").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
                    for (DataSnapshot datasnapshot:
                            snapshot.getChildren()) {
                        clsOrderModel model=datasnapshot.getValue(clsOrderModel.class);
                        if (model.getMo().trim().equals(mo)){
                            orderModelArrayList.add(model);
                        }
                    }
                    rcorderAdapter=new RcorderAdapter(User_output.this,orderModelArrayList,true,false);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(rcorderAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            finish();
        }


    }

}