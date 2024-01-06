package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class orderbydate extends AppCompatActivity {
RcorderAdapter rcorderAdapter;
RecyclerView recyclerView;
RelativeLayout rldate;
TextView txtdate;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderbydate);
        recyclerView=findViewById(R.id.rccprdt);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        rldate=findViewById(R.id.rldate);
        txtdate=findViewById(R.id.txtdate);
        date = day + "-" + (++month) + "-" + year;
        FirebaseDatabase.getInstance().getReference().child("Orders").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    orderModelArrayList.add(datasnapshot.getValue(clsOrderModel.class));
                }
                    rcorderAdapter=new RcorderAdapter(orderbydate.this,orderModelArrayList);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(rcorderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txtdate.setText(date);
        rldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(orderbydate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth + "-" + (++month) + "-" + year;
                        txtdate.setText(dayOfMonth + "/" + (++month) + "/" + year);
                        FirebaseDatabase.getInstance().getReference().child("Orders").child(date).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
                                for (DataSnapshot datasnapshot:
                                        snapshot.getChildren()) {
                                    orderModelArrayList.add(datasnapshot.getValue(clsOrderModel.class));
                                }
                                rcorderAdapter=new RcorderAdapter(orderbydate.this,orderModelArrayList);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                recyclerView.setAdapter(rcorderAdapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }
}