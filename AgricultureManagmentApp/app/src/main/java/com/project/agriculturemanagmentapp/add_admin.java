package com.project.agriculturemanagmentapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class add_admin extends AppCompatActivity {
RecyclerView rcadmin;
AppCompatButton btnadd;
TextInputEditText mo,name,password;
    Map<String,String> map;
    ArrayList<String> adminlist;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rcadmin=findViewById(R.id.rcadmin);
        btnadd=findViewById(R.id.btnadd);
        mo=findViewById(R.id.mo);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        String currentmo=sharedPreferences.getString("mo","");
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().isEmpty()){
                    Toast.makeText(add_admin.this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else if (mo.getText().toString().trim().isEmpty()) {
                    Toast.makeText(add_admin.this, "Enter MO", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(add_admin.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (!map.get(currentmo).equals(password.getText().toString().trim())){
                    Toast.makeText(add_admin.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                } else if (adminlist.contains(mo.getText().toString())) {
                    Toast.makeText(add_admin.this, mo.getText().toString()+" is Already admin", Toast.LENGTH_SHORT).show();
                }
                else{
                    long passint=new Random().nextInt(10000+1-1000)+1000;
                    String Finalpass=name.getText().toString()+"@"+mo.getText().toString()+"#"+passint;
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+91" + mo.getText().toString(), null, "Hello,\n"+name.getText().toString()+" you are the admin of Agrocare.\nUsername : "+mo.getText().toString().trim()+"\nPassword : "+Finalpass, null, null);
                    FirebaseDatabase.getInstance().getReference("/Admin").child(mo.getText().toString().trim()).setValue(Finalpass);
                    for (String str:
                         adminlist) {
                        smsManager.sendTextMessage("+91" +str, null, "New admin "+mo.getText().toString().trim()+" have added by "+currentmo, null, null);
                    }
                }
            }
        });
        FirebaseDatabase.getInstance().getReference("/Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                map=new HashMap<>();
                adminlist=new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    map.put(datasnapshot.getKey().toString(),datasnapshot.getValue().toString());
                    adminlist.add(datasnapshot.getKey().toString());
                }
                FirebaseDatabase.getInstance().getReference().child("Users_List").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<clsUserModel> userModelArrayList=new ArrayList<>();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            clsUserModel model=dataSnapshot.getValue(clsUserModel.class);
                            if (adminlist.contains(model.getMo().toString())){
                                userModelArrayList.add(model);
                            }
                        }

                        RcuserAdapter rcuserAdapter = new RcuserAdapter( getBaseContext(),userModelArrayList,true,false);
                        rcadmin.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                        rcadmin.setAdapter(rcuserAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}