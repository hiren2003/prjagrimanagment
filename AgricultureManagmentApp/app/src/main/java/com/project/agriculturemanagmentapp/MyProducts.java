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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProducts#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MyProducts extends Fragment {
RcAnimalAdapter rcAnimalAdapter;
RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
String Mo;
    Boolean SelfAccount;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProducts.
     */
    // TODO: Rename and change types and number of parameters
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
        ExtendedFloatingActionButton fltsell=view.findViewById(R.id.fltsell);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        myproduct.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mytools.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        myanimal.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Resell").child("animal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsAnimalModel> animalModelArrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                     snapshot.getChildren()) {
                    animalModelArrayList.add(dataSnapshot.getValue(clsAnimalModel.class));
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
                ArrayList<ClsCultivationProductModel> cultivationProductModelArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                        snapshot.getChildren()) {
                    cultivationProductModelArrayList.add(dataSnapshot.getValue(ClsCultivationProductModel.class));
                }
                rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(getContext(),SelfAccount,cultivationProductModelArrayList);
                myproduct.setAdapter(rcCultivatonPrdtAdpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Resell").child("Tools&Accessories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsToolsAccessoriesModel> toolsAccessoriesModelArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                        snapshot.getChildren()) {
                    toolsAccessoriesModelArrayList.add(dataSnapshot.getValue(clsToolsAccessoriesModel.class));
                }
                rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(getContext(),SelfAccount,toolsAccessoriesModelArrayList);
                mytools.setAdapter(rcToolsAccesoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fltsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Resell_Category.class));

            }
        });
        return view;
    }
}