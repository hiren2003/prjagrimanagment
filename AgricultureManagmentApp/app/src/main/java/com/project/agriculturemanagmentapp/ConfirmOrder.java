package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ConfirmOrder extends AppCompatActivity implements PaymentResultWithDataListener {
TextView txtpname,txtprc,txtqty,txtdes,txtcname,txtcmo,txtcadd,txtamount,txtcgst,txtsgst,txtshpgcharges,txtdis,txtpayableamt,txtoid,txtpmod,txtpstatus,txtpid;
ImageView imgprdt,imgedit,down;
RadioButton rbcod,rbonline;
AppCompatButton btnconfirm;
SharedPreferences sharedPreferences;
String key;
float qty;
float price;
clsEcommModel clsEcommModel;
CardView cdprdt,cdpmod,cdpdetail;
LinearLayout lnpoption;
boolean IsOrder=false;
boolean isAdmin=false;
    boolean isCancelled=false;
clsOrderModel clsOrderModel;
float payable;
String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        txtpname=findViewById(R.id.txtpname);
        txtprc=findViewById(R.id.txtprc);
        txtqty=findViewById(R.id.txtqty);
        txtdes=findViewById(R.id.txtdes);
        txtcname=findViewById(R.id.txtcname);
        down= findViewById(R.id.back);
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
        cdprdt=findViewById(R.id.cdprdt);
        cdpmod=findViewById(R.id.cdpmod);
        cdpdetail=findViewById(R.id.cdpdetail);
        lnpoption=findViewById(R.id.lnpoption);
        txtoid=findViewById(R.id.txtoid);
        txtpmod=findViewById(R.id.txtpmod);
        txtpstatus=findViewById(R.id.txtpstatus);
        txtpid=findViewById(R.id.txtpid);
        int month=Calendar.getInstance().get(java.util.Calendar.MONTH);
        month++;
        date = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) + "-" + month + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        Checkout.preload(getApplicationContext());
        Intent intent=getIntent();
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        key=intent.getStringExtra("key");
        IsOrder=intent.getBooleanExtra("IsOrder",false);
        isAdmin=intent.getBooleanExtra("IsAdmin",false);
        isCancelled=intent.getBooleanExtra("isCancelled",false);
        if(!IsOrder){
            txtcname.setText(sharedPreferences.getString("uname","unkown"));
            txtcmo.setText(sharedPreferences.getString("mo","unkown"));
            txtcadd.setText(sharedPreferences.getString("add","unkown"));
        }
        if (IsOrder){
            imgedit.setVisibility(View.GONE);
            cdpmod.setVisibility(View.GONE);
            btnconfirm.setVisibility(View.GONE);
            String path="";
            if (isCancelled)
            {
                 path="/Cancelled_order";
            }
            else{
                 path="/Orders";
            }
            FirebaseDatabase.getInstance().getReference(path).child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     clsOrderModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsOrderModel.class);
                     if (clsOrderModel!=null){
                         if (clsOrderModel.getPaymentMode().equals("COD")){
                             lnpoption.setVisibility(View.GONE);
                         }
                         Glide.with(ConfirmOrder.this)
                                 .load(clsOrderModel.getClsEcommModel().getImg())
                                 .into(imgprdt);
                         price=Float.parseFloat(clsOrderModel.getClsEcommModel().getPrice());
                         txtprc.setText(clsOrderModel.getClsEcommModel().getPrice());
                         txtpname.setText(clsOrderModel.getClsEcommModel().getPname());
                         txtdes.setText(clsOrderModel.getClsEcommModel().getDes());
                         txtqty.setText(getString(R.string.qty)+" : "+clsOrderModel.getQty());
                         qty=Float.parseFloat(clsOrderModel.getQty());
                         price=Float.parseFloat(clsOrderModel.getClsEcommModel().getPrice());
                         txtamount.setText(""+(price*qty));
                         txtoid.setText(clsOrderModel.getKey());
                         txtpid.setText(clsOrderModel.getPaymentId());
                         txtpmod.setText(clsOrderModel.getPaymentMode());
                         txtpstatus.setText(clsOrderModel.getPaymentStatus());
                         txtcname.setText(clsOrderModel.getName());
                         txtcmo.setText(clsOrderModel.getMo());
                         txtcadd.setText(clsOrderModel.getAddress());
                         float sgst=Float.parseFloat(clsOrderModel.getClsEcommModel().getSgst().toString());
                         float cgst=Float.parseFloat(clsOrderModel.getClsEcommModel().getCgst().toString());
                         float Discount=Float.parseFloat(clsOrderModel.getClsEcommModel().getDiscount().toString());
                         float amt=price*qty;
                         float s=(sgst/100)*amt;
                         float c=(cgst/100)*amt;
                         float d=(Discount/100)*amt;
                         txtcgst.setText(c+"");
                         txtsgst.setText(s+"");
                         txtdis.setText(d+"");
                         txtshpgcharges.setText("120");
                         payable=((price*qty)+(s)+(c)+120)-(d);
                         System.out.println("-----------"+s+"-----------"+c+"----------"+d);
                         txtpayableamt.setText(payable+"");
                         AppCompatButton  btncancelorder=findViewById(R.id.btnremoveorder);
                         long time=86400+clsOrderModel.getTime();
                         if (Instant.now().getEpochSecond()<time&&isAdmin==false){
                             btncancelorder.setVisibility(View.VISIBLE);
                         }
                         else {
                             btncancelorder.setVisibility(View.GONE);
                         }
                         btncancelorder.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 FirebaseDatabase.getInstance().getReference().child("Cancelled_order").child(clsOrderModel.getKey()).setValue(clsOrderModel);
                                 FirebaseDatabase.getInstance().getReference().child("Orders").child(clsOrderModel.getKey()).removeValue();
                                 finish();
                             }
                         });
                     }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            cdpdetail.setVisibility(View.GONE);
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
                    txtshpgcharges.setText("120");
                    float sgst=Float.parseFloat(clsEcommModel.getSgst().toString());
                    float cgst=Float.parseFloat(clsEcommModel.getCgst().toString());
                    float Discount=Float.parseFloat(clsEcommModel.getDiscount().toString());
                    float amt=price*qty;
                    txtcgst.setText((amt*cgst)/100+"");
                    txtsgst.setText((sgst/100)*amt+"");
                    txtdis.setText((Discount/100)*amt+"");
                    float s=(sgst/100)*amt;
                    float c=(cgst/100)*amt;
                    float d=(Discount/100)*amt;
                    txtcgst.setText(c+"");
                    txtsgst.setText(s+"");
                    txtdis.setText(d+"");
                    payable=((price*qty)+(s)+(c)+120)-(d);
                    System.out.println("-----------"+s+"-----------"+c+"----------"+d);
                    txtpayableamt.setText(payable+"");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cdprdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsOrder){
                    startActivity(new Intent(ConfirmOrder.this, show_ecom_prdt.class).putExtra("key",clsOrderModel.clsEcommModel.getKey()));
                }
                else{
                    startActivity(new Intent(ConfirmOrder.this, show_ecom_prdt.class).putExtra("key",key));
                }
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
                      Toast.makeText(ConfirmOrder.this, "Done", Toast.LENGTH_SHORT).show();
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
                          options.put("amount",payable*100 );//pass amount in currency subunits
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 301) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        txtcname.setText(sharedPreferences.getString("uname","unkown"));
        txtcmo.setText(sharedPreferences.getString("mo","unkown"));
        txtcadd.setText(sharedPreferences.getString("add","unkown"));
    }

    public void AddOrder(){
        int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        hour++;
        long time= Instant.now().getEpochSecond();
        String key = FirebaseDatabase.getInstance().getReference().child("orders").push().getKey();
        clsOrderModel clsOrderModel = new clsOrderModel(clsEcommModel,key,sharedPreferences.getString("uname", "unknown"),sharedPreferences.getString("mo", "1234567890"), sharedPreferences.getString("add", "null"), qty+"", date,time,"COD","","");
        FirebaseDatabase.getInstance().getReference().child("Orders").child(key).setValue(clsOrderModel);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(clsEcommModel.getKey()).removeValue();
        Intent intent=new Intent(ConfirmOrder.this,MyOrder.class);
        startActivity(intent);    }
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_DENIED && permission2 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},301);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        hour++;
        long time= Instant.now().getEpochSecond();
        String key = FirebaseDatabase.getInstance().getReference().child("orders").push().getKey();
        clsOrderModel clsOrderModel = new clsOrderModel(clsEcommModel,key,sharedPreferences.getString("uname", "unknown"),sharedPreferences.getString("mo", "1234567890"), sharedPreferences.getString("add", "null"), qty+"", date,time,"Online",paymentData.getPaymentId().toString(),"Sucess");
        FirebaseDatabase.getInstance().getReference().child("Orders").child(key).setValue(clsOrderModel);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(clsEcommModel.getKey()).removeValue();
        Intent intent=new Intent(ConfirmOrder.this,MyOrder.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        if (i==Checkout.NETWORK_ERROR){
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        } else if (i==Checkout.INVALID_OPTIONS) {
            Toast.makeText(this, "Invalid Options", Toast.LENGTH_SHORT).show();
        }
        else if (i==Checkout.PAYMENT_CANCELED) {
            Toast.makeText(this, "Payment cancelled by User", Toast.LENGTH_SHORT).show();
        }
        else {
                Toast.makeText(this, "Payment Unsucessfull", Toast.LENGTH_SHORT).show();
            }
        }
    }
