package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    MeowBottomNavigation btmnv;
    FrameLayout frameLayout;
    RelativeLayout toolbar;
    ImageView prfpc, imgcart, imgorder;
    TextView txtname;
    BubbleNavigationLinearView bubbleNavigationLinearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        setLanguage();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        btmnv = findViewById(R.id.btmnv);
        toolbar = findViewById(R.id.toolbar);
        prfpc = findViewById(R.id.prfpc);
        imgcart = findViewById(R.id.imgcart);
        frameLayout = findViewById(R.id.fmlayout);
        txtname = findViewById(R.id.txtname);
        imgorder = findViewById(R.id.imgorder);
        bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        txtname.setText(sharedPreferences.getString("uname", "man"));
        Glide.with(this)
                .load(sharedPreferences.getString("url", "null"))
                .circleCrop()
                .into(prfpc);
        frameLayout.removeAllViews();
        getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new ShowFeed()).commit();
        prfpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Navigation.class));
            }
        });
        imgorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MyOrder.class));
            }
        });
        imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Cart.class));
            }
        });
        btmnv.show(3, true);
        btmnv.add(new MeowBottomNavigation.Model(1, R.drawable.feed2));
        btmnv.add(new MeowBottomNavigation.Model(2, R.drawable.labour));
        btmnv.add(new MeowBottomNavigation.Model(3, R.drawable.house));
        btmnv.add(new MeowBottomNavigation.Model(4, R.drawable.resell));
        btmnv.add(new MeowBottomNavigation.Model(5, R.drawable.online_store));
        btmnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if (model.getId() == 0) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new ShowFeed()).commit();
                } else if (model.getId() == 1) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Labour_Vacancy()).commit();
                }  else if (model.getId() == 3) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Resell()).commit();
                } else if (model.getId() == 4) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new E_commrce()).commit();
                }
                else {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new frghome()).commit();
                }
                return null;
            }
        });
        bubbleNavigationLinearView.setCurrentActiveItem(2);
        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if (position == 0) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new ShowFeed()).commit();
                } else if (position == 1) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Other_vacancy()).commit();
                } else if (position == 2) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new frghome()).commit();
                } else if (position == 3) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Resell()).commit();
                } else if (position == 4) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new E_commrce()).commit();
                } else {

                }
            }
        });

    }




    public void setLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String lang = sharedPreferences.getString("getlen", "en");
        Locale locale = new Locale(lang, "rnIN");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
}