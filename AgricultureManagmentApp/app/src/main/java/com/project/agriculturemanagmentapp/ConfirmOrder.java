package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.Calendar;

public class ConfirmOrder extends AppCompatActivity implements PaymentResultWithDataListener {
TextView txtpname,txtprc,txtqty,txtdes,txtcname,txtcmo,txtcadd,txtamount,txtcgst,txtsgst,txtshpgcharges,txtdis,txtpayableamt;
ImageView imgprdt,imgedit;
RadioButton rbcod,rbonline;
AppCompatButton btnconfirm;
SharedPreferences sharedPreferences;
String key;
float qty;
float price;
clsEcommModel clsEcommModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        txtpname=findViewById(R.id.txtpname);
        txtprc=findViewById(R.id.txtprc);
        txtqty=findViewById(R.id.txtqty);
        txtdes=findViewById(R.id.txtdes);
        txtcname=findViewById(R.id.txtcname);
        txtcmo=findViewById(R.id.txtcmo);
        txtcadd=findViewById(R.id.txtcadd);
        txtamount=findViewById(R.id.txtamount);
        txtcgst=findViewById(R.id.txtcgst);
        txtsgst=findViewById(R.id.txtsgst);
        txtshpgcharges=findViewById(R.id.txtshpgcharges);
        txtdis=findViewById(R.id.txtdis);
        txtpayableamt=findViewById(R.id.txtpayableamt);
        imgprdt=findViewById(R.id.imgprdt);
        imgedit=findViewById(R.id.imgedit);
        rbcod=findViewById(R.id.rbcod);
        rbonline=findViewById(R.id.rbonline);
        btnconfirm=findViewById(R.id.btnconfirm);
        Checkout.preload(getApplicationContext());
        Intent intent=getIntent();
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        txtcname.setText(sharedPreferences.getString("uname","unkown"));
        txtcmo.setText(sharedPreferences.getString("mo","unkown"));
        txtcadd.setText(sharedPreferences.getString("add","unkown"));
        key=intent.getStringExtra("key");
        qty=Float.parseFloat(intent.getStringExtra("qty"));
        FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 clsEcommModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsEcommModel.class);
                Glide.with(ConfirmOrder.this)
                                .load(clsEcommModel.getImg())
                                        .into(imgprdt);
                price=Float.parseFloat(clsEcommModel.getPrice());
                txtprc.setText(clsEcommModel.getPrice());
                txtpname.setText(clsEcommModel.getPname());
                txtdes.setText(clsEcommModel.getDes());
                txtqty.setText(getString(R.string.qty)+" : "+qty);
                txtamount.setText(""+(price*qty));
                txtcgst.setText(clsEcommModel.getCgst().toString());
                txtsgst.setText(clsEcommModel.getSgst().toString());
                txtdis.setText(clsEcommModel.getDiscount().toString());
                txtshpgcharges.setText("120");
                float sgst=Float.parseFloat(clsEcommModel.getSgst().toString());
                float cgst=Float.parseFloat(clsEcommModel.getCgst().toString());
                float Discount=Float.parseFloat(clsEcommModel.getDiscount().toString());
                float amt=price*qty;
                float payable=((price*qty)+((sgst/100)*amt)+((cgst/100)*amt)+120)-((Discount/100)*amt);
                txtpayableamt.setText(payable+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmOrder.this,EditprofileActivity.class));
            }
        });
        rbcod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbonline.setChecked(false);
                }
            }
        });
        rbonline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbcod.setChecked(false);
                }
            }
        });
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbcod.isChecked()){
                    AddOrder();
                }
                else{
                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_test_UHPUiPFiPwwVZt");
                    checkout.setImage(R.drawable.logo);
                    final Activity activity = ConfirmOrder.this;
                    try {
                        JSONObject options = new JSONObject();
                        options.put("name", "AgroCare");
                        options.put("description", clsEcommModel.getKey().toString());
                        options.put("image", R.drawable.logo2);
                        options.put("theme.color", "#3399cc");
                        options.put("currency", "INR");
                        options.put("amount",(qty*price)*100 );//pass amount in currency subunits
                        options.put("prefill.contact",sharedPreferences.getString("mo", "1234567890"));
                        JSONObject retryObj = new JSONObject();
                        retryObj.put("enabled", true);
                        retryObj.put("max_count", 4);
                        options.put("retry", retryObj);
                        checkout.open(activity, options);
                    } catch(Exception e) {
                        Toast.makeText(activity, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        txtcname.setText(sharedPreferences.getString("uname","unkown"));
        txtcmo.setText(sharedPreferences.getString("mo","unkown"));
        txtcadd.setText(sharedPreferences.getString("add","unkown"));
    }

    public void AddOrder(){
        String date = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) + "-" + Calendar.getInstance().get(java.util.Calendar.MONTH) + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        String key = FirebaseDatabase.getInstance().getReference().child("orders").child(date.toString()).push().getKey();
        clsOrderModel clsOrderModel = new clsOrderModel(clsEcommModel, clsEcommModel.getKey(),sharedPreferences.getString("mo", "1234567890"), sharedPreferences.getString("add", "null"), qty+"", date);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Myorder").child(key).setValue(clsOrderModel);
        FirebaseDatabase.getInstance().getReference().child("Orders").child(date).child(key).setValue(clsOrderModel);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(clsEcommModel.getKey()).removeValue();
        finish();
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        AddOrder();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}