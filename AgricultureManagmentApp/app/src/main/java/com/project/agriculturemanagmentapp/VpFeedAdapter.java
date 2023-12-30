package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpFeedAdapter extends FragmentPagerAdapter {
    Context context;
    public VpFeedAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new ShowFeed(true);
        }
        else{
            return new ShowFeed(false);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return context.getString(R.string.My_Feed);
        }
        else{
            return context.getString(R.string.Explore_Feed);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
