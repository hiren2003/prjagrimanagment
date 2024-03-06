package com.project.agriculturemanagmentapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class E_commrce extends Fragment {
    RcEcommAdapter rcEcommAdapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<clsEcommModel> originalecommModelArrayList;
    ArrayList<clsEcommModel> currentecommModelArrayList;
    ArrayList<clsEcommModel> updatedecommModelArrayList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public E_commrce() {
        // Required empty public constructor
    }
    public static E_commrce newInstance(String param1, String param2) {
        E_commrce fragment = new E_commrce();
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
        View view= inflater.inflate(R.layout.fragment_e_commrce, container, false);
        RecyclerView rcprdt=view.findViewById(R.id.rccprdt);
        Spinner spntype=view.findViewById(R.id.category);
        Query query=FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All");
        FloatingActionButton filter=view.findViewById(R.id.addecomm);
        SearchView searchView=view.findViewById(R.id.searchvacancy);
        originalecommModelArrayList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsEcommModel> originalecommModelArrayList=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    originalecommModelArrayList.add(datasnapshot.getValue(clsEcommModel.class));
                }
                rcEcommAdapter=new RcEcommAdapter(getContext(),1,originalecommModelArrayList);
                rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rcprdt.setAdapter(rcEcommAdapter);
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
                    updatedecommModelArrayList = new ArrayList<>();
                    for (clsEcommModel model:
                         originalecommModelArrayList) {
                        if(model.getType().toLowerCase().trim().contains(newText)||model.getKey().toLowerCase().trim().contains(newText)||model.getPname().toLowerCase().trim().contains(newText)){
                            updatedecommModelArrayList.add(model);
                        }
                    }
                    RcEcommAdapter rcEcommAdapter2=new RcEcommAdapter(getContext(),1,updatedecommModelArrayList);
                    rcprdt.setAdapter(rcEcommAdapter2);


                return false;
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1=LayoutInflater.from(getContext()).inflate(R.layout.lyt_ecom_filter,null,false);
                Spinner spinner=view1.findViewById(R.id.category);
                String[] arr = getResources().getStringArray(R.array.arrcategory2);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr);
                spinner.setAdapter(adapter);
                SearchView searchView=view1.findViewById(R.id.searchvacancy);
                TextInputEditText from=view1.findViewById(R.id.from);
                TextInputEditText to=view1.findViewById(R.id.to);
                AppCompatButton apply=view1.findViewById(R.id.btnapply);
                AppCompatButton clear=view1.findViewById(R.id.btnclear);
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rcEcommAdapter=new RcEcommAdapter(getContext(),1,originalecommModelArrayList);
                        rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        rcprdt.setAdapter(rcEcommAdapter);
                        Toast.makeText(getContext(), "Cleared", Toast.LENGTH_SHORT).show();
                    }
                });
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatedecommModelArrayList=new ArrayList<>();
                        String type="";
                        int start=0;
                        int end=0;
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].equals(spinner.getSelectedItem())){
                                type=arr[i];
                                break;
                            }
                        }
                        if(!from.getText().toString().trim().isEmpty()){
                            start=Integer.parseInt(from.getText().toString());
                        }
                        else{
                            start=Integer.MIN_VALUE;
                        }
                        if(!to.getText().toString().trim().isEmpty()){
                            end=Integer.parseInt(to.getText().toString());
                        }
                        else{
                            end=Integer.MAX_VALUE;
                        }
                        for (clsEcommModel model:originalecommModelArrayList){
                            if (type.trim().isEmpty()){
                                if (start>=Integer.parseInt(model.getPrice())&&start<=Integer.parseInt(model.getPrice())){
                                    updatedecommModelArrayList.add(model);
                                }
                            }
                            else{
                                if (start>=Integer.parseInt(model.getPrice())&&start<=Integer.parseInt(model.getPrice())&&model.getType().equals(type)){
                                    updatedecommModelArrayList.add(model);
                                }
                            }
                        }
                        rcEcommAdapter=new RcEcommAdapter(getContext(),1,updatedecommModelArrayList);
                        rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        rcprdt.setAdapter(rcEcommAdapter);
                    }
                });

            }
        });
        return  view;
    }
}