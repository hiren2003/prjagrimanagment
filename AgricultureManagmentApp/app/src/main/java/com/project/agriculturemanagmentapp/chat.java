package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.ai.client.generativeai.Chat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

public class chat extends AppCompatActivity {
    String smo,rmo,surl,rurl,sname,rname,date,time;
    RecyclerView rcchat;
    LinearLayout rvprofile;
    TextInputEditText edtmsg;
    ImageView send,prfpc,chooseimage;
    ArrayList<clsChatModel> chatModelArrayList;
    TextView txtrname;
    Calendar calendar;
    Uri uri=null;
    RcChatAdapter rcChatAdapter;
    ProgressBar prgbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rcchat=findViewById(R.id.rcchat);
        edtmsg=findViewById(R.id.edtmsg);
        send=findViewById(R.id.btnsendmsg);
        prfpc=findViewById(R.id.profilepc);
        txtrname=findViewById(R.id.txtuname);
        chooseimage=findViewById(R.id.btn2);
        prgbar=findViewById(R.id.prgbar);
        rvprofile=findViewById(R.id.rvprofile);
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.chat));
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        Intent intent = getIntent();
        rmo=intent.getStringExtra("rmo");
        // smo="6353007116";
        smo=sharedPreferences.getString("mo","unknown");
        //rmo = "7229005896";
        surl=sharedPreferences.getString("url","none");
        sname = sharedPreferences.getString("uname","none");
        calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        month++;
        date=calendar.get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+calendar.get(Calendar.YEAR);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(chat.this);
        rcchat.setLayoutManager(linearLayoutManager);
        rvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chat.this,MyProfile.class);
                intent.putExtra("mo",rmo) ;
                startActivity(intent);
            }
        });
        FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModelArrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot:
                        snapshot.getChildren()) {
                    chatModelArrayList.add(dataSnapshot.getValue(clsChatModel.class));
                }
                rcChatAdapter=new RcChatAdapter(chat.this,chatModelArrayList);
                rcchat.setAdapter(rcChatAdapter);
                rcchat.getLayoutManager().scrollToPosition(chatModelArrayList.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users_List").child(rmo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clsUserModel clsUserModel = snapshot.getValue(com.project.agriculturemanagmentapp.clsUserModel.class);
                rname=clsUserModel.getUname();
                rurl=clsUserModel.getUrl();
                Glide.with(chat.this)
                        .load(rurl)
                        .circleCrop()
                        .into(prfpc);
                txtrname.setText(rname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null){
                    uri=result;
                }
            }
        });
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtmsg.getText().toString().trim().isEmpty()&&uri==null){
                    Toast.makeText(chat.this, getString(R.string.Invalid_msg), Toast.LENGTH_SHORT).show();
                }
                else{
                    prgbar.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                    String hour=calendar.get(Calendar.HOUR_OF_DAY)+"";
                    String minute=calendar.get(Calendar.MINUTE)+"";
                    if(minute.length()<2){
                        minute+="0";
                        if(hour.length()<2) {
                            hour+="0";
                        }
                    }
                    if(hour.length()<2){
                        hour+="0";
                        if(minute.length()<2) {
                            minute+="0";
                        }
                    }
                    time=hour+":"+minute;
                    String key=FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).push().getKey().toString();
                    if(uri==null){
                        clsChatModel model = new clsChatModel(edtmsg.getText().toString(),smo,rmo,date,time,key,"");
                        FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference().child("User").child(rmo.toString()).child("Chats").child(smo.toString()).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        prgbar.setVisibility(View.GONE);
                                        send.setVisibility(View.VISIBLE);
                                        edtmsg.setText("");
                                        FirebaseDatabase.getInstance().getReference().child("User").child(rmo.toString()).child("RecentChats").child(Instant.now().getEpochSecond()+"").setValue(smo.toString());
                                        FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("RecentChats").child(Instant.now().getEpochSecond()+"").setValue(rmo.toString());
                                    }
                                });
                            }
                        });

                    }
                    else{
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Chats").child(smo.toString()).child(key);
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri2) {
                                        clsChatModel model = new clsChatModel(edtmsg.getText().toString(),smo,rmo,date,time,key,uri2.toString());
                                        FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                FirebaseDatabase.getInstance().getReference().child("User").child(rmo.toString()).child("Chats").child(smo.toString()).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        prgbar.setVisibility(View.GONE);
                                                        send.setVisibility(View.VISIBLE);
                                                        edtmsg.setText("");
                                                        uri=null;
                                                        FirebaseDatabase.getInstance().getReference().child("User").child(rmo.toString()).child("RecentChats").child(Instant.now().getEpochSecond()+"").setValue(smo.toString());
                                                        FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("RecentChats").child(Instant.now().getEpochSecond()+"").setValue(rmo.toString());
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }

            }
        });

    }


}