package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpAdapterResell extends FragmentPagerAdapter {
    Context context;

    public VpAdapterResell(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Cultivation_Product();
        } else if (position == 1) {
            return new animals();
        } else if (position == 2) {
            return new toos_accesories();
        } else {
            return new MyProducts();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getResources().getString(R.string.Cuti_prd);
        } else if (position == 1) {
            return context.getResources().getString(R.string.animals);
        } else if (position == 2) {
            return context.getResources().getString(R.string.Tools_Accesories);
        } else {
            return context.getResources().getString(R.string.My_Product);
        }
    }

}
