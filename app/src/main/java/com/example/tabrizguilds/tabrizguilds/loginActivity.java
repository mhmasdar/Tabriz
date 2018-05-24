package com.example.tabrizguilds.tabrizguilds;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.adapter.loginViewPager;
import com.example.tabrizguilds.tabrizguilds.adapter.referendimViewPager;

public class loginActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private ImageView imgLoginBack;
    private android.support.design.widget.TabLayout TabLayout;
    private ViewPager viewPager;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");


        //set image dark
        imgLoginBack.setColorFilter(Color.rgb(110, 110, 110), android.graphics.PorterDuff.Mode.MULTIPLY);


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TabLayout.addTab(TabLayout.newTab().setText("ورود"));
        TabLayout.addTab(TabLayout.newTab().setText("ثبت نام"));
        loginViewPager adapter = new loginViewPager (getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(TabLayout));


        TabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        changeTabsFont();
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) TabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {

                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        imgLoginBack = (ImageView) findViewById(R.id.imgLoginBack);
        TabLayout = (TabLayout) findViewById(R.id.TabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
