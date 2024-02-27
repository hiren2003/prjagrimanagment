package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;

import static okhttp3.Response.*;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        String apiKey = "rzp_test_UHPUiPFiPwwVZt";
        String url = "https://api.razorpay.com/v1/payments?from=1704070861&to="+ Instant.now().getEpochSecond()+"&count=100";

        RequestQueue queue = Volley.newRequestQueue(this);

    }
}