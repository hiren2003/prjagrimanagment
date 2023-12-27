package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class add_labour extends AppCompatActivity {
    TextInputEditText edtlname, edtlmo, edtlplace, edtlwages, edtldes;
    ImageView imageView,rldate;
    RelativeLayout submit;
    TextView date;
    DatePickerDialog datePickerDialog;
    SharedPreferences sharedPreferences;
    boolean isDateSelected = false;

    ProgressBar progressBar;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labour);
        setLanguage();
        edtlname = findViewById(R.id.edtlname);
        edtlmo = findViewById(R.id.edtlmo);
        edtlplace = findViewById(R.id.edtlplace);
        txt = findViewById(R.id.txt);
        edtlwages = findViewById(R.id.edtlwages);
        edtldes = findViewById(R.id.edtldes);
        progressBar = findViewById(R.id.progressBar2);
        rldate = findViewById(R.id.rldate);
        submit = findViewById(R.id.btnsavedetail);
        date = findViewById(R.id.txtdate);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        datePickerDialog = new DatePickerDialog(add_labour.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                date.setText(dayOfMonth + "/" + month + "/" + year);
                isDateSelected = true;

            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        rldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtlname.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Lname), false);
                    edtlname.requestFocus();
                } else if (edtlmo.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Mo), false);
                    edtlmo.requestFocus();
                } else if (edtlmo.getText().toString().trim().length() != 10) {
                    show_toast(getResources().getString(R.string.Invalid_MobileNumber), false);
                    edtlmo.requestFocus();
                } else if (edtlwages.getText().toString().trim().isEmpty()) {
                    show_toast(getResources().getString(R.string.Please_Enter_Wages), false);
                    edtlwages.requestFocus();
                } else if (isDateSelected == false) {
                    show_toast(getResources().getString(R.string.Please_Enter_Date), false);
                    datePickerDialog.show();
                } else {
                    submit.setEnabled(false);
                    submit.setClickable(false);
                    progressBar.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.GONE);
                    String key = FirebaseDatabase.getInstance().getReference().child("Labor_data").child(sharedPreferences.getString("mo", "1234567890")).push().getKey();
                    FirebaseDatabase.getInstance().getReference().child("Labor_data").child(sharedPreferences.getString("mo", "1234567890")).child(key).setValue(
                            new clsLaborModel(
                                    edtlname.getText().toString(),
                                    edtlmo.getText().toString(),
                                    edtlplace.getText().toString(),
                                    edtlwages.getText().toString(),
                                    edtldes.getText().toString(),
                                    date.getText().toString(),
                                    key
                            )).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.setVisibility(View.GONE);
                            txt.setVisibility(View.VISIBLE);
                            String msg = getResources().getString(R.string.Hello) + " " + edtlname.getText().toString() + " ,\n" + getResources().getString(R.string.msg1) + "\n" +
                                    getResources().getString(R.string.Worker_name) + " : " + edtlname.getText().toString() + "\n" +
                                    getResources().getString(R.string.loc) + " : " + edtlplace.getText().toString() + "\n" +
                                    getResources().getString(R.string.date) + " : " + date.getText().toString() + "\n" +
                                    getResources().getString(R.string.Wages) + " : " + edtlwages.getText().toString() + "\n" +
                                    getResources().getString(R.string.Sender) + " : " + sharedPreferences.getString("uname", "unknown");
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("+91" + edtlmo.getText().toString(), null, msg, null, null);
                            show_toast(getResources().getString(R.string.Upload_Successfully), true);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            show_toast(getResources().getString(R.string.Upload_Cancelled), false);
                        }
                    });
                }
            }

        });
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
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    public void show_toast(String msg, boolean isgreen) {
        Toast ts = new Toast(getBaseContext());
        View view;
        if (isgreen) {
            view = getLayoutInflater().inflate(R.layout.lyt_green_toast, (ViewGroup) findViewById(R.id.container));
        } else {
            view = getLayoutInflater().inflate(R.layout.lyt_red_toast, (ViewGroup) findViewById(R.id.container));
        }
        TextView txtmessage = view.findViewById(R.id.txtmsg);
        txtmessage.setText(msg);
        ts.setView(view);
        ts.setGravity(Gravity.TOP, 0, 30);
        ts.setDuration(Toast.LENGTH_SHORT);
        ts.show();

    }
}