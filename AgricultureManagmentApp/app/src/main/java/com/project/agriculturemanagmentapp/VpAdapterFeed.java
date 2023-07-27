package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpAdapterFeed extends FragmentPagerAdapter {
Context context;
    RelativeLayout r1,r2;
    public VpAdapterFeed(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
        this.r1=r1;
        this.r2=r2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
    if(position==0){

        return new ShowFeed();
    }
    else{

        return new Myfeed();
    }
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return context.getResources().getString(R.string.Explore_Feed);
        }
        else{
            return context.getResources().getString(R.string.My_Feed);
        }
    }
}
