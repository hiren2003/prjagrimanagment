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

public class MyVacancy extends Fragment {
    RcVacancyAdapter rcVacancyAdapter;
    String Mo;
    Boolean SelfAccount;

    public MyVacancy(String Mo,Boolean SelfAccount) {
        this.Mo=Mo;
        this.SelfAccount=SelfAccount;
        // Required empty public constructor
    }
    public MyVacancy() {
        // Required empty public constructor
    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static MyVacancy newInstance(String param1, String param2) {
        MyVacancy fragment = new MyVacancy();
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
        View view= inflater.inflate(R.layout.fragment_my_vacancy, container, false);
        RecyclerView rcmy=view.findViewById(R.id.rcmyvacancy);
        ExtendedFloatingActionButton fltaddvacancy=view.findViewById(R.id.fltaddvacancy);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String mo = sharedPreferences.getString("mo", "1234567890");
        FirebaseDatabase.getInstance().getReference("/Labour_Vacancy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsVacancyModel> vacancyModelArrayList =new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    clsVacancyModel model=datasnapshot.getValue(clsVacancyModel.class);
                    if (model.umo.equals(Mo)){
                        vacancyModelArrayList.add(model);
                    }
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                rcVacancyAdapter=new RcVacancyAdapter(getContext(),SelfAccount,vacancyModelArrayList);
                rcmy.setLayoutManager(linearLayoutManager);
                rcmy.setAdapter(rcVacancyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fltaddvacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),add_labour_vacancy.class));
            }
        });
        return  view;
    }
}