package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class add_labour_vacancy extends AppCompatActivity {
    RelativeLayout btnsavedetail;
    TextInputEditText edtoname, edtmo, edtwtype, edtincome, edtduration, edtstate, edtdist, edttehsil, edtvlg, edtdes;
    RadioButton rdbfixwages, rdbprtsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labour_vacancy);
        btnsavedetail = findViewById(R.id.btnsavevdetail);
        edtoname = findViewById(R.id.edtoname);
        edtmo = findViewById(R.id.edtmo);
        edtincome = findViewById(R.id.edtincome);
        edtduration = findViewById(R.id.edtduration);
        edtstate = findViewById(R.id.edtstate);
        edtdist = findViewById(R.id.edtdist);
        edtwtype = findViewById(R.id.edtwtype);
        edttehsil = findViewById(R.id.edttehsil);
        edtvlg = findViewById(R.id.edtvlg);
        edtdes = findViewById(R.id.edtdes);
        rdbfixwages = findViewById(R.id.rdbfixwages);
        rdbprtsp = findViewById(R.id.rdbprtsp);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.loginback));
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        btnsavedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtoname.getText().toString().trim().isEmpty()) {
                    edtoname.setError(getResources().getString(R.string.Required_Field));
                } else if (edtmo.getText().toString().trim().isEmpty()) {
                    edtmo.setError(getResources().getString(R.string.Required_Field));
                } else if (edtmo.getText().toString().trim().length() != 10) {
                    edtmo.setError(getResources().getString(R.string.Invalid_MobileNumber));
                } else if (edtwtype.getText().toString().trim().isEmpty()) {
                    edtwtype.setError(getResources().getString(R.string.Required_Field));
                } else if (edtincome.getText().toString().trim().isEmpty()) {
                    edtincome.setError(getResources().getString(R.string.Required_Field));
                } else if (edtduration.getText().toString().trim().isEmpty()) {
                    edtduration.setError(getResources().getString(R.string.Required_Field));
                } else if (edtstate.getText().toString().trim().isEmpty()) {
                    edtstate.setError(getResources().getString(R.string.Required_Field));
                } else if (edttehsil.getText().toString().trim().isEmpty()) {
                    edttehsil.setError(getResources().getString(R.string.Required_Field));
                } else if (edtvlg.getText().toString().trim().isEmpty()) {
                    edtvlg.setError(getResources().getString(R.string.Required_Field));
                } else if (edtdist.getText().toString().trim().isEmpty()) {
                    edtdist.setError(getResources().getString(R.string.Required_Field));
                } else if (!rdbfixwages.isChecked() && !rdbprtsp.isChecked()) {
                    Toast.makeText(add_labour_vacancy.this, getResources().getString(R.string.Required_Field), Toast.LENGTH_SHORT).show();
                }
                else{
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
            }
        });

    }
}