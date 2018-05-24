package com.example.tabrizguilds.tabrizguilds.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.imagesPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class imagesFragment extends Fragment {


    private RelativeLayout lytBack;
    private android.support.design.widget.TabLayout TabLayout;
    private android.support.v4.view.ViewPager ViewPager;
    private Typeface typeface;

    public static int idRow, mainType;

    public imagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        lytBack = (RelativeLayout) view.findViewById(R.id.lytBack);
        TabLayout = (TabLayout) view.findViewById(R.id.TabLayout);
        ViewPager = (ViewPager) view.findViewById(R.id.ViewPager);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");

        Bundle args = getArguments();
        idRow = args.getInt("ID");
        mainType = args.getInt("MainType");


        TabLayout.addTab(TabLayout.newTab().setText("تصاویر سازمان"));
        TabLayout.addTab(TabLayout.newTab().setText("تصاویر ارسالی"));
        imagesPager adapter = new imagesPager (getActivity().getSupportFragmentManager(), args);
        ViewPager.setAdapter(adapter);
        ViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(TabLayout));


        TabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        changeTabsFont();

        return view;
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

}
