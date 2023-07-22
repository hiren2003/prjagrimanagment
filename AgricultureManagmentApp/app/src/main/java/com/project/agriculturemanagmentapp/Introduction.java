package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Locale;

public class Introduction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        setLanguage();
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