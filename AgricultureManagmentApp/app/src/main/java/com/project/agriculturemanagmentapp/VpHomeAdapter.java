package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpHomeAdapter extends FragmentPagerAdapter {
    Context context;

    @Override
    public int getCount() {
        return 5;
    }

    public VpHomeAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
       return new ShowFeed();
        } else if (position == 1) {
            return  new Other_vacancy();
        } else if (position == 2) {
          return  new frghome(context);
        } else if (position == 3) {
            return new Resell();
        } else {
           return new E_commrce();
        }
    }

}
