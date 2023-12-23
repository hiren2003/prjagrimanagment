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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Myfeed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Myfeed extends Fragment {
    RcFeedAdapter rcFeedAdapter;
    String Mo;
    Boolean SelfAccount;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Myfeed(String Mo,Boolean SelfAccount) {
        this.Mo=Mo;
        this.SelfAccount=SelfAccount;
        // Required empty public constructor
    }
    public Myfeed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Myfeed.
     */
    // TODO: Rename and change types and number of parameters
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
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
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
                cdvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(),add_video.class));
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