package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VporderAdapter extends FragmentPagerAdapter {
    Context context;
    public VporderAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==1){
            return new datewiseorder();
        }
        else{
            return new Cancelled_order();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==1){
            return context.getString(R.string.My_Order);
        }
        else{
            return context.getString(R.string.Cancel_order);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
