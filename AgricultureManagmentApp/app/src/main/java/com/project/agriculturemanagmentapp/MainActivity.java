package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextInputEditText edtuname, edtmo;
    RelativeLayout btngetotp;
    FirebaseAuth mAuth;
     String VerificationId,SmsCode;
     ProgressBar progressBar;
     TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtuname = findViewById(R.id.edtuname);
        edtmo = findViewById(R.id.edtmo);
        btngetotp = findViewById(R.id.btngetotp);
        txt=findViewById(R.id.txt);
        progressBar=findViewById(R.id.progressBar2);
        mAuth= FirebaseAuth.getInstance();
        btngetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtuname.getText().toString().isEmpty()) {
                    edtuname.setError(getResources().getString(R.string.Username_Required));
                } else {
                    if (edtmo.getText().toString().length() != 10) {
                        edtmo.setError(getResources().getString(R.string.Invalid_MobileNumber));
                    } else {
                        txt.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        sendOtp();
                    }

                }
            }
        });
    }

    private void sendOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + edtmo.getText().toString())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(MainActivity.this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(s, forceResendingToken);
        VerificationId=s;
        Intent intent= new Intent(MainActivity.this,OTP.class);
        intent.putExtra("uname",edtuname.getText().toString());
        intent.putExtra("mo",edtmo.getText().toString());
        intent.putExtra("vid",VerificationId);
        Toast.makeText(MainActivity.this, getResources().getString(R.string.Code_sent), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

    }
    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
        txt.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this,getResources().getString(R.string.Verification_Failed), Toast.LENGTH_SHORT).show();
    }
};

}