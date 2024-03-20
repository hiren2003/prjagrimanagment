package com.project.agriculturemanagmentapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AI extends AppCompatActivity {
TextInputEditText edtquery;
ImageView submitquery,chooseimage;
Bitmap img=null;
String apikey= "AIzaSyA-kKjqQcNczyyUIQwuLrVQkmbOxjGNMPQ";
RecyclerView rcconv;
RcAiConversationAdapter rcAiConversationAdapter;
ArrayList<clsaiconversation> clsaiconversationArrayList;
Uri uri=null;
ProgressBar prgbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);
        edtquery=findViewById(R.id.edtquery);
        submitquery =findViewById(R.id.btn);
        chooseimage=findViewById(R.id.btn2);
        rcconv=findViewById(R.id.rc);
        prgbar=findViewById(R.id.prgbar);
        rcconv.setLayoutManager(new LinearLayoutManager(this));
        clsaiconversationArrayList=new ArrayList<>();
        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result!=null){
                    try {
                        uri=result;
                        img= MediaStore.Images.Media.getBitmap(AI.this.getContentResolver(), result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        submitquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgbar.setVisibility(View.VISIBLE);
                submitquery.setVisibility(View.GONE);
                GenerativeModel gm;
                GenerativeModelFutures generativeModelFutures;
                Content content;
            if (img==null){
                    gm = new GenerativeModel("gemini-pro",apikey);
                    generativeModelFutures=GenerativeModelFutures.from(gm);
                    content  = new Content.Builder()
                            .addText(edtquery.getText().toString())
                            .build();
                    clsaiconversationArrayList.add(new clsaiconversation(null,edtquery.getText().toString(),false));
                    rcAiConversationAdapter=new RcAiConversationAdapter(clsaiconversationArrayList,AI.this);
                    rcconv.setAdapter(rcAiConversationAdapter);
                    rcconv.getLayoutManager().scrollToPosition(clsaiconversationArrayList.size()-1);
                }
                else{
                     content = new Content.Builder()
                            .addText(edtquery.getText().toString())
                            .addImage(img)
                            .build();
                     gm = new GenerativeModel("gemini-pro-vision",apikey);
                     generativeModelFutures=GenerativeModelFutures.from(gm);
                    clsaiconversationArrayList.add(new clsaiconversation(uri,edtquery.getText().toString(),false));
                    rcAiConversationAdapter=new RcAiConversationAdapter(clsaiconversationArrayList,AI.this);
                    rcconv.setAdapter(rcAiConversationAdapter);
                    rcconv.getLayoutManager().scrollToPosition(clsaiconversationArrayList.size()-1);
                }
                Executor executor = new ThreadPoolExecutor(1, 10,
                        10, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>());
                ListenableFuture<GenerateContentResponse> response = generativeModelFutures.generateContent(content);
                Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                edtquery.setText("");
                                clsaiconversationArrayList.add(new clsaiconversation(null,resultText,true));
                                rcAiConversationAdapter=new RcAiConversationAdapter(clsaiconversationArrayList,AI.this);
                                rcconv.setAdapter(rcAiConversationAdapter);
                                rcconv.getLayoutManager().scrollToPosition(clsaiconversationArrayList.size()-1);
                                prgbar.setVisibility(View.GONE);
                                submitquery.setVisibility(View.VISIBLE);
                                img=null;
                            }
                        });

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                clsaiconversationArrayList.add(new clsaiconversation(null,"Failed to generate response.",true));
                                rcAiConversationAdapter=new RcAiConversationAdapter(clsaiconversationArrayList,AI.this);
                                rcconv.setAdapter(rcAiConversationAdapter);
                                rcconv.getLayoutManager().scrollToPosition(clsaiconversationArrayList.size()-1);
                                prgbar.setVisibility(View.GONE);
                                submitquery.setVisibility(View.VISIBLE);
                                img=null;
                            }
                        });

                    }
                },executor);
            }
        });



    }
}