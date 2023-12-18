package com.project.agriculturemanagmentapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VpProfileAdapter extends FragmentPagerAdapter {
    Context context;
    String Mo;
    boolean SelfAccount;
    public VpProfileAdapter(@NonNull FragmentManager fm,Context context,String Mo,boolean SelfAccount) {
        super(fm);
        this.context=context;
        this.Mo=Mo;
        this.SelfAccount=SelfAccount;
    }

    @Override
    public int getCount() {
if (SelfAccount){
    return 4;

}
    else{
        return 3;
}
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
      if (SelfAccount){
          if (position == 0) {
              return new Myfeed(Mo,SelfAccount);
          } else if (position == 1) {
              return new MyVacancy(Mo,SelfAccount);
          } else if (position == 2) {
              return new Labour_Management();
          } else {
              return new MyProducts(Mo,SelfAccount);
          }
      }
      else{
          if (position == 0) {
              return new Myfeed(Mo,SelfAccount);
          } else if (position == 1) {
              return new MyVacancy(Mo,SelfAccount);
          } else {
              return new MyProducts(Mo,SelfAccount);
          }
      }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       if (SelfAccount){
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
       else{
           if (position == 0) {
               return context.getString(R.string.My_Feed);
           } else if (position == 1) {
               return context.getString( R.string.Labour_Vacancy);
           } else {
               return  context.getString(R.string.My_Product);
           }
       }
    }
}
