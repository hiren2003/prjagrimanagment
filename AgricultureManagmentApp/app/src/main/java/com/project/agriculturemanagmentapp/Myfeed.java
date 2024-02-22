package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Myfeed extends Fragment {
    RcFeedAdapter rcFeedAdapter;
    String Mo;
    Boolean SelfAccount;

    public Myfeed(String Mo,Boolean SelfAccount) {
        this.Mo=Mo;
        this.SelfAccount=SelfAccount;
        // Required empty public constructor
    }
    public Myfeed() {
        // Required empty public constructor
    }

    public static Myfeed newInstance(String param1, String param2) {
        Myfeed fragment = new Myfeed();
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
        View view= inflater.inflate(R.layout.fragment_myfeed, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.rcmyfeed);
        FloatingActionButton addfeed=view.findViewById(R.id.addfeed);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String mo=sharedPreferences.getString("mo","123456789");
        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child("Feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsFeedModel> feedModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    feedModelArrayList.add(datasnapshot.getValue(clsFeedModel.class));
                }
                ArrayList<clsFeedModel> reversefeedModelArrayList = new ArrayList<>();
                for (int i = feedModelArrayList.size() - 1; i >= 0; i--) {
                    reversefeedModelArrayList.add(feedModelArrayList.get(i));
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                RcImageGridAdapter rcImageGridAdapter=new RcImageGridAdapter(getContext(),SelfAccount,reversefeedModelArrayList);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                recyclerView.setAdapter(rcImageGridAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
                View v1=LayoutInflater.from(getContext()).inflate(R.layout.lyt_add_feed_category,null,false);
                CardView cdfeed = v1.findViewById(R.id.cdfeed);
                CardView cdvideo=v1.findViewById(R.id.cdvideo);
                CardView cdwc=v1.findViewById(R.id.cdwc);
                cdfeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(),add_feed.class));
                        bottomSheetDialog.cancel();
                    }
                });

                cdwc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), Write_share.class));
                        bottomSheetDialog.cancel();
                    }
                });
                bottomSheetDialog.setContentView(v1);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();


            }
        });
        return view;
    }
}