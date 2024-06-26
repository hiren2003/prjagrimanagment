package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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

public class Explore_User extends AppCompatActivity {
    RecyclerView rcuser;
    RcuserAdapter rcuserAdapter;
    androidx.appcompat.widget.SearchView searchView;
    ArrayList<clsUserModel> userModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_user);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        rcuser = findViewById(R.id.rcusers);
        searchView=findViewById(R.id.search);
        userModelArrayList=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Users_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModelArrayList=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    userModelArrayList.add(dataSnapshot.getValue(clsUserModel.class));
                }
                LottieAnimationView loty=findViewById(R.id.loty3);
                if (userModelArrayList.isEmpty()){
                    loty.setVisibility(View.VISIBLE);
                }
                else{
                    loty.setVisibility(View.GONE);
                    rcuserAdapter = new RcuserAdapter( getBaseContext(),userModelArrayList,false,false);
                    rcuser.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    rcuser.setAdapter(rcuserAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(Explore_User.this, query, Toast.LENGTH_SHORT).show();

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
                if (filteredlist.isEmpty()){
                    loty.setVisibility(View.VISIBLE);
                }
                else{
                    loty.setVisibility(View.GONE);
                    rcuserAdapter = new RcuserAdapter( getBaseContext(),filteredlist,false,false);
                    rcuser.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                    rcuser.setAdapter(rcuserAdapter);
                }


                return false;
            }
        });

    }
}