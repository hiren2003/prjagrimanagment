package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class admin_home extends AppCompatActivity {
    RelativeLayout cduser, cdnews, cdgov, cdorder, cdaddprdt,eshop,cdanimal,rvhome;
    int TotalOrder=0,TotalDays,ModeCode=0,ModeOnline=0;
    float TotalAmount=0;
    PieChart pie_paymentmode,pie_feedtype,pie_statewise,pie_resellsplit;
    TextView txtname,txtmo;
    TextView txtallorder,txtavgorder,txtallpayment,txtavgpayment;
    TextView txtttlfeed,txtttlcmt,txtttllike,txtavgposts;
    BarChart bar_monthwisefeed;
    BarChart Bar_monthlyorder;
    int ttlcmt=0,ttllike=0,avgpost=0,ttltweet=0,ttlpic=0;
    long ttlfeed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        cduser = findViewById(R.id.user);
        cdnews = findViewById(R.id.addnews);
        cdgov = findViewById(R.id.addgovscheme);
        cdorder = findViewById(R.id.orderlist);
        cdanimal=findViewById(R.id.cdanimal);
        cdaddprdt = findViewById(R.id.addprdt);
        eshop = findViewById(R.id.ecomprdt);
        rvhome=findViewById(R.id.rvhome);
        pie_statewise=findViewById(R.id.pie_statewise);
        bar_monthwisefeed=findViewById(R.id.bar_monthwisefeed);
        pie_paymentmode=findViewById(R.id.pie_paymentmode);
        txtallorder=findViewById(R.id.txtallorder);
        txtavgorder=findViewById(R.id.txtavgorder);
        txtallpayment=findViewById(R.id.txtallpayment);
        txtavgpayment=findViewById(R.id.txtavgpayment);
        txtname=findViewById(R.id.txtname);
        txtmo=findViewById(R.id.txtmo);
        txtttlfeed=findViewById(R.id.txtttlfeed);
        txtavgposts=findViewById(R.id.txtavdpost);
        txtttlcmt=findViewById(R.id.txtttlcmt);
        txtttllike=findViewById(R.id.txtttllikes);
        Bar_monthlyorder=findViewById(R.id.Bar_monthlyorder);
        pie_feedtype=findViewById(R.id.pie_feedtype);
        pie_resellsplit=findViewById(R.id.pie_resellsplit);
        TotalDays= Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        txtname.setText(sharedPreferences.getString("uname","Anonymous"));
        txtmo.setText(sharedPreferences.getString("mo","Anonymous"));
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.ct));
        rvhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this,Home.class));
            }
        });
        cduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, User_list.class));
            }
        });
        cdnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, news.class));
            }
        });
        cdgov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, govermentScheme.class).putExtra("type", 1));
            }
        });
        cdanimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, admin_resell_category.class));
            }
        });
        eshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, admin_ecom.class));
            }
        });
        cdorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, orderbydate.class));
            }
        });
        cdaddprdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, add_ecomm.class));
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 TotalOrder=0;ModeCode=0;ModeOnline=0;
                 TotalAmount=0;
                 int mon[]=new int[12];
                for (int i = 0; i < mon.length; i++) {
                    mon[i]=0;
                }
                int i=0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    for (DataSnapshot childdataSnapshot:dataSnapshot.getChildren()){
                        TotalOrder++;
                        clsOrderModel orderModel=childdataSnapshot.getValue(clsOrderModel.class);
                        TotalAmount+=(Float.parseFloat(orderModel.getQty())*Float.parseFloat(orderModel.getClsEcommModel().getPrice()));
                        String date=orderModel.getDate();
                        String temp[]=date.split("-");
                        i=Integer.parseInt(temp[1]);
                        mon[i]++;
                        if(orderModel.getPaymentMode().equals("COD")){
                            ModeCode++;
                        }
                        else{
                            ModeOnline++;
                        }
                    }
                }
                txtallpayment.setText("₹"+TotalAmount);
                txtavgpayment.setText("₹"+(TotalAmount/TotalOrder));
                double temp=(TotalOrder/TotalDays);
                txtavgorder.setText(""+temp);
                txtallorder.setText(""+TotalOrder);
                ArrayList<PieEntry> arrayList=new ArrayList<>();
                arrayList.add(new PieEntry(ModeCode,"COD"));
                arrayList.add(new PieEntry(ModeOnline,"Online"));
                PieDataSet pieDataSet=new PieDataSet(arrayList,"");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pie_paymentmode.setData(new PieData(pieDataSet));
                pie_paymentmode.animateXY(500,500);
                pie_paymentmode.getDescription().setEnabled(false);

                ArrayList<BarEntry> arrmonthwise=new ArrayList<>();
                for ( i = 0; i < mon.length; i++) {
                    arrmonthwise.add(new BarEntry(i+1,mon[i]));
                }
                BarDataSet barDataSet=new BarDataSet(arrmonthwise,"");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                Bar_monthlyorder.setData(new BarData(barDataSet));
                Bar_monthlyorder.animateXY(500,500);
                Bar_monthlyorder.getDescription().setEnabled(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                avgpost=0;ttltweet=0;ttlpic=0;ttlfeed=0;
                ttlfeed=snapshot.getChildrenCount();
                int monthcnt []=new int[12];
                Map<String,Integer> mapmonorder= new HashMap<>();
                for (int i = 0; i < monthcnt.length; i++) {
                    monthcnt[i]=0;
                }
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    clsFeedModel clsFeedModel=dataSnapshot.getValue(com.project.agriculturemanagmentapp.clsFeedModel.class);
                    String [] split=clsFeedModel.getDate().split("/");
                    int month=Integer.parseInt(split[1]);
                    monthcnt[month]++;
                    if (clsFeedModel.getMediatype().equals("1")){
                        ttlpic++;
                    }
                    else{
                        ttltweet++;
                    }
                }
                txtttlfeed.setText(""+ttlfeed);
                float temp=(ttlfeed/TotalDays);
                txtavgposts.setText(""+temp);
                ArrayList<PieEntry> arrayList=new ArrayList<>();
                arrayList.add(new PieEntry(ttlpic,getApplicationContext().getString(R.string.Total_picture)));
                arrayList.add(new PieEntry(ttltweet,getApplicationContext().getString(R.string.Total_Tweet)));
                PieDataSet pieDataSet=new PieDataSet(arrayList,"");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pie_feedtype.setData(new PieData(pieDataSet));
                pie_feedtype.animateXY(500,500);
                pie_feedtype.getDescription().setEnabled(false);
                pie_feedtype.setEntryLabelTextSize(0.0f);
                ArrayList<BarEntry> arrmonthwise=new ArrayList<>();
                for (int i = 0; i < monthcnt.length; i++) {
                    arrmonthwise.add(new BarEntry(i+1,monthcnt[i]));
                }
                BarDataSet barDataSet=new BarDataSet(arrmonthwise,"");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                bar_monthwisefeed.setData(new BarData(barDataSet));
                bar_monthwisefeed.animateXY(500,500);
                bar_monthwisefeed.getDescription().setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ttllike=0;
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    ttllike++;
                }
                txtttllike.setText(ttllike+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Feed_Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ttlcmt=0;
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    ttlcmt++;
                }
                txtttlcmt.setText(ttlcmt+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Users_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,Integer> states= new HashMap<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    clsUserModel clsUserModel=datasnapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                    if (states.containsKey(clsUserModel.state)){
                        Integer temp=states.get(clsUserModel.state);
                        states.put(clsUserModel.state,new Integer(++temp));
                    }
                    else{
                        states.put(clsUserModel.state,new Integer(1));
                    }
                }
                ArrayList<PieEntry> arrlststate=new ArrayList<>();
                for (Map.Entry<String,Integer> e:states.entrySet()){
                    arrlststate.add(new PieEntry(e.getValue(),e.getKey()));
                }
                PieDataSet pieDataSet=new PieDataSet(arrlststate,"");
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pie_statewise.setData(new PieData(pieDataSet));
                pie_statewise.animateXY(500,500);
                pie_statewise.getDescription().setEnabled(false);
                pie_statewise.setEntryLabelTextSize(0.0f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Resell").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<PieEntry> arrlresellsplit=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                     snapshot.getChildren()) {
                    arrlresellsplit.add(new PieEntry(datasnapshot.getChildrenCount(),datasnapshot.getKey()));
                }
                PieDataSet pieDataSet=new PieDataSet(arrlresellsplit,"");
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pie_resellsplit.setData(new PieData(pieDataSet));
                pie_resellsplit.animateXY(500,500);
                pie_resellsplit.getDescription().setEnabled(false);
                pie_resellsplit.setEntryLabelTextSize(0.0f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}