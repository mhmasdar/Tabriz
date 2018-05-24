package com.example.tabrizguilds.tabrizguilds.introPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.viewpagerindicator.CirclePageIndicator;


/**
 * Created by mohamadHasan on 20/07/2017.
 */

public class IntroActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ImageView btnIntroNext, btnIntroPre;
    private CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        btnIntroNext = (ImageView) findViewById(R.id.btnIntroNext);
        btnIntroPre = (ImageView) findViewById(R.id.btnIntroPre);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);


        final float density = getResources().getDisplayMetrics().density;

        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));
        indicator.setViewPager(mViewPager);
        indicator.setRadius(5 * density);

        // Set a PageTransformer
        mViewPager.setPageTransformer(false, new IntroPageTransformer());


        btnIntroNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });

        btnIntroPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
            }
        });
    }
}
