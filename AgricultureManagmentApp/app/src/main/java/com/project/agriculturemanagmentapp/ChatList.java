package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

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
        FirebaseDatabase.getInstance().getReference().child("User").child(umo.toString()).child("RecentChats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> arrayList=new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (!arrayList.contains(dataSnapshot.getValue().toString())){
                        arrayList.add(dataSnapshot.getValue().toString());
                        System.out.println(dataSnapshot.getValue().toString());
                    }
                }
                userModelArrayList=new ArrayList<>();
                for (String str:
                        arrayList) {
                    FirebaseDatabase.getInstance().getReference().child("Users_List").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            userModelArrayList.add(snapshot.getValue(clsUserModel.class));
                            rcuserAdapter = new RcuserAdapter( getBaseContext(),userModelArrayList,false,true);
                            rcchatlist.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                            rcchatlist.setAdapter(rcuserAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

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
                if (filteredlist.isEmpty()){
                    Toast.makeText(ChatList.this, "User Not Exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    rcuserAdapter = new RcuserAdapter( getBaseContext(),filteredlist,false,true);
                    rcchatlist.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    rcchatlist.setAdapter(rcuserAdapter);                }
                return false;
            }
        });
    }
}