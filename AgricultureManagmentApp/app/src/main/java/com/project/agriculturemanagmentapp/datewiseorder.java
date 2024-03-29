package com.project.agriculturemanagmentapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link datewiseorder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class datewiseorder extends Fragment {
    LottieAnimationView loty;
    RcorderAdapter rcorderAdapter;
    RecyclerView recyclerView;
    RelativeLayout rldate;
    TextView txtdate,txtallorder,txtavgorder,txtallpayment,txtavgpayment;
    String date;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public datewiseorder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment datewiseorder.
     */
    // TODO: Rename and change types and number of parameters
    public static datewiseorder newInstance(String param1, String param2) {
        datewiseorder fragment = new datewiseorder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_datewiseorder, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.rccprdt);
        Calendar calendar = Calendar.getInstance();
        LottieAnimationView loty=view.findViewById(R.id.loty3);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        rldate=view.findViewById(R.id.rldate);
        txtdate=view.findViewById(R.id.txtdate);

        date = day + "-" + (++month) + "-" + year;
        FirebaseDatabase.getInstance().getReference().child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot:
                        snapshot.getChildren()) {
                    clsOrderModel model=datasnapshot.getValue(clsOrderModel.class);
                    if (model.getDate().equals(date)){
                        orderModelArrayList.add(model);
                    }
                }
                if (orderModelArrayList.isEmpty()){
                    loty.setVisibility(View.VISIBLE);
                }
                else{
                    loty.setVisibility(View.GONE);
                    rcorderAdapter=new RcorderAdapter(getContext(),orderModelArrayList,true,false);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(rcorderAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txtdate.setText(date);
        rldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date=dayOfMonth + "-" + (++month) + "-" + year;
                        txtdate.setText(dayOfMonth + "/" + (++month) + "/" + year);
                        FirebaseDatabase.getInstance().getReference().child("Orders").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<clsOrderModel> orderModelArrayList = new ArrayList<>();
                                for (DataSnapshot datasnapshot:
                                        snapshot.getChildren()) {
                                    clsOrderModel model=datasnapshot.getValue(clsOrderModel.class);
                                    if (model.getDate().equals(date)){
                                        orderModelArrayList.add(model);
                                    }
                                }
                                if (orderModelArrayList.isEmpty()){
                                    loty.setVisibility(View.VISIBLE);
                                }
                                else{
                                    loty.setVisibility(View.GONE);
                                    rcorderAdapter=new RcorderAdapter(getContext(),orderModelArrayList,true,false);
                                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                    recyclerView.setAdapter(rcorderAdapter);
                                }
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
        return  view;
    }
}