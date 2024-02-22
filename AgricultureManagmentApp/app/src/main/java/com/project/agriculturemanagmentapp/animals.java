package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class animals extends Fragment {
RecyclerView rcanimal;
RcAnimalAdapter rcAnimalAdapter;
    ArrayList<clsAnimalModel> arrayList;
    public animals() {
        // Required empty public constructor
    }
    public static animals newInstance(String param1, String param2) {
        animals fragment = new animals();
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
        View view= inflater.inflate(R.layout.fragment_animals, container, false);
        rcanimal=view.findViewById(R.id.rcanimal);
        arrayList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Resell").child("animals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    arrayList.add(dataSnapshot.getValue(clsAnimalModel.class));
                }
                ArrayList<clsAnimalModel> reversedlist=new ArrayList<>();
                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    reversedlist.add(arrayList.get(i));
                }
                rcanimal.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rcAnimalAdapter=new RcAnimalAdapter(getContext(),false,reversedlist);
                rcanimal.setAdapter(rcAnimalAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  view;
    }
}