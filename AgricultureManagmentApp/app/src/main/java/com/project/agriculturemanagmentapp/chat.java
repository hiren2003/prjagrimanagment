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
import android.widget.ImageView;
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
import java.util.Calendar;

public class chat extends AppCompatActivity {
String smo,rmo,surl,rurl,sname,rname,date,time;
RecyclerView rcchat;
TextInputEditText edtmsg;
ImageView send,prfpc,chooseimage;
TextView txtrname;
    Calendar calendar;
    Uri uri=null;
    RcChatAdapter rcChatAdapter;

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
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        Intent intent = getIntent();
        //rmo=intent.getStringExtra("rmo");
        smo="6353007116";
       // smo=sharedPreferences.getString("mo","unknown");
        rmo = "7229005896";
        surl=sharedPreferences.getString("url","none");
        sname = sharedPreferences.getString("uname","none");
         calendar = Calendar.getInstance();
        date=calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR);
        rcchat.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<clsChatModel> options= new FirebaseRecyclerOptions.Builder<clsChatModel>()
                .setQuery( FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()), clsChatModel.class)
                .build();
        rcChatAdapter=new RcChatAdapter(options,this);
        rcchat.setAdapter(rcChatAdapter);
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
                time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
                String key=FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).push().getKey().toString();
                if(uri==null){
                    clsChatModel model = new clsChatModel(edtmsg.getText().toString(),smo,rmo,date,time,key,"");
                    FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).child(key).setValue(model);
                    FirebaseDatabase.getInstance().getReference().child("User").child(rmo.toString()).child("Chats").child(smo.toString()).child(key).setValue(model);

                }
                else{
                   StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Chats").child(smo.toString()).child(key);
                   storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   clsChatModel model = new clsChatModel(edtmsg.getText().toString(),smo,rmo,date,time,key,uri.toString());
                                   FirebaseDatabase.getInstance().getReference().child("User").child(smo.toString()).child("Chats").child(rmo.toString()).child(key).setValue(model);
                                   FirebaseDatabase.getInstance().getReference().child("User").child(rmo.toString()).child("Chats").child(smo.toString()).child(key).setValue(model);
                               }
                           });
                       }
                   });
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        rcChatAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rcChatAdapter.startListening();
    }
}