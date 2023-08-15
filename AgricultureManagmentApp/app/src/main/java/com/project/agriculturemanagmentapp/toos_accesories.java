package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link toos_accesories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class toos_accesories extends Fragment {
RcToolsAccesoriesAdapter rcToolsAccesoriesAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public toos_accesories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment toos_accesories.
     */
    // TODO: Rename and change types and number of parameters
    public static toos_accesories newInstance(String param1, String param2) {
        toos_accesories fragment = new toos_accesories();
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
        FirebaseRecyclerOptions<clsToolsAccessoriesModel> options=new FirebaseRecyclerOptions.Builder<clsToolsAccessoriesModel>()
                .setQuery( FirebaseDatabase.getInstance().getReference().child("Tools&Accessories"),clsToolsAccessoriesModel.class)
                .build();
        rcToolsAccesoriesAdapter=new RcToolsAccesoriesAdapter(options,getContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(rcToolsAccesoriesAdapter);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        rcToolsAccesoriesAdapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rcToolsAccesoriesAdapter.stopListening();
    }
}