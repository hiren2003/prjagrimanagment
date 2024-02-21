package com.project.agriculturemanagmentapp;

import android.content.Context;

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
        } else  {
            return new tools_accesories();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
