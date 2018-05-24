package com.example.tabrizguilds.tabrizguilds;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.adapter.referendimViewPager;

public class referendumActivity extends AppCompatActivity {

    private TextView txtCatTitle;
    private RelativeLayout relativeBack;
    private TabLayout referendimTabLayout;
    private ViewPager viewPager;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referendum);
        initView();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        referendimTabLayout.addTab(referendimTabLayout.newTab().setText("نظرسنجی"));
        referendimTabLayout.addTab(referendimTabLayout.newTab().setText("مسابقه"));
        referendimViewPager adapter = new referendimViewPager (getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(referendimTabLayout));


        referendimTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void initView() {
        txtCatTitle = (TextView) findViewById(R.id.txtCatTitle);
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        referendimTabLayout = (TabLayout) findViewById(R.id.referendimTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) referendimTabLayout.getChildAt(0);
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
}
