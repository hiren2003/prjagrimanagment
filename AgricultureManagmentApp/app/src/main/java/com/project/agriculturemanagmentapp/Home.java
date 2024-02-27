package com.project.agriculturemanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class Home extends AppCompatActivity {
    FrameLayout frameLayout;
    RelativeLayout toolbar;
    ImageView prfpc, imgcart,imgmessage;
    TextView txtname;
    BubbleNavigationLinearView bubbleNavigationLinearView;
    SharedPreferences sharedPreferences;
    ImageView  fltfeed, fltvacancy,fltlbr,fltrsell,fltai,fltsearch;
    boolean clicked = false;
    LottieAnimationView fltadd;
View view;
TextView txt1,txt2,txt3,txt4,txt5,txt6;
Animation rotateOpen,rotateClose,fromBottom,toBottom;
ViewPager vp;
TabLayout tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tb=findViewById(R.id.tl_landing);
        vp=findViewById(R.id.vp_landing);
        view=findViewById(R.id.view);
        toolbar = findViewById(R.id.toolbar);
        prfpc = findViewById(R.id.prfpc);
        imgcart = findViewById(R.id.imgcart);
        txtname = findViewById(R.id.txtname);
        imgmessage=findViewById(R.id.imgmessage);
        rotateOpen= AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose= AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom= AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        fltadd =findViewById(R.id.floatingActionButton);
        fltfeed =findViewById(R.id.floatingActionButton1);
        fltvacancy =findViewById(R.id.floatingActionButton2);
        fltlbr=findViewById(R.id.floatingActionButton3);
        fltrsell=findViewById(R.id.floatingActionButton4);
        fltai=findViewById(R.id.floatingActionButton5);
        fltsearch=findViewById(R.id.floatingActionButton6);

        VpHomeAdapter vpHomeAdapter=new VpHomeAdapter(getSupportFragmentManager(),Home.this);
        vp.setAdapter(vpHomeAdapter);
        tb.setupWithViewPager(vp);
        tb.getTabAt(0).setIcon(R.drawable.instagram);
        tb.getTabAt(1).setIcon(R.drawable.suitcase);
        tb.getTabAt(2).setIcon(R.drawable.home2);
        tb.getTabAt(3).setIcon(R.drawable.resell);
        tb.getTabAt(4).setIcon(R.drawable.store);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        setLanguage();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        fltsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Explore_User.class));
            }
        });
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClick();
            }
        });
        fltfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(Home.this);
                View v1= LayoutInflater.from(Home.this).inflate(R.layout.lyt_add_feed_category,null,false);
                CardView cdfeed = v1.findViewById(R.id.cdfeed);
                CardView cdvideo=v1.findViewById(R.id.cdvideo);
                CardView cdwc=v1.findViewById(R.id.cdwc);
                cdfeed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Home.this,add_feed.class));
                        bottomSheetDialog.cancel();
                    }
                });

                cdwc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Home.this, Write_share.class));
                        bottomSheetDialog.cancel();
                    }
                });
                bottomSheetDialog.setContentView(v1);
                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.show();            }
        });
        fltvacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, add_labour_vacancy.class));
            }
        });
        fltlbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, add_labour.class));
            }
        });
        fltrsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Resell_Category.class));
            }
        });
        fltai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,AI.class));
            }
        });
        prfpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Navigation.class));
            }
        });

        imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Cart.class));
            }
        });
        imgmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ChatList.class));
            }
        });
        txtname.setText(sharedPreferences.getString("uname", "man"));
        Glide.with(this)
                .load(sharedPreferences.getString("url", "null"))
                .circleCrop()
                .into(prfpc);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClick();
            }
        });

    }

    public void setLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String lang = sharedPreferences.getString("getlen", "en");
        Locale locale = new Locale(lang, "rnIN");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        txtname.setText(sharedPreferences.getString("uname", "man"));
        Glide.with(this)
                .load(sharedPreferences.getString("url", "null"))
                .circleCrop()
                .into(prfpc);
    }
    void onAddClick(){
        setAnimation(clicked);
        setVisibility(clicked);
      //  setClickable(clicked);
        clicked=!clicked;
    }
    void setAnimation(boolean clicked){
        if(!clicked){
            fltfeed.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            fltvacancy.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            fltlbr.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            fltrsell.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            fltai.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        }
        else{
            fltfeed.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltvacancy.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltlbr.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltrsell.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltai.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));

        }
    }
    void setVisibility(boolean clicked){
        if(!clicked){
            fltfeed.setVisibility(View.VISIBLE);
            fltvacancy.setVisibility(View.VISIBLE);
            fltlbr.setVisibility(View.VISIBLE);
            fltrsell.setVisibility(View.VISIBLE);
            fltai.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);

        }
        else{
            fltfeed.setVisibility(View.INVISIBLE);
            fltvacancy.setVisibility(View.INVISIBLE);
            fltlbr.setVisibility(View.INVISIBLE);
            fltrsell.setVisibility(View.INVISIBLE);
            fltai.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);

        }
    }
    void setClickable(boolean clicked){
        if (!clicked){
            fltfeed.setClickable(true);
            fltvacancy.setClickable(true);
        }
        fltfeed.setClickable(false);
        fltvacancy.setClickable(false);


    }
}