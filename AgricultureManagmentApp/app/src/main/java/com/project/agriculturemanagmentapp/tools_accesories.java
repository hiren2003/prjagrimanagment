package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tools_accesories extends Fragment {
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tools_accesories() {
        // Required empty public constructor
    }
    public static tools_accesories newInstance(String param1, String param2) {
        tools_accesories fragment = new tools_accesories();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_toos_accesories, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.rctoolacce);
        FirebaseDatabase.getInstance().getReference().child("Resell").child("Tools&Accessories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsToolsAccessoriesModel> toolsAccessoriesModelArrayList =new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    toolsAccessoriesModelArrayList.add(datasnapshot.getValue(clsToolsAccessoriesModel.class));
                }
                ArrayList<clsToolsAccessoriesModel> reversedlist=new ArrayList<>();
                for (int i = toolsAccessoriesModelArrayList.size() - 1; i >= 0; i--) {
                    reversedlist.add(toolsAccessoriesModelArrayList.get(i));
                }
                rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(getContext(),false,reversedlist);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(rcToolsAccesoriesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  view;
    }
}