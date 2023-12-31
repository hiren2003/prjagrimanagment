package com.project.agriculturemanagmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.L;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String ListType, Mo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FollowList() {
        // Required empty public constructor
    }
    public FollowList(String ListType,String Mo) {
        this.ListType= ListType;
        this.Mo=Mo;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FollowList.
     */
    // TODO: Rename and change types and number of parameters
    public static FollowList newInstance(String param1, String param2) {
        FollowList fragment = new FollowList();
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
        View view= inflater.inflate(R.layout.fragment_follow_list, container, false);
        RecyclerView rcfollower=view.findViewById(R.id.rcfollower);
        rcfollower.setLayoutManager(new GridLayoutManager(getContext(),2));

        FirebaseDatabase.getInstance().getReference().child("User").child(Mo).child(ListType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                ArrayList<String> arrayList=new ArrayList<>();
                for (DataSnapshot dt:snapshot2.getChildren()){
                    arrayList.add(dt.getValue().toString());
                }
                FirebaseDatabase.getInstance().getReference().child("Users_List").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<clsUserModel> userModelArrayList=new ArrayList<>();
                        for (DataSnapshot datasnapshot:
                             snapshot.getChildren()) {
                            clsUserModel model=datasnapshot.getValue(clsUserModel.class);
                            if(arrayList.contains(model.getMo())){
                                userModelArrayList.add(model);
                            }
                        }
                        RcuserAdapter rcuserAdapter=new RcuserAdapter(getContext(),userModelArrayList,false,false);
                        rcfollower.setAdapter(rcuserAdapter);
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
        return  view;
    }
}