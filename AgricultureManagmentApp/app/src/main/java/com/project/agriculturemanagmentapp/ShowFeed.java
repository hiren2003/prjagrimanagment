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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    RcFeedAdapter rcFeedAdapter,rcFeedAdapter2;
    Boolean isFollowerFeed;
    ArrayList<clsFeedModel> feedModelArrayList;
    ArrayList<clsFeedModel> FollowingfeedModelArrayList;
    ArrayList<clsFeedModel> OtherfeedModelArrayList;
    Boolean feed1=true,feed2=false;
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
        RecyclerView rcviewotherfeed=view.findViewById(R.id.rcviewotherfeed);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        LottieAnimationView loty1=view.findViewById(R.id.loty1);
        LottieAnimationView loty2=view.findViewById(R.id.loty2);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        ImageView img1=view.findViewById(R.id.img1);
        ImageView img2=view.findViewById(R.id.img2);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext());
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        rcviewotherfeed.setLayoutManager(linearLayoutManager2);
        FollowingfeedModelArrayList= new ArrayList<>();
        OtherfeedModelArrayList= new ArrayList<>();
        view.findViewById(R.id.ll1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feed1){
                    feed1=false;
                    img1.setImageDrawable(getContext().getDrawable(R.drawable.rightarrow));
                    recyclerView.setVisibility(View.GONE);
                    loty1.setVisibility(View.GONE);

                }
                else {
                    feed1=true;
                    img1.setImageDrawable(getContext().getDrawable(R.drawable.downarrow));
                    recyclerView.setVisibility(View.VISIBLE);
                    if(FollowingfeedModelArrayList.isEmpty()){
                        loty1.setVisibility(View.VISIBLE);
                    }
                    else{
                        loty1.setVisibility(View.GONE);
                    }
                }

            }
        });
        view.findViewById(R.id.ll2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feed2){
                    feed2=false;
                    img2.setImageDrawable(getContext().getDrawable(R.drawable.rightarrow));
                    rcviewotherfeed.setVisibility(View.GONE);
                    loty2.setVisibility(View.GONE);
                }
                else {
                    feed2=true;
                    img2.setImageDrawable(getContext().getDrawable(R.drawable.downarrow));
                    rcviewotherfeed.setVisibility(View.VISIBLE);
                    if(OtherfeedModelArrayList.isEmpty()){
                        loty2.setVisibility(View.VISIBLE);
                    }
                    else{
                        loty2.setVisibility(View.GONE);
                    }
                }
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    clsFeedModel model=datasnapshot.getValue(clsFeedModel.class);
                    feedModelArrayList.add(model);
                }

                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo","null")).child("Following").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        ArrayList<String> arrayList=new ArrayList<>();
                        FollowingfeedModelArrayList= new ArrayList<>();
                        OtherfeedModelArrayList= new ArrayList<>();
                        for (DataSnapshot dataSnapshot:snapshot2.getChildren()){
                            arrayList.add(dataSnapshot.getValue().toString());
                        }
                        for (clsFeedModel model:feedModelArrayList) {
                            if(arrayList.contains(model.getUmo())){
                             FollowingfeedModelArrayList.add(model);
                            }
                            else{
                              OtherfeedModelArrayList.add(model);
                            }
                        }
                        if(FollowingfeedModelArrayList.isEmpty()){
                            loty1.setVisibility(View.VISIBLE);
                        }
                        else{
                         loty1.setVisibility(View.GONE);
                        }
                        rcFeedAdapter =new RcFeedAdapter(getContext(),false,FollowingfeedModelArrayList);
                        recyclerView.setAdapter(rcFeedAdapter);
                        rcFeedAdapter2 =new RcFeedAdapter(getContext(),false,OtherfeedModelArrayList);
                        rcviewotherfeed.setAdapter(rcFeedAdapter2);
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