package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class splashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.lan));
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("islogin", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isConnected()){
                    if (isLogin) {
                        startActivity(new Intent(splashActivity.this, Home.class));
                        finish();
                    } else {
                        startActivity(new Intent(splashActivity.this, Language.class));
                        finish();
                    }
                }
                else{
                    AlertDialog.Builder alert= new AlertDialog.Builder(splashActivity.this);
                    alert.setIcon(getDrawable(R.drawable.baseline_signal_wifi_connected_no_internet_4_24));
                    alert.setTitle(getString(R.string.Network_error));
                    alert.setMessage(getResources().getString(R.string.internet_on)+"\n \n \n");
                    AlertDialog internetbox=alert.create();
                    internetbox.setOnDismissListener(new DialogInterface.OnDismissListener() {
                       @Override
                       public void onDismiss(DialogInterface dialog) {
                           if(isConnected()==false){
                               internetbox.show();
                           }
                           else{
                               if (isLogin) {
                                   startActivity(new Intent(splashActivity.this, Home.class));
                                   finish();
                               } else {
                                   startActivity(new Intent(splashActivity.this, Language.class));
                                   finish();
                               }
                           }
                       }
                   });
                    internetbox.show();
                }
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {

    }
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Network network = cm.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
            if (networkCapabilities == null) {
                return false;
            }
            boolean isInternetSuspended = !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED);
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    && !isInternetSuspended;
        } else {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        }
        return false;
    }
}