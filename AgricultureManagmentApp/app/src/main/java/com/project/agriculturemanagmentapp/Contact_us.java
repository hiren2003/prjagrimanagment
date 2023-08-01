package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Contact_us extends AppCompatActivity {
TextInputEditText edtdes;
TextView txtaddress;
ImageButton btnwhatsapp,btncall,btnsms,btnemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        edtdes=findViewById(R.id.edtdes2);
        txtaddress=findViewById(R.id.txtaddress);
        btncall=findViewById(R.id.btncall);
        btnwhatsapp=findViewById(R.id.btnwhatsapp);
        btnsms=findViewById(R.id.btnsms);
        btnemail=findViewById(R.id.btnemail);
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager=SmsManager.getDefault();
                String msg="Sender : "+sharedPreferences.getString("uname","unknown")+"\n Contact me : "+sharedPreferences.getString("mo","1234567890");
                smsManager.sendTextMessage("7229005896",null,msg,null,null);
                finish();
            }
        });
        btnwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mo="+917229005896";
                String msg="Sender : "+sharedPreferences.getString("uname","unknown")+"\n "+edtdes.getText().toString();
                String url = "https://api.whatsapp.com/send?phone="+mo+"&text="+msg;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:7229005896"));
                startActivity(intent);
                Toast.makeText(Contact_us.this, getResources().getString(R.string.msgsent), Toast.LENGTH_SHORT).show();
            }
        });
        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg="Sender : "+sharedPreferences.getString("uname","unknown")+"\n "+edtdes.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{"prjagriculture@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "CustomerSupport"+sharedPreferences.getString("mo","1234567890"));
                email.putExtra(Intent.EXTRA_TEXT, msg);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        txtaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat="21.2356206972365";
                String lng="72.85250131921465";
                String strUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + "Sutex Bank College of Computer Applications and Science." + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
            }
        });
    }
}