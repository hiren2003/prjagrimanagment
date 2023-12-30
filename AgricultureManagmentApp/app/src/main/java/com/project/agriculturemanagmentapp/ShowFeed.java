package com.project.agriculturemanagmentapp;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowFeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowFeed extends Fragment {
    RcFeedAdapter rcFeedAdapter;
    Boolean isFollowerFeed;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ShowFeed(Boolean isFollowerFeed) {
        this.isFollowerFeed=isFollowerFeed;
    }
    public ShowFeed() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowFeed.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowFeed newInstance(String param1, String param2) {
        ShowFeed fragment = new ShowFeed();
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
        View view= inflater.inflate(R.layout.fragment_show_feed, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.rcviewshfeed);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        FirebaseDatabase.getInstance().getReference().child("Feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsFeedModel> feedModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    clsFeedModel model=datasnapshot.getValue(clsFeedModel.class);
                    feedModelArrayList.add(model);
                }


                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","null")).child("Following").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        ArrayList<String> arrayList=new ArrayList<>();
                        for (DataSnapshot dataSnapshot:snapshot2.getChildren()){
                            arrayList.add(dataSnapshot.getValue().toString());
                        }
                        ArrayList<clsFeedModel> feedModelArrayList2 = new ArrayList<>();
                        if (isFollowerFeed){
                            for (clsFeedModel clsFeedModel:feedModelArrayList){
                                if (arrayList.contains(clsFeedModel.getUmo())){
                                    feedModelArrayList2.add(clsFeedModel);
                                }
                            }
                        }
                        else {
                            for (clsFeedModel clsFeedModel:feedModelArrayList){
                                if (!arrayList.contains(clsFeedModel.getUmo())){
                                    feedModelArrayList2.add(clsFeedModel);
                                }
                            }
                        }
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                        linearLayoutManager.setReverseLayout(true);
                        linearLayoutManager.setStackFromEnd(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        rcFeedAdapter =new RcFeedAdapter(getContext(),false,feedModelArrayList2);
                        recyclerView.setAdapter(rcFeedAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}