package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cultivation_Product extends Fragment {
    RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cultivation_Product() {
        // Required empty public constructor
    }
    public static Cultivation_Product newInstance(String param1, String param2) {
        Cultivation_Product fragment = new Cultivation_Product();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view= inflater.inflate(R.layout.fragment_cultivation__product, container, false);
        RecyclerView rccprdt=view.findViewById(R.id.rccprdt);
        FirebaseDatabase.getInstance().getReference().child("Resell").child("Cultivation Product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ClsCultivationProductModel> clsCultivationProductModelArrayList=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    clsCultivationProductModelArrayList.add(datasnapshot.getValue(ClsCultivationProductModel.class));
                }
                ArrayList<ClsCultivationProductModel> reversedlist=new ArrayList<>();
                for (int i = clsCultivationProductModelArrayList.size() - 1; i >= 0; i--) {
                    reversedlist.add(clsCultivationProductModelArrayList.get(i));
                }
                rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(getContext(),false,reversedlist);
                rccprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rccprdt.setAdapter(rcCultivatonPrdtAdpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}