package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
public class Resell extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Resell() {
        // Required empty public constructor
    }
   public static Resell newInstance(String param1, String param2) {
        Resell fragment = new Resell();
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
        View view = inflater.inflate(R.layout.fragment_resell, container, false);
        ExtendedFloatingActionButton fltsell=view.findViewById(R.id.fltsell);
        ViewPager vpresell=view.findViewById(R.id.vpresell);
        TabLayout tbresell=view.findViewById(R.id.tbresell);
        VpAdapterResell vpAdapterResell=new VpAdapterResell(getChildFragmentManager(),getContext());
        vpresell.setAdapter(vpAdapterResell);
        tbresell.setupWithViewPager(vpresell);
        tbresell.getTabAt(0).setIcon(R.drawable.wheat);
        tbresell.getTabAt(1).setIcon(R.drawable.animals);
        tbresell.getTabAt(2).setIcon(R.drawable.cultivator);
        fltsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Resell_Category.class));

            }
        });
        return  view;
    }
}