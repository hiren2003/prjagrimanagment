package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpFollowerAdapter extends FragmentPagerAdapter {
    String Mo;
    Context context;
    public VpFollowerAdapter(@NonNull FragmentManager fm, String Mo, Context context) {
        super(fm);
        this.Mo=Mo;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==1){
            return new FollowList(context.getString(R.string.Followers),Mo);
        }
        else{
            return new FollowList(context.getString(R.string.Following),Mo);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==1){
            return context.getString(R.string.Followers);
        }
        else{
            return context.getString(R.string.Following);        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
