package com.example.tabrizguilds.tabrizguilds.fragments.categories;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.ViewPagerCustomDuration;
import com.example.tabrizguilds.tabrizguilds.adapter.categoriesSliderAdapter;
import com.example.tabrizguilds.tabrizguilds.app;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class educationFragment extends Fragment {


    private RelativeLayout relativeBack;
    private RecyclerView recycler;
    private TextView txtTitle;

    private int currentPage = 0;
    private int totalSlides = 3;
    private ViewPagerCustomDuration mPager;

    public educationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        initView(view);
        initSlider();

        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        mPager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
    }

    private void initSlider() {


        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.shop1);
        images.add(R.drawable.shop2);
        images.add(R.drawable.shop3);
        mPager.setAdapter(new categoriesSliderAdapter(getContext(), images));
//        app.isScheduled = false;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == totalSlides) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        if (app.isScheduled) {
            app.swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 7000);
            app.isScheduled = true;
        }

    }
}
