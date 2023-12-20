package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class

OTP extends AppCompatActivity {
    TextView txtmo;
    FirebaseAuth mAuth;
    String VerificationId, Mobile, Uname, SmsCode,url;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sedit;
    ProgressBar progressBar;
    TextView txt,txtretry;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setLanguage();
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));
        Intent i = getIntent();
        Uname = i.getStringExtra("uname");
        Mobile = i.getStringExtra("mo");
        VerificationId = i.getStringExtra("vid");
        url=i.getStringExtra("url");
        firebaseDatabase=FirebaseDatabase.getInstance();
        EditText sms1 = findViewById(R.id.sms1);
        EditText sms2 = findViewById(R.id.sms2);
        EditText sms3 = findViewById(R.id.sms3);
        txtretry=findViewById(R.id.txtretry);
        EditText sms4 = findViewById(R.id.sms4);
        EditText sms5 = findViewById(R.id.sms5);
        EditText sms6 = findViewById(R.id.sms6);
        txt = findViewById(R.id.txt);
        progressBar = findViewById(R.id.progressBar2);
        txtmo = findViewById(R.id.txtmo);
        txtmo.setText(txtmo.getText() + " " + Mobile);
        RelativeLayout btnverify = findViewById(R.id.btnverify);
        TextView txtretry = findViewById(R.id.txtretry);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sedit = sharedPreferences.edit();
        txtretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTP.this,MainActivity.class));
            }
        });
        sms1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    sms2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sms2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    sms3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sms3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    sms4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sms4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    sms5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sms5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    sms6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendotp();
            }
        });
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                SmsCode = sms1.getText().toString() + sms2.getText().toString() + sms3.getText().toString() + sms4.getText().toString() + sms5.getText().toString() + sms6.getText().toString();
                verifyCode(SmsCode);
            }
        });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VerificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            txt.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            show_toast(getResources().getString(R.string.Verification_Failed),false);
        }
    };

    private void resendotp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + Mobile)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(OTP.this)
                        .setCallbacks(mCallBack)
                        .build();
        txt.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId, SmsCode);
        SigninWithDetail(credential);
    }

    private void SigninWithDetail(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OTP.this, Home.class);
                            sedit.putString("uname", Uname);
                            sedit.putString("mo", Mobile);
                            sedit.putString("url",url);
                            sedit.putBoolean("islogin",true);
                            sedit.apply();
                            sedit.commit();
                            firebaseDatabase.getReference().child("Users_List").child(Mobile).setValue(new clsUserModel(Uname,Mobile,url," "," "," "," "," "));
                            startActivity(intent);
                            show_toast(getResources().getString(R.string.Login_SuccessFully),true);
                            finish();
                        } else {
                            txt.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            show_toast(getResources().getString(R.string.Signin_Failed),false);}
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
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {

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