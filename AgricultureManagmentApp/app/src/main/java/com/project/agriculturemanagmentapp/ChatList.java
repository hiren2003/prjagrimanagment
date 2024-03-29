package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatList extends AppCompatActivity {
    RecyclerView rcchatlist;
    RcuserAdapter rcuserAdapter;
    SharedPreferences sharedPreferences;
    androidx.appcompat.widget.SearchView searchView;
    ArrayList<clsUserModel> userModelArrayList;

    String umo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        rcchatlist=findViewById(R.id.rcchatlist);
        searchView=findViewById(R.id.search);
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        umo=sharedPreferences.getString("mo","");
        FirebaseDatabase.getInstance().getReference("/User/"+umo+"/Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> arrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        arrayList.add(dataSnapshot.getKey().toString());
                        System.out.println(dataSnapshot.getKey().toString());
                }
                FirebaseDatabase.getInstance().getReference("/Users_List").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userModelArrayList=new ArrayList<>();
                        for (DataSnapshot datasnapshot:
                             snapshot.getChildren()) {
                            clsUserModel model=datasnapshot.getValue(clsUserModel.class);
                            if (arrayList.contains(model.getMo())){
                                userModelArrayList.add(model);
                            }
                        }
                        LottieAnimationView loty=findViewById(R.id.loty3);
                        if (userModelArrayList.isEmpty()){
                            loty.setVisibility(View.VISIBLE);
                        }
                        else{
                            loty.setVisibility(View.GONE);
                            rcchatlist.setLayoutManager(new LinearLayoutManager(ChatList.this));
                            RcuserAdapter rcuserAdapter1=new RcuserAdapter(ChatList.this,userModelArrayList,false,true);
                            rcchatlist.setAdapter(rcuserAdapter1);
                        }

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<clsUserModel> filteredlist = new ArrayList<>();
                for (clsUserModel model:
                        userModelArrayList) {
                    if(model.getMo().trim().toLowerCase().contains(newText)||model.getUname().toLowerCase().contains(newText)||model.getUname().toUpperCase().contains(newText)){
                        filteredlist.add(model);
                    }
                }
                LottieAnimationView loty=findViewById(R.id.loty3);
                if (userModelArrayList.isEmpty()){
                    loty.setVisibility(View.VISIBLE);
                }
                else{
                    loty.setVisibility(View.GONE);
                    rcuserAdapter = new RcuserAdapter( getBaseContext(),filteredlist,false,true);
                    rcchatlist.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    rcchatlist.setAdapter(rcuserAdapter);                }
                return false;
            }
        });
    }
}