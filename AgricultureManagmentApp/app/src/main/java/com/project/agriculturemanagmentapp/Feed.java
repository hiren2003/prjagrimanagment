package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class Feed extends Fragment {


    public Feed() {
        // Required empty public constructor
    }

    public static Feed newInstance(String param1, String param2) {
        Feed fragment = new Feed();
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
        View view= inflater.inflate(R.layout.fragment_feed, container, false);
        ViewPager vpfeed=view.findViewById(R.id.vpfeed);
        TabLayout tbfeed=view.findViewById(R.id.tbfeed);
        VpFeedAdapter vpFeedAdapter=new VpFeedAdapter(getParentFragmentManager(),getContext());
        vpfeed.setAdapter(vpFeedAdapter);
        tbfeed.setupWithViewPager(vpfeed);
        return view;
    }
}