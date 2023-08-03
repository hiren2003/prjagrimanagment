package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpVacancyAdapter extends FragmentPagerAdapter {
    Context context;
    public VpVacancyAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return context.getResources().getString(R.string.Vacancy);
        }else{
            return context.getResources().getString(R.string.Myvacancy);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new Other_vacancy();
        }
        else {
            return new MyVacancy();
        }    }
}