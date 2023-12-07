package com.project.agriculturemanagmentapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
                    edtoname.requestFocus();
                    show_toast(getResources().getString(R.string.Please_Enter_Owner), false);
                } else if (edtmo.getText().toString().trim().isEmpty()) {
                    edtmo.requestFocus();
                    show_toast(getResources().getString(R.string.Please_Enter_Mo), false);
                } else if (edtmo.getText().toString().trim().length() != 10) {
                    edtmo.requestFocus();
                    show_toast(getResources().getString(R.string.Invalid_MobileNumber), false);
                } else if (edtwtype.getText().toString().trim().isEmpty()) {
                    edtwtype.requestFocus();
                    show_toast(getResources().getString(R.string.Please_Enter_Worktype), false);
                } else if (edtincome.getText().toString().trim().isEmpty()) {
                    edtincome.requestFocus();
                    show_toast(getResources().getString(R.string.Please_Enter_pincome), false);
                } else if (edtduration.getText().toString().trim().isEmpty()) {
                    edtduration.requestFocus();
                    show_toast(getResources().getString(R.string.Please_Enter_wDuration), false);
                } else if (edtstate.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_State), false);
                    edtstate.requestFocus();
                } else if (edtdist.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_District), false);
                    edtdist.requestFocus();
                } else if (edttehsil.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Tehsil), false);
                    edttehsil.requestFocus();
                } else if (edtvlg.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Village), false);
                    edtvlg.requestFocus();
                } else if (!rdbfixwages.isChecked() && !rdbprtsp.isChecked()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Wagetype), false);
                    edtvlg.requestFocus();
                } else {
                    Dialog dg=new Dialog(add_labour_vacancy.this);
                    dg.setContentView(R.layout.lytloading);
                    dg.getWindow().setBackgroundDrawableResource(R.drawable.curvebackground);
                    dg.setCancelable(false);
                    dg.show();
                    String mo = sharedPreferences.getString("mo", "1234567890");
                    String key = FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").push().getKey().toString();
                    clsVacancyModel obj = new clsVacancyModel(key,
                            edtoname.getText().toString(),
                            edtmo.getText().toString(),
                            edtwtype.getText().toString(),
                            rdbfixwages.isChecked() == true ? "Fix Wages" : "Partenership",
                            edtincome.getText().toString(),
                            edtduration.getText().toString(),
                            edtstate.getText().toString(),
                            edtdist.getText().toString(),
                            edttehsil.getText().toString(),
                            edtvlg.getText().toString(),
                            edtdes.getText().toString(),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR),
                            sharedPreferences.getString("mo", "1234567890")
                    );
                    FirebaseDatabase.getInstance().getReference().child("User").child(mo).child("MyVacancy").child(key).setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FirebaseDatabase.getInstance().getReference().child("Labour_Vacancy").child(key).setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    show_toast(getResources().getString(R.string.successfullyuploaded),true);
                                    dg.dismiss();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    show_toast(getResources().getString(R.string.unsuccessfullyuploaded),false);
                                    dg.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            show_toast(getResources().getString(R.string.unsuccessfullyuploaded),false);
                            dg.dismiss();
                        }
                    });
                }

            }
        });

    }

    public void show_toast(String msg, boolean isgreen) {
        Toast ts = new Toast(getBaseContext());
        View view;
        if (isgreen) {
            view = getLayoutInflater().inflate(R.layout.lyttoastgreen, (ViewGroup) findViewById(R.id.container));
        } else {
            view = getLayoutInflater().inflate(R.layout.lyttoast, (ViewGroup) findViewById(R.id.container));
        }
        TextView txtmessage = view.findViewById(R.id.txtmsg);
        txtmessage.setText(msg);
        ts.setView(view);
        ts.setGravity(Gravity.TOP, 0, 30);
        ts.setDuration(Toast.LENGTH_SHORT);
        ts.show();

    }
}