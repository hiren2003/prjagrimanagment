package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Other_vacancy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Other_vacancy extends Fragment {
    RcVacancyAdapter rcOtherVacancyAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Other_vacancy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Other_vacancy.
     */
    // TODO: Rename and change types and number of parameters
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
        FirebaseRecyclerOptions<clsVacancyModel> options2=new FirebaseRecyclerOptions.Builder<clsVacancyModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Labour_Vacancy"),clsVacancyModel.class)
                .build();
        rcother.setLayoutManager(new LinearLayoutManager(getContext()));
        rcOtherVacancyAdapter=new RcVacancyAdapter(options2,getContext(),false);
        rcother.setAdapter(rcOtherVacancyAdapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        rcOtherVacancyAdapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rcOtherVacancyAdapter.stopListening();
    }
}