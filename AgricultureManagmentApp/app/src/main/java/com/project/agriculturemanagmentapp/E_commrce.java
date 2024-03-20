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
        FloatingActionButton filter=view.findViewById(R.id.addecomm);
        SearchView searchView=view.findViewById(R.id.srhitem);
        String arr[]=getResources().getStringArray(R.array.arrcategory);
        spntype.setAdapter(new ArrayAdapter<String>(getContext(),com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,arr));
        originalecommModelArrayList=new ArrayList<>();

        spntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            originalecommModelArrayList=new ArrayList<>();
                            for (DataSnapshot datasnapshot:
                                    snapshot.getChildren()) {
                                clsEcommModel model=datasnapshot.getValue(clsEcommModel.class);
                                if (model.getType().trim().equals(arr[position])){
                                    originalecommModelArrayList.add(model);
                                }
                            }
                            rcEcommAdapter=new RcEcommAdapter(getContext(),1,originalecommModelArrayList);
                            rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                            rcprdt.setAdapter(rcEcommAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            originalecommModelArrayList=new ArrayList<>();
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                String newText=txt.toString().trim().toLowerCase();
                ArrayList<clsEcommModel> filterlist=new ArrayList<>();
                for (clsEcommModel model:originalecommModelArrayList){
                    if (model.getPname().toString().toLowerCase().contains(newText)||model.getKey().toString().toLowerCase().contains(newText)||
                    model.getDes().toString().toLowerCase().contains(newText)||model.getDpec().toString().toLowerCase().contains(newText)||
                            model.getRecomm().toString().toLowerCase().contains(newText)){
                        filterlist.add(model);
                    }
                }
                rcEcommAdapter=new RcEcommAdapter(getContext(),1,filterlist);
                rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rcprdt.setAdapter(rcEcommAdapter);
                return false;
            }
        });
        return  view;
    }
}