package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.project.agriculturemanagmentapp.databinding.ActivityAddLabourVacancyBinding;

import java.util.Calendar;

public class add_labour_vacancy extends AppCompatActivity {
RelativeLayout btnsavedetail;
TextInputEditText edtoname,edtmo,edtwtype,edtincome,edtduration,edtstate,edtdist,edttehsil,edtvlg,edtdes;
RadioButton rdbfixwages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labour_vacancy);
        btnsavedetail=findViewById(R.id.btnsavevdetail);
        edtoname=findViewById(R.id.edtoname);
        edtmo=findViewById(R.id.edtmo);
        edtincome=findViewById(R.id.edtincome);
        edtduration=findViewById(R.id.edtduration);
        edtstate=findViewById(R.id.edtstate);
        edtdist=findViewById(R.id.edtdist);
        edtwtype=findViewById(R.id.edtwtype);
        edttehsil=findViewById(R.id.edttehsil);
        edtvlg=findViewById(R.id.edtvlg);
        edtdes=findViewById(R.id.edtdes);
        rdbfixwages=findViewById(R.id.rdbfixwages);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        btnsavedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(add_labour_vacancy.this, "hello", Toast.LENGTH_SHORT).show();
                String mo = sharedPreferences.getString("mo", "1234567890");
                String key=FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").push().getKey().toString();
                clsVacancyModel obj=  new clsVacancyModel(key,
                        edtoname.getText().toString(),
                        edtmo.getText().toString(),
                        edtwtype.getText().toString(),
                        rdbfixwages.isChecked()==true?"Fix Wages":"Partenership",
                        edtincome.getText().toString(),
                        edtduration.getText().toString(),
                        edtstate.getText().toString(),
                        edtdist.getText().toString(),
                        edttehsil.getText().toString(),
                        edtvlg.getText().toString(),
                        edtdes.getText().toString(),
                        sharedPreferences.getString("url","null"),
                        sharedPreferences.getString("uname","null"),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR)
                );
                FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").child(key).setValue(obj);
                FirebaseDatabase.getInstance().getReference().child("Labour_Vacancy").child(key).setValue(obj);
                finish();
            }
        });

    }
}