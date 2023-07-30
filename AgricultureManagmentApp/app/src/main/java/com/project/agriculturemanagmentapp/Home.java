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
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.Locale;

public class Home extends AppCompatActivity {
MeowBottomNavigation btmnv;
FrameLayout frameLayout;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.background));
        setLanguage();
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        btmnv=findViewById(R.id.btmnv);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.Hello)+",");
        getSupportActionBar().setSubtitle(sharedPreferences.getString("uname","man"));
        frameLayout =findViewById(R.id.fmlayout);
        btmnv.show(3,true);
        btmnv.add(new MeowBottomNavigation.Model(1, R.drawable.feed2));
        btmnv.add(new MeowBottomNavigation.Model(2, R.drawable.labour));
        btmnv.add(new MeowBottomNavigation.Model(3,R.drawable.house));
        btmnv.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_sell_24));
        btmnv.add(new MeowBottomNavigation.Model(5,R.drawable.online_store));
        btmnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
    @Override
    public Unit invoke(MeowBottomNavigation.Model model) {
        if(model.getId()==1){
            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.fmlayout,new Feed()).commit();
        } else if (model.getId()==2) {
            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.fmlayout,new Labour()).commit();
        } else if (model.getId()==4) {
            frameLayout.removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.fmlayout,new Resell()).commit();
        } else {

        }
        return null;
    }
});
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Home.this, Navigation.class));
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setLanguage();
    }
    public void setLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String lang = sharedPreferences.getString("getlen", "en");
        Locale locale = new Locale(lang, "rnIN");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }
}