package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class MyProducts extends Fragment {
RcAnimalAdapter rcAnimalAdapter;
RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    ArrayList<clsAnimalModel> animalModelArrayList;
    ArrayList<clsToolsAccessoriesModel> toolsAccessoriesModelArrayList;
    ArrayList<ClsCultivationProductModel> cultivationProductModelArrayList;
    String Mo;
    Boolean SelfAccount;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static MyProducts newInstance(String param1, String param2) {
        MyProducts fragment = new MyProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyProducts(String Mo,Boolean SelfAccount) {
        // Required empty public constructor
        this.Mo=Mo;
        this.SelfAccount=SelfAccount;
    }
    public MyProducts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_products, container, false);
        RecyclerView myanimal=view.findViewById(R.id.rcmyanimal);
        RecyclerView myproduct=view.findViewById(R.id.rcmyproduct);
        RecyclerView mytools=view.findViewById(R.id.rcmytools);
        TextView txt1=view.findViewById(R.id.txt1);
        TextView txt2=view.findViewById(R.id.txt2);
        TextView txt3=view.findViewById(R.id.txt3);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        myproduct.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mytools.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        myanimal.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Resell").child("animal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animalModelArrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                     snapshot.getChildren()) {
                    animalModelArrayList.add(dataSnapshot.getValue(clsAnimalModel.class));
                }
                if (animalModelArrayList.isEmpty()){
                    txt1.setVisibility(View.GONE);
                }
                 rcAnimalAdapter=new RcAnimalAdapter(getContext(),SelfAccount, animalModelArrayList);
                myanimal.setAdapter(rcAnimalAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Resell").child("Cultivatio_Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           cultivationProductModelArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                        snapshot.getChildren()) {
                    cultivationProductModelArrayList.add(dataSnapshot.getValue(ClsCultivationProductModel.class));
                }
                if (cultivationProductModelArrayList.isEmpty()){
                    txt2.setVisibility(View.GONE);
                }
                ArrayList<ClsCultivationProductModel> reversedlist=new ArrayList<>();
                for (int i = cultivationProductModelArrayList.size() - 1; i >= 0; i--) {
                    reversedlist.add(cultivationProductModelArrayList.get(i));
                }
                rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(getContext(),SelfAccount,reversedlist);
                myproduct.setAdapter(rcCultivatonPrdtAdpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Resell").child("Tools&Accessories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 toolsAccessoriesModelArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                        snapshot.getChildren()) {
                    toolsAccessoriesModelArrayList.add(dataSnapshot.getValue(clsToolsAccessoriesModel.class));
                }
                if (toolsAccessoriesModelArrayList.isEmpty()){
                    txt3.setVisibility(View.GONE);
                }
                ArrayList<clsToolsAccessoriesModel> reversedlist=new ArrayList<>();
                for (int i = toolsAccessoriesModelArrayList.size() - 1; i >= 0; i--) {
                    reversedlist.add(toolsAccessoriesModelArrayList.get(i));
                }
                rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(getContext(),SelfAccount,reversedlist);
                mytools.setAdapter(rcToolsAccesoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}