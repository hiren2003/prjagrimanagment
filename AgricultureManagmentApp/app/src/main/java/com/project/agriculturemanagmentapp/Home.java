package com.project.agriculturemanagmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
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
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class Home extends AppCompatActivity {
    MeowBottomNavigation btmnv;
    FrameLayout frameLayout;
    RelativeLayout toolbar;
    ImageView prfpc, imgcart, imgorder,imgmessage;
    TextView txtname;
    BubbleNavigationLinearView bubbleNavigationLinearView;
    SharedPreferences sharedPreferences;
    ImageView  fltfeed, fltvacancy,fltlbr,fltrsell,fltai,fltsearch;
    boolean clicked = false;
    LottieAnimationView fltadd;
View view;
TextView txt1,txt2,txt3,txt4,txt5,txt6;
Animation rotateOpen,rotateClose,fromBottom,toBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        setLanguage();
     sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
     view=findViewById(R.id.view);
        btmnv = findViewById(R.id.btmnv);
        toolbar = findViewById(R.id.toolbar);
        prfpc = findViewById(R.id.prfpc);
        imgcart = findViewById(R.id.imgcart);
        frameLayout = findViewById(R.id.fmlayout);
        txtname = findViewById(R.id.txtname);
        imgorder = findViewById(R.id.imgorder);
        imgmessage=findViewById(R.id.imgmessage);
        bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
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
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        txt4=findViewById(R.id.txt4);
        txt5=findViewById(R.id.txt5);
        txt6=findViewById(R.id.txt6);
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
                cdvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Home.this,add_video.class));
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
        txtname.setText(sharedPreferences.getString("uname", "man"));
        Glide.with(this)
                .load(sharedPreferences.getString("url", "null"))
                .circleCrop()
                .into(prfpc);
        frameLayout.removeAllViews();
        getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Feed()).commit();
        prfpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Navigation.class));
            }
        });
        imgorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MyOrder.class));
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
        btmnv.show(3, true);
        btmnv.add(new MeowBottomNavigation.Model(1, R.drawable.feed2));
        btmnv.add(new MeowBottomNavigation.Model(2, R.drawable.labour));
        btmnv.add(new MeowBottomNavigation.Model(3, R.drawable.house));
        btmnv.add(new MeowBottomNavigation.Model(4, R.drawable.resell));
        btmnv.add(new MeowBottomNavigation.Model(5, R.drawable.online_store));
        btmnv.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                if (model.getId() == 0) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Feed()).commit();
                } else if (model.getId() == 1) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Labour_Vacancy()).commit();
                }  else if (model.getId() == 3) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Resell()).commit();
                } else if (model.getId() == 4) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new E_commrce()).commit();
                }
                else {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new frghome()).commit();
                }
                return null;
            }
        });
        bubbleNavigationLinearView.setCurrentActiveItem(2);
        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if (position == 0) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Feed()).commit();
                } else if (position == 1) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Other_vacancy()).commit();
                } else if (position == 2) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new frghome()).commit();
                } else if (position == 3) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new Resell()).commit();
                } else if (position == 4) {
                    frameLayout.removeAllViews();
                    getSupportFragmentManager().beginTransaction().add(R.id.fmlayout, new E_commrce()).commit();
                } else {

                }
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
            fltsearch.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            txt1.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            txt2.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            txt3.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            txt4.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            txt5.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
            txt6.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        }
        else{
            fltfeed.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltvacancy.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltlbr.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltrsell.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltai.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            fltsearch.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            txt1.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            txt2.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            txt3.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            txt4.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            txt5.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
            txt6.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        }
    }
    void setVisibility(boolean clicked){
        if(!clicked){
            fltfeed.setVisibility(View.VISIBLE);
            fltvacancy.setVisibility(View.VISIBLE);
            fltlbr.setVisibility(View.VISIBLE);
            fltrsell.setVisibility(View.VISIBLE);
            fltai.setVisibility(View.VISIBLE);
            fltsearch.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            txt3.setVisibility(View.VISIBLE);
            txt4.setVisibility(View.VISIBLE);
            txt5.setVisibility(View.VISIBLE);
            txt6.setVisibility(View.VISIBLE);
        }
        else{
            fltfeed.setVisibility(View.INVISIBLE);
            fltvacancy.setVisibility(View.INVISIBLE);
            fltlbr.setVisibility(View.INVISIBLE);
            fltrsell.setVisibility(View.INVISIBLE);
            fltai.setVisibility(View.INVISIBLE);
            fltsearch.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);
            txt1.setVisibility(View.INVISIBLE);
            txt2.setVisibility(View.INVISIBLE);
            txt3.setVisibility(View.INVISIBLE);
            txt4.setVisibility(View.INVISIBLE);
            txt5.setVisibility(View.INVISIBLE);
            txt6.setVisibility(View.INVISIBLE);
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