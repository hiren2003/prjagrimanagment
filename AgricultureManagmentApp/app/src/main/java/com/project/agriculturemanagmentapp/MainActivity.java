package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    TextInputEditText edtuname, edtmo;
    RelativeLayout btngetotp;
    FirebaseAuth mAuth;
    String VerificationId, SmsCode;
    ProgressBar progressBar;
    TextView txt;
    CircleImageView prfpc;
    StorageReference reference;
    ActivityResultLauncher<String> launcher;
    Uri uri=null;
    String uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLanguage();
        edtuname = findViewById(R.id.edtuname);
        edtmo = findViewById(R.id.edtmo);
        btngetotp = findViewById(R.id.btngetotp);
        prfpc = findViewById(R.id.prfpc);
        txt = findViewById(R.id.txt);
        progressBar = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));

        prfpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                prfpc.setImageURI(result);
                uri = result;
            }
        });

        btngetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtuname.getText().toString().isEmpty()) {
                    show_toast(getResources().getString(R.string.Username_Required),false);
                    edtuname.requestFocus();
                }
                else {
                    if (edtmo.getText().toString().length() != 10) {
                        show_toast(getResources().getString(R.string.Invalid_MobileNumber),false);
                        edtmo.requestFocus();
                    } else if (uri==null) {
                        show_toast(getResources().getString(R.string.Select_Image),false);
                        launcher.launch("image/*");
                    } else {
                        txt.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        sendOtp();
                        reference = FirebaseStorage.getInstance().getReference().child("User_Profiles").child(edtmo.getText().toString());
                        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        uri2 = uri.toString();
                                    }
                                });
                            }
                        });
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
            VerificationId = s;
            Intent intent = new Intent(MainActivity.this, OTP.class);
            intent.putExtra("uname", edtuname.getText().toString());
            intent.putExtra("mo", edtmo.getText().toString());
            intent.putExtra("vid", VerificationId);
            intent.putExtra("url", uri2);
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
            show_toast(getResources().getString(R.string.Verification_Failed),false);
        }
    };

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