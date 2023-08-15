package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Use the {@link MyProducts#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MyProducts extends Fragment {
RcAnimalAdapter rcAnimalAdapter;
RcCultivatonPrdtAdpter rcCultivatonPrdtAdpter;
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProducts newInstance(String param1, String param2) {
        MyProducts fragment = new MyProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyProducts() {
        // Required empty public constructor
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
        View view= inflater.inflate(R.layout.fragment_my_products, container, false);
        RecyclerView myanimal=view.findViewById(R.id.rcmyanimal);
        RecyclerView myproduct=view.findViewById(R.id.rcmyproduct);
        RecyclerView mytools=view.findViewById(R.id.rcmytools);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        myanimal.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        myproduct.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mytools.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        FirebaseRecyclerOptions optionanimal=new FirebaseRecyclerOptions.Builder<clsAnimalModel>()
                .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Resell").child("animal"),clsAnimalModel.class)
                .build();
        FirebaseRecyclerOptions optionproduct=new FirebaseRecyclerOptions.Builder<ClsCultivationProductModel>()
                .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Resell").child("Cultivatio_Product"),ClsCultivationProductModel.class)
                .build();
        FirebaseRecyclerOptions optiontools=new FirebaseRecyclerOptions.Builder<clsToolsAccessoriesModel>()
                .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","1234567890")).child("Resell").child("Tools&Accessories"),clsToolsAccessoriesModel.class)
                .build();
        rcAnimalAdapter=new RcAnimalAdapter(optionanimal,getContext());
        rcCultivatonPrdtAdpter=new RcCultivatonPrdtAdpter(optionproduct,getContext());
        rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(optiontools,getContext());
        myanimal.setAdapter(rcAnimalAdapter);
        mytools.setAdapter(rcToolsAccesoriesAdapter);
        myproduct.setAdapter(rcCultivatonPrdtAdpter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        rcToolsAccesoriesAdapter.startListening();
        rcAnimalAdapter.startListening();
        rcCultivatonPrdtAdpter.startListening();
    }
}