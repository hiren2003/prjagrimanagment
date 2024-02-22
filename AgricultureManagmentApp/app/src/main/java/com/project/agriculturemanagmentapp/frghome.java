package com.project.agriculturemanagmentapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.SEND_SMS;

public class frghome extends Fragment {
    RcnewsAdapter rcnewsAdapter;
    ImageView imageview;
    Context context;
    TextView txttempcity, txtdes,txttemp, txtmintemp, txtmaxtemp, txthumidity, txtcloud, txtpressure, txtwind, txtvisiblity;
    String api = "c19c16e82898cc7627f4e02e485861de";
    String url = "https://api.openweathermap.org/data/2.5/weather";
    DecimalFormat decimalFormat;
    String des, speed, city, visiblity, cloud, icon;
    Double feels_like, temp, temp_max, temp_min;
    float pressure;
    int humidity;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude = "";
    String longitude = "";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frghome() {
        // Required empty public constructor
    }
    public frghome(Context context) {
        // Required empty public constructor
        this.context=context;
    }
    public static frghome newInstance(String param1, String param2) {
        frghome fragment = new frghome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frghome, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcnews);
        setTemprature(28.7041, 77.1025);
        decimalFormat = new DecimalFormat("#.##");
        txttemp = view.findViewById(R.id.txttemp);
        txtmaxtemp = view.findViewById(R.id.txttemphigh);
        txtmintemp = view.findViewById(R.id.txttemplow);
        txthumidity = view.findViewById(R.id.txthumidity);
        txtcloud = view.findViewById(R.id.txtcloud);
        txtwind = view.findViewById(R.id.txtwind);
        txttempcity=view.findViewById(R.id.txtcity);
        txtdes=view.findViewById(R.id.txtdes);
        txtpressure = view.findViewById(R.id.txtpressure);
        imageview = view.findViewById(R.id.imageview);
        txtvisiblity = view.findViewById(R.id.txtvisiblity);
        FirebaseDatabase.getInstance().getReference().child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsNewsModel> clsNewsModelArrayList=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    clsNewsModelArrayList.add(datasnapshot.getValue(clsNewsModel.class));
                }
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                rcnewsAdapter = new RcnewsAdapter( getContext(),false,clsNewsModelArrayList);
                recyclerView.setAdapter(rcnewsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 101);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGps();
        } else {
            getlocation();
        }
        return view;
    }

    public void setTemprature(Double lat, Double lng) {
        String tempurl = "";
        tempurl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng + "&appid=" + api;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonobjectarr = jsonArray.getJSONObject(0);
                    des = jsonobjectarr.getString("description");
                    JSONObject objectmain = jsonObject.getJSONObject("main");
                    feels_like = objectmain.getDouble("feels_like");
                    humidity = objectmain.getInt("humidity");
                    pressure = objectmain.getInt("pressure");
                    temp = objectmain.getDouble("temp") - 273.15;
                    temp_max = objectmain.getDouble("temp_max") - 273.15;
                    temp_min = objectmain.getDouble("temp_min") - 273.15;
                    JSONObject objectwind = jsonObject.getJSONObject("wind");
                    city = jsonObject.getString("name");
                    visiblity = jsonObject.getString("visibility");
                    speed = objectwind.getString("speed");
                    icon = jsonobjectarr.getString("icon");
                    System.out.println(icon);
                    JSONObject onjcloud = jsonObject.getJSONObject("clouds");
                    cloud = onjcloud.getString("all");
                    txttemp.setText(decimalFormat.format(temp) + "°C");
                    txtmaxtemp.setText(decimalFormat.format(temp_max) + "°C");
                    txtmintemp.setText(decimalFormat.format(temp_min) + "°C");
                    txthumidity.setText(humidity + "%");
                    txtwind.setText(speed + "m/s");
                    txtcloud.setText(cloud + "%");
                    txtpressure.setText(pressure + "");
                    txtvisiblity.setText(visiblity + "");
                    txttempcity.setText(city);
                    txtdes.setText(des);
                    String img = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                    Glide.with(context)
                            .load(img)
                            .into(imageview);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void onGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable Gps").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
    }

    public void getlocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 101);
        }
        else
        {
            Criteria criteria=new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria,true);
            if (provider==null){
                setTemprature(28.7041,77.1025);
            }
            else{
                Location location =locationManager.getLastKnownLocation(provider);
                setTemprature(location.getLatitude(),location.getLongitude());
            }
        }
    }
}