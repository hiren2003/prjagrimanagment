package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import java.util.Locale;

public class create_feed extends AppCompatActivity {

    Toolbar toolbar;
    Toolbar toolbar1;
    TextView txtadd,post,uname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feed);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.background));
        setLanguage();
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        toolbar =findViewById(R.id.toolbar);
        toolbar1=findViewById(R.id.toolbar1);
        txtadd=findViewById(R.id.txtadd);
        post=findViewById(R.id.post);
        uname=findViewById(R.id.uname);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle(getResources().getString(R.string.Hello)+",");
        getSupportActionBar().setSubtitle(sharedPreferences.getString("uname","man"));
    }



    private void setLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String lang = sharedPreferences.getString("getlen", "en");
        Locale locale = new Locale(lang, "rnIN");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }
}