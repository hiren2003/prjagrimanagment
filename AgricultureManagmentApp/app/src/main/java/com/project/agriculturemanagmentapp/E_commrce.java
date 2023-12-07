package com.project.agriculturemanagmentapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link E_commrce#newInstance} factory method to
 * create an instance of this fragment.
 */
public class E_commrce extends Fragment {
    RcEcommAdapter rcEcommAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public E_commrce() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment E_commrce.
     */
    // TODO: Rename and change types and number of parameters
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
        ExtendedFloatingActionButton addecomm=view.findViewById(R.id.addecomm);
        FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsEcommModel> ecommModelArrayList=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    ecommModelArrayList.add(datasnapshot.getValue(clsEcommModel.class));
                }
                rcEcommAdapter=new RcEcommAdapter(getContext(),1,ecommModelArrayList);
                rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                rcprdt.setAdapter(rcEcommAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String[] arr = getResources().getStringArray(R.array.arrcategory2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, arr);
        spntype.setAdapter(adapter);
        spntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference().child("ECommerce").child(arr[position]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<clsEcommModel> ecommModelArrayList=new ArrayList<>();
                        for (DataSnapshot datasnapshot:
                                snapshot.getChildren()) {
                            ecommModelArrayList.add(datasnapshot.getValue(clsEcommModel.class));
                        }
                        rcEcommAdapter=new RcEcommAdapter(getContext(),1,ecommModelArrayList);
                        rcprdt.setAdapter(rcEcommAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addecomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), add_ecomm.class));
            }
        });
        return  view;
    }
}