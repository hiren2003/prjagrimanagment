package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class vpImg extends PagerAdapter {
    ArrayList<Uri> arrayList;
    Context context;

    public vpImg(ArrayList<Uri> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytimg,container,false);
        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageURI(arrayList.get(position));
        container.addView(view);
        return  view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((RelativeLayout)object).removeView(container);
    }
}
