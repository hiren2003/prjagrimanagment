package com.project.agriculturemanagmentapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

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
        ExtendedFloatingActionButton addecomm=view.findViewById(R.id.addecomm);
        FirebaseRecyclerOptions<clsEcommModel> options=new FirebaseRecyclerOptions.Builder<clsEcommModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All"), clsEcommModel.class)
                .build();
         rcEcommAdapter=new RcEcommAdapter(options,getContext(),1);
        rcprdt.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcprdt.setAdapter(rcEcommAdapter);
        addecomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), add_ecomm.class));
            }
        });
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        rcEcommAdapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rcEcommAdapter.stopListening();
    }
}