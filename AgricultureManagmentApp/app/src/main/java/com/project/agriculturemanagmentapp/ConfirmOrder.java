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
clsOrderModel clsOrderModel;
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
        if(!IsOrder){
            txtcname.setText(sharedPreferences.getString("uname","unkown"));
            txtcmo.setText(sharedPreferences.getString("mo","unkown"));
            txtcadd.setText(sharedPreferences.getString("add","unkown"));
        }
        if (IsOrder){
            imgedit.setVisibility(View.GONE);
            cdpmod.setVisibility(View.GONE);
            btnconfirm.setVisibility(View.GONE);
            FirebaseDatabase.getInstance().getReference("/Orders").child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     clsOrderModel=snapshot.getValue(com.project.agriculturemanagmentapp.clsOrderModel.class);
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
                    txtcgst.setText(clsOrderModel.getClsEcommModel().getCgst().toString());
                    txtsgst.setText(clsOrderModel.getClsEcommModel().getSgst().toString());
                    txtdis.setText(clsOrderModel.getClsEcommModel().getDiscount().toString());
                    txtshpgcharges.setText("120");
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
                    float payable=((price*qty)+((sgst/100)*amt)+((cgst/100)*amt)+120)-((Discount/100)*amt);
                    txtpayableamt.setText(payable+"");
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
    private void generatePDF() {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(1120, 792, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.Dark_green));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("A portal for IT professionals.", 209, 100, title);
        canvas.drawText("Geeks for Geeks", 209, 80, title);

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.Dark_green));
        title.setTextSize(15);

        // below line is used for setting
        // our text to center of PDF.
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(ConfirmOrder.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
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
        String time=hour+":"+Calendar.getInstance().get(Calendar.MINUTE);
        String key = FirebaseDatabase.getInstance().getReference().child("orders").push().getKey();
        clsOrderModel clsOrderModel = new clsOrderModel(clsEcommModel,key,sharedPreferences.getString("uname", "unknown"),sharedPreferences.getString("mo", "1234567890"), sharedPreferences.getString("add", "null"), qty+"", date,time,"COD","","");
        FirebaseDatabase.getInstance().getReference().child("Orders").child(key).setValue(clsOrderModel);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(clsEcommModel.getKey()).removeValue();
        finish();
    }
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
        String time=hour+":"+Calendar.getInstance().get(Calendar.MINUTE);
        String key = FirebaseDatabase.getInstance().getReference().child("orders").push().getKey();
        clsOrderModel clsOrderModel = new clsOrderModel(clsEcommModel,key,sharedPreferences.getString("uname", "unknown"),sharedPreferences.getString("mo", "1234567890"), sharedPreferences.getString("add", "null"), qty+"", date,time,"Online",paymentData.getPaymentId().toString(),"Sucess");
        FirebaseDatabase.getInstance().getReference().child("Orders").child(key).setValue(clsOrderModel);
        FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(clsEcommModel.getKey()).removeValue();
        finish();
        Intent intent=new Intent(ConfirmOrder.this,ConfirmOrder.class);
        intent.putExtra("key",key);
        intent.putExtra("IsOrder",true);
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
