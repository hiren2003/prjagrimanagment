package com.project.agriculturemanagmentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.Manifest.permission.SEND_SMS;

public class Labour_Management extends Fragment {
RcLabourAdapter rcLabourAdapter;
    ArrayList<clsLaborModel> clsLaborModels;
    public Labour_Management() {
        // Required empty public constructor
    }
    public static Labour_Management newInstance(String param1, String param2) {
        Labour_Management fragment = new Labour_Management();
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
        View view= inflater.inflate(R.layout.fragment_labour__management, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.rc);
        ExtendedFloatingActionButton btnAddWorker = view.findViewById(R.id.addlabor);
        SearchView searchView=view.findViewById(R.id.srhlabr);
        clsLaborModels=new ArrayList<>();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        FirebaseDatabase.getInstance().getReference().child("Labor_data").child(sharedPreferences.getString("mo","1234567890")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsLaborModels=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    clsLaborModels.add(datasnapshot.getValue(clsLaborModel.class));
                }
                rcLabourAdapter=new RcLabourAdapter(getContext(),clsLaborModels);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(rcLabourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toString().trim().toLowerCase();
                ArrayList<clsLaborModel> filteredlist=new ArrayList<>();
                for (clsLaborModel model:
                     clsLaborModels) {
                    if(model.getLloc().toString().trim().toLowerCase().contains(newText)||
                            model.getLloc().toString().trim().toLowerCase().contains(newText)||
                            model.getLdate().toString().trim().toLowerCase().contains(newText)||
                            model.getLmo().toString().trim().toLowerCase().contains(newText)||
                            model.getLname().toString().trim().toLowerCase().contains(newText)||
                            model.getLwages().toString().trim().toLowerCase().contains(newText)
                    ){
                        filteredlist.add(model);
                    }
                }
               RcLabourAdapter newrcLabourAdapter=new RcLabourAdapter(getContext(),filteredlist);
                recyclerView.setAdapter(newrcLabourAdapter);
                return false;
            }
        });

        btnAddWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getContext(),SEND_SMS)== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{SEND_SMS},101);
                }
                else{
                    startActivity(new Intent(getContext(),add_labour.class));
                }
            }
        });
        return view;
    }
}