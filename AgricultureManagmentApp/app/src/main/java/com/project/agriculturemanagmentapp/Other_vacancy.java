package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Other_vacancy extends Fragment {
    RcVacancyAdapter rcOtherVacancyAdapter;
    ArrayList<clsVacancyModel> vacancyModelArrayList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Other_vacancy() {
        // Required empty public constructor
    }
    public static Other_vacancy newInstance(String param1, String param2) {
        Other_vacancy fragment = new Other_vacancy();
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
        View view= inflater.inflate(R.layout.fragment_other_vacancy, container, false);
        RecyclerView rcother=view.findViewById(R.id.rcothervacancy);
        LottieAnimationView loty=view.findViewById(R.id.loty1);
        androidx.appcompat.widget.SearchView searchView=view.findViewById(R.id.searchvacancy);
         vacancyModelArrayList=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Labour_Vacancy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             vacancyModelArrayList =new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    vacancyModelArrayList.add(datasnapshot.getValue(clsVacancyModel.class));
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                rcother.setLayoutManager(linearLayoutManager);
                rcOtherVacancyAdapter=new RcVacancyAdapter(getContext(),false,vacancyModelArrayList);
                rcother.setAdapter(rcOtherVacancyAdapter);
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
            public boolean onQueryTextChange(String text) {
                ArrayList<clsVacancyModel> filteredlist = new ArrayList<>();
String newText=text.toString().toLowerCase().trim();
                    for(clsVacancyModel model:vacancyModelArrayList) {
                        if (model.getDistrict().trim().toLowerCase().contains(newText) || model.getEamt().trim().toLowerCase().contains(newText) ||
                                model.getOname().trim().toLowerCase().contains(newText) || model.getOcan().trim().toLowerCase().contains(newText) ||
                                model.getState().trim().toLowerCase().contains(newText) || model.getTehsil().trim().toLowerCase().contains(newText) ||
                                model.getTwork().trim().toLowerCase().contains(newText) || model.getUmo().trim().toLowerCase().contains(newText) ||
                                model.getVillage().trim().toLowerCase().contains(newText) || model.getWdur().trim().toLowerCase().contains(newText) ||
                                model.getWtype().trim().toLowerCase().contains(newText)) {
                            filteredlist.add(model);
                        }
                    }
                if (filteredlist.isEmpty()){
                    loty.setVisibility(View.VISIBLE);
                }
                else{
                    loty.setVisibility(View.GONE);
                    rcOtherVacancyAdapter=new RcVacancyAdapter(getContext(),false,filteredlist);
                    rcother.setAdapter(rcOtherVacancyAdapter);
                }
                return false;
            }
        });

        return view;
    }
}