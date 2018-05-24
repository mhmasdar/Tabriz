package com.example.tabrizguilds.tabrizguilds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.fragments.categoryFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.mapFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.newsListFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.supportFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private LinearLayout container, container3;
    private LinearLayout lytSupport, lytMenu;
    private ImageView imgSupport;
    private TextView txtSupport;
    private LinearLayout lytEvents;
    private ImageView imgEvents;
    private TextView txtEvents;
    private LinearLayout lytMap;
    private ImageView imgMap;
    private TextView txtMap;
    private LinearLayout lytCategory;
    private ImageView imgCategory;
    private TextView txtCategory;
    private LinearLayout lytHome;
    private ImageView imgHome;
    private TextView txtHome;
    private FragmentTransaction ft, ft2, ft3, ft4;
    private boolean doubleBackToExitPressedOnce = false, frgCreateCheck4 = false;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();




        //display home fragment as default
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, new HomeFragment());
        ft.commit();



        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        lytCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imm.hideSoftInputFromWindow(lytCategory.getWindowToken(), 0);

                if (app.check != 1 && app.check != 5)
                    setLytCategory();

            }
        });


        lytEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(lytEvents.getWindowToken(), 0);

                if (app.check != 2 && app.check != 6)
                    setLytEvants();
            }
        });


        lytSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                if (!frgCreateCheck4)
                {
                    frgCreateCheck4 = true;
                    setLytSupport();
                }

                else
                    showLytSupport();
            }
        });


        lytHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(lytHome.getWindowToken(), 0);

                if (app.check != 0)
                    setLytHome();
            }
        });


        lytMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(lytMap.getWindowToken(), 0);

                if (app.check != 4)
                    setLytMap();
            }
        });

    }


    private void initView() {
        container = (LinearLayout) findViewById(R.id.container);
        container3 = (LinearLayout) findViewById(R.id.container3);
        lytSupport = (LinearLayout) findViewById(R.id.lytSupport);
        lytMenu = (LinearLayout) findViewById(R.id.lytMenu);
        imgSupport = (ImageView) findViewById(R.id.imgSupport);
        txtSupport = (TextView) findViewById(R.id.txtSupport);
        lytEvents = (LinearLayout) findViewById(R.id.lytEvents);
        imgEvents = (ImageView) findViewById(R.id.imgEvents);
        txtEvents = (TextView) findViewById(R.id.txtEvents);
        lytMap = (LinearLayout) findViewById(R.id.lytMap);
        imgMap = (ImageView) findViewById(R.id.imgMap);
        txtMap = (TextView) findViewById(R.id.txtMap);
        lytCategory = (LinearLayout) findViewById(R.id.lytCategory);
        imgCategory = (ImageView) findViewById(R.id.imgCategory);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        lytHome = (LinearLayout) findViewById(R.id.lytHome);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        txtHome = (TextView) findViewById(R.id.txtHome);
    }

    private void setLytCategory(){


        imgCategory.setImageResource(R.drawable.ic_category_selected);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));

        ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.container, new categoryFragment());
        ft2.commit();

        container.setVisibility(View.VISIBLE);
        container3.setVisibility(View.GONE);

        app.check = 1;
    }

    private void setLytEvants(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event_selected);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));

        container.setVisibility(View.VISIBLE);
        container3.setVisibility(View.GONE);

        ft3 = getSupportFragmentManager().beginTransaction();
        ft3.replace(R.id.container, new newsListFragment());
        ft3.commit();

        app.check = 2;
    }

    private void setLytSupport(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support_selected);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.colorPrimary));

        container.setVisibility(View.GONE);
        container3.setVisibility(View.VISIBLE);

        ft4 = getSupportFragmentManager().beginTransaction();
        ft4.replace(R.id.container3, new supportFragment());
        ft4.commit();

        app.check = 3;
    }

    private void setLytHome(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home_selected);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));

        container.setVisibility(View.VISIBLE);
        container3.setVisibility(View.GONE);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HomeFragment());
        ft.commit();

        app.check = 0;
    }

    private void setLytMap(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));

        container.setVisibility(View.VISIBLE);
        container3.setVisibility(View.GONE);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new mapFragment());
        ft.commit();

        app.check = 4;
    }

    private void showLytSupport(){

        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support_selected);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.colorPrimary));

        container.setVisibility(View.GONE);
        container3.setVisibility(View.VISIBLE);

        app.check = 3;
    }

    @Override
    public void onBackPressed() {

        switch (app.check)
        {
            case 0:
                if (doubleBackToExitPressedOnce) {
                    finish();
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "برای خروج مجددا کلید برگشت را بزنید", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
                break;

            case 1:
                setLytHome();
                break;

            case 2:
                setLytHome();
                break;

            case 3:
                setLytHome();
                break;

            case 4:
                setLytHome();
                break;

                default:
                    super.onBackPressed();
        }
    }
}
