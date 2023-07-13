package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class Language extends AppCompatActivity {

    ToggleButton tgleng, tglhnd, tglgjr, tgltml, tglknd, tglurd, tgltlg, tglbgl, tglpjb, tglmlylm;
    ImageButton btnsublang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        btnsublang=findViewById(R.id.btnsublang);
        tgleng = findViewById(R.id.tgleng);
        tglhnd = findViewById(R.id.tglhnd);
        tglgjr = findViewById(R.id.tglgjr);
        tgltml = findViewById(R.id.tgltml);
        tglknd = findViewById(R.id.tglknd);
        tgltlg = findViewById(R.id.tgltlg);
        tglurd = findViewById(R.id.tglurd);
        tglbgl = findViewById(R.id.tglbgl);
        tglpjb = findViewById(R.id.tglpjb);
        tglmlylm = findViewById(R.id.tglmlylm);
        tgleng.setTextOn("English");
        tglhnd.setTextOn("हिन्दी");
        tglgjr.setTextOn("ગુજરાતી");
        tgltml.setTextOn("தமிழ்");
        tglknd.setTextOn("ಕನ್ನಡ");
        tglurd.setTextOn("اردو");
        tgltlg.setTextOn("తెలుగు");
        tglbgl.setTextOn("বাংলা");
        tglpjb.setTextOn("پَن٘جابی");
        tglmlylm.setTextOn("മലയാളം");
        tgleng.setChecked(true);
        tgleng.setTextColor(getResources().getColor(R.color.Dark_green));
        btnsublang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Language.this,MainActivity.class));
            }
        });
        tgleng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selelang(tgleng,true);
                }
                else{
                    selelang(tgleng,false);
                }
            }
        });
        tglhnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglhnd, true);
                } else {
                    selelang(tglhnd, false);
                }
            }
        });
        tglgjr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglgjr, true);
                } else {
                    selelang(tglgjr, false);
                }
            }
        });
        tgltml.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tgltml, true);
                } else {
                    selelang(tgltml, false);
                }
            }
        });
        tglknd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglknd, true);
                } else {
                    selelang(tglknd, false);
                }
            }
        });
        tgltlg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tgltlg, true);
                } else {
                    selelang(tgltlg, false);
                }
            }
        });
        tglurd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglurd, true);
                } else {
                    selelang(tglurd, false);
                }
            }
        });
        tglbgl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglbgl, true);
                } else {
                    selelang(tglbgl, false);
                }
            }
        });
        tglpjb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglpjb, true);
                } else {
                    selelang(tglpjb, false);
                }
            }
        });
        tglmlylm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selelang(tglmlylm, true);
                } else {
                    selelang(tglmlylm, false);
                }
            }
        });
    }

    public void selelang(ToggleButton toggleButton, Boolean isChecked) {
        if (isChecked) {
            int red = getResources().getColor(R.color.Dark_red);
            int green = getResources().getColor(R.color.Dark_green);
            tgleng.setChecked(false);
            tgleng.setTextColor(red);
            tglhnd.setChecked(false);
            tglhnd.setTextColor(red);
            tglgjr.setChecked(false);
            tglgjr.setTextColor(red);
            tgltml.setChecked(false);
            tgltml.setTextColor(red);
            tglknd.setChecked(false);
            tglknd.setTextColor(red);
            tglurd.setChecked(false);
            tglurd.setTextColor(red);
            tgltlg.setChecked(false);
            tgltlg.setTextColor(red);
            tglbgl.setChecked(false);
            tglbgl.setTextColor(red);
            tglpjb.setChecked(false);
            tglpjb.setTextColor(red);
            tglmlylm.setChecked(false);
            tglmlylm.setTextColor(red);
            toggleButton.setTextColor(green);
            toggleButton.setChecked(true);
        } else {
            int red = getResources().getColor(R.color.Dark_red);
            tgleng.setChecked(false);
            tgleng.setTextColor(red);
            tglhnd.setChecked(false);
            tglhnd.setTextColor(red);
            tglgjr.setChecked(false);
            tglgjr.setTextColor(red);
            tgltml.setChecked(false);
            tgltml.setTextColor(red);
            tglknd.setChecked(false);
            tglknd.setTextColor(red);
            tglurd.setChecked(false);
            tglurd.setTextColor(red);
            tgltlg.setChecked(false);
            tgltlg.setTextColor(red);
            tglbgl.setChecked(false);
            tglbgl.setTextColor(red);
            tglpjb.setChecked(false);
            tglpjb.setTextColor(red);
            tglmlylm.setChecked(false);
            tglmlylm.setTextColor(red);
        }

    }
}