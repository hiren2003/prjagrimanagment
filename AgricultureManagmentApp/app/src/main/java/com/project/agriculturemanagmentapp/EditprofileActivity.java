package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class EditprofileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.Light_green));
    }
}