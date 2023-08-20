package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseArray;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class show_CultivationProduct extends AppCompatActivity {
    ImageButton btnwhatsapp, btncall;
    TextView txtcategory, txtpname, txtspecie, txtqty, txtprc, txtpayment, txtstate, txttehsil, txtdistrict, txtvillage, txtdes, txtuname, txtdate;
    String key;
    ImageView imgprdt, prfpc;
    ClsCultivationProductModel clsCultivationProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cultivation_product);
        btnwhatsapp = findViewById(R.id.btnwhatsapp);
        btncall = findViewById(R.id.btncall);
        txtcategory = findViewById(R.id.txtpcategory);
        txtpname = findViewById(R.id.txtpname);
        txtspecie = findViewById(R.id.txtspeice);
        txtqty = findViewById(R.id.txtpqty);
        txtprc = findViewById(R.id.txtprc);
        txtpayment = findViewById(R.id.txtpayable);
        imgprdt = findViewById(R.id.imgprdt);
        txtuname = findViewById(R.id.txtuname);
        txtdate = findViewById(R.id.txtdate);
        txtstate = findViewById(R.id.txtstate);
        txttehsil = findViewById(R.id.txttehsil);
        txtdistrict = findViewById(R.id.txtdistrict);
        txtvillage = findViewById(R.id.txtVillage);
        txtdes = findViewById(R.id.txtdes);
        prfpc = findViewById(R.id.profilepc);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        btnwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo = "+91" + clsCultivationProductModel.getMo();
                String msg = "Hello " + clsCultivationProductModel.getUname() + "," + getResources().getString(R.string.Interest2) + " " + clsCultivationProductModel.getPname();
                String url = "https://api.whatsapp.com/send?phone=" + mo + "&text=" + msg;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo = "+91" + clsCultivationProductModel.getMo();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mo));
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cultivation Product").child(key);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsCultivationProductModel = snapshot.getValue(ClsCultivationProductModel.class);
                Glide.with(show_CultivationProduct.this)
                        .load(clsCultivationProductModel.getImg())
                        .into(imgprdt);
                Glide.with(show_CultivationProduct.this)
                        .load(clsCultivationProductModel.getPrfpc())
                        .circleCrop()
                        .into(prfpc);
                txtcategory.setText(clsCultivationProductModel.getCategory());
                txtpname.setText(clsCultivationProductModel.getPname());
                txtspecie.setText(clsCultivationProductModel.getSpecie());
                txtqty.setText(clsCultivationProductModel.getQty());
                txtprc.setText(clsCultivationProductModel.getPrice());
                txtpayment.setText(clsCultivationProductModel.getPayment());
                txtstate.setText(clsCultivationProductModel.getState());
                txttehsil.setText(clsCultivationProductModel.getTehsil());
                txtdistrict.setText(clsCultivationProductModel.getDistrict());
                txtvillage.setText(clsCultivationProductModel.getVillage());
                txtdes.setText(clsCultivationProductModel.getDes());
                txtuname.setText(clsCultivationProductModel.getUname());
                txtdate.setText(clsCultivationProductModel.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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