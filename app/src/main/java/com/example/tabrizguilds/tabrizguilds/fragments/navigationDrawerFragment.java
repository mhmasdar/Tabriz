package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.referendumActivity;
import com.example.tabrizguilds.tabrizguilds.suggestionActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class navigationDrawerFragment extends Fragment {


    private TextView txtLogin;
    private LinearLayout lytIntroduction;
    private LinearLayout lytFavorites;
    private LinearLayout lytreferendum;
    private LinearLayout lytSuggestion;
    private LinearLayout lytShare;
    private LinearLayout lytAbout;
    private LinearLayout lytExit;

    public navigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        initView(view);

        lytSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), suggestionActivity.class);
                startActivity(i);
            }
        });


        lytreferendum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), referendumActivity.class);
                startActivity(i);
            }
        });


        return view;
    }

    private void initView(View view) {
        txtLogin = (TextView) view.findViewById(R.id.txtLogin);
        lytIntroduction = (LinearLayout) view.findViewById(R.id.lytIntroduction);
        lytFavorites = (LinearLayout) view.findViewById(R.id.lytFavorites);
        lytreferendum = (LinearLayout) view.findViewById(R.id.lytreferendum);
        lytSuggestion = (LinearLayout) view.findViewById(R.id.lytSuggestion);
        lytShare = (LinearLayout) view.findViewById(R.id.lytShare);
        lytAbout = (LinearLayout) view.findViewById(R.id.lytAbout);
        lytExit = (LinearLayout) view.findViewById(R.id.lytExit);
    }
}
