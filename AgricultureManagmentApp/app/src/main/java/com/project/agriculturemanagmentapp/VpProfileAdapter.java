package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpProfileAdapter extends FragmentPagerAdapter {
    Context context;
    public VpProfileAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Myfeed();
        } else if (position == 1) {
            return new MyVacancy();
        } else if (position == 2) {
            return new Labour_Management();
        } else {
            return new MyProducts();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.My_Feed);
        } else if (position == 1) {
            return context.getString( R.string.Labour_Vacancy);
        } else if (position == 2) {
            return context.getString( R.string.Labour_Managment);
        } else {
            return  context.getString(R.string.My_Product);
        }
    }
}
