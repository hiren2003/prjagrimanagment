package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.util.Calendar;

public class show_ecom_prdt extends AppCompatActivity implements PaymentResultWithDataListener {
    String key;
    ImageView imageView;
    TextView txtpname, txtprice, txtspec, txtdes, txtrecom,txtkey,txtsgst,txtcgst,txtdiscount;
    ImageButton btncart, btnrmcart, btnorder, btncancelorder,rmprdt;
    clsEcommModel model;
    TextInputLayout txtqty2;
    TextInputEditText edtqty;
    int btn = 0;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ecom_prdt);
        imageView = findViewById(R.id.imgprdt);
        txtpname = findViewById(R.id.txtpname);
        txtprice = findViewById(R.id.txtprice);
        txtspec = findViewById(R.id.txtspec);
        txtdes = findViewById(R.id.txtdec);
        btncart = findViewById(R.id.btncart);
        edtqty = findViewById(R.id.edtqty2);
        txtrecom = findViewById(R.id.txtrecom);
        btnrmcart = findViewById(R.id.btnremovecart);
        btnorder = findViewById(R.id.btnorder);
        txtkey=findViewById(R.id.txtkey);
        rmprdt=findViewById(R.id.dprdt);
        txtqty2=findViewById(R.id.txtqty2);
        txtcgst=findViewById(R.id.txtcgst);
        txtsgst=findViewById(R.id.txtsgst);
        txtdiscount=findViewById(R.id.txtdiscount);
        btncancelorder = findViewById(R.id.btncancelorder);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        btn = intent.getIntExtra("btn", 0);
        if (btn == 1) {
            btncart.setVisibility(View.VISIBLE);
        } else if (btn == 2) {
            btnorder.setVisibility(View.VISIBLE);
            edtqty.setVisibility(View.VISIBLE);
            txtqty2.setVisibility(View.VISIBLE);
            btnrmcart.setVisibility(View.VISIBLE);
        } else if (btn == 3) {
            btncancelorder.setVisibility(View.VISIBLE);
            edtqty.setInputType(InputType.TYPE_NULL);
            edtqty.setVisibility(View.VISIBLE);
            txtqty2.setVisibility(View.VISIBLE);
        }
        reference = FirebaseDatabase.getInstance().getReference().child("ECommerce").child("All").child(key);
        System.out.println("---------------------------------------"+key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(clsEcommModel.class);
                Glide.with(getApplicationContext())
                        .load(model.getImg())
                        .into(imageView);
                txtpname.setText(model.getPname());
                txtprice.setText("₹" + model.getPrice());
                txtdes.setText(model.getDes());
                txtspec.setText(model.getDpec());
                txtrecom.setText(model.getRecomm());
                edtqty.setText(model.getQty());
                txtkey.setText(model.getKey());
                txtcgst.setText(model.getCgst()+"%");
                txtsgst.setText(model.getSgst()+"%");
                txtdiscount.setText(model.getDiscount()+"%sh");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.qty = edtqty.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(model.getKey()).setValue(model);
                show_toast(getResources().getString(R.string.Added_cart),true);
                finish();
            }
        });
        btnrmcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(model.getKey()).removeValue();
                show_toast(getResources().getString(R.string.remove_cart),true);
                finish();
            }
        });
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtqty.getText().toString().trim().isEmpty()){
                    show_toast(getResources().getString(R.string.Invalid_Quntity),false);
                } else if (edtqty.getText().toString().trim().equals("0")) {
                    show_toast(getResources().getString(R.string.Invalid_Quntity),false);
                }
                else{
                    model.qty = edtqty.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    boolean hasadd = sharedPreferences.getBoolean("hasadd", false);
                    if (hasadd) {
                        Intent intent=new Intent(show_ecom_prdt.this, ConfirmOrder.class);
                        intent.putExtra("key",model.getKey());
                        intent.putExtra("qty",edtqty.getText().toString());
                        startActivity(intent);

                    //    String date = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) + "-" + Calendar.getInstance().get(java.util.Calendar.MONTH) + "-" + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                      //  String key = FirebaseDatabase.getInstance().getReference().child("orders").child(date.toString()).push().getKey();
                        //clsOrderModel clsOrderModel = new clsOrderModel(model, model.getKey(),sharedPreferences.getString("mo", "1234567890"), sharedPreferences.getString("add", "null"), edtqty.getText().toString(), date);
                       // FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Myorder").child(key).setValue(clsOrderModel);
                        //FirebaseDatabase.getInstance().getReference().child("Orders").child(date).child(key).setValue(clsOrderModel);
                       // FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Cart").child(model.getKey()).removeValue();
                        //show_toast(getResources().getString(R.string.Confirm_order),true);
                        //finish();
                    } else {
                        show_toast(getResources().getString(R.string.Enter_address), false);
                        startActivity(new Intent(getBaseContext(), EditprofileActivity.class));
                    }
                }

            }
        });
        btncancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);
                String date = day + "-" + month + "-" + year;
                FirebaseDatabase.getInstance().getReference().child("User").child(sharedPreferences.getString("mo", "1234567890")).child("Myorder").child(model.getKey()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Orders").child(date.toString()).child(model.getKey()).removeValue();
                finish();
            }
        });
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
    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}