package com.project.agriculturemanagmentapp;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class orderbydate extends AppCompatActivity {
RcorderAdapter rcorderAdapter;
RecyclerView recyclerView;
RelativeLayout rldate;
TextView txtdate;
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
        String date = day + "-" + (month) + "-" + year;
        txtdate.setText(date);
        FirebaseRecyclerOptions<clsOrderModel> options=new FirebaseRecyclerOptions.Builder<clsOrderModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders").child(date), clsOrderModel.class)
                .build();
        rcorderAdapter=new RcorderAdapter(options,orderbydate.this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(rcorderAdapter);
        rldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(orderbydate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth + "-" + (month) + "-" + year;
                        txtdate.setText(dayOfMonth + "/" + (++month) + "/" + year);
                        FirebaseRecyclerOptions<clsOrderModel> options=new FirebaseRecyclerOptions.Builder<clsOrderModel>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders").child(date), clsOrderModel.class)
                                .build();
                        rcorderAdapter=new RcorderAdapter(options,orderbydate.this);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setAdapter(rcorderAdapter);
                        rcorderAdapter.startListening();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        rcorderAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rcorderAdapter.stopListening();
    }
}