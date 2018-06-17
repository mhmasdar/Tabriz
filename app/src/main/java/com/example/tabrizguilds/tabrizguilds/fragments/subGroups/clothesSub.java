package com.example.tabrizguilds.tabrizguilds.fragments.subGroups;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.clothesFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.shoppingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class clothesSub extends Fragment {


    private RelativeLayout lytBack;
    private LinearLayout lytSub1;
    private LinearLayout lytSub2;
    private LinearLayout lytSub3;
    private LinearLayout lytSub4;
    private LinearLayout lytSub5;
    private LinearLayout lytSub6;
    private LinearLayout lytSub7;
    private LinearLayout lytSub8;
    private LinearLayout lytSub9;
    private LinearLayout lytSub10;
    private LinearLayout lytSub11;

    public clothesSub() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clothes_sub, container, false);
        initView(view);


        lytSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothesFragment fragment = new clothesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return view;
    }

    private void initView(View view) {
        lytBack = (RelativeLayout) view.findViewById(R.id.lytBack);
        lytSub1 = (LinearLayout) view.findViewById(R.id.lytSub1);
        lytSub2 = (LinearLayout) view.findViewById(R.id.lytSub2);
        lytSub3 = (LinearLayout) view.findViewById(R.id.lytSub3);
        lytSub4 = (LinearLayout) view.findViewById(R.id.lytSub4);
        lytSub5 = (LinearLayout) view.findViewById(R.id.lytSub5);
        lytSub6 = (LinearLayout) view.findViewById(R.id.lytSub6);
        lytSub7 = (LinearLayout) view.findViewById(R.id.lytSub7);
        lytSub8 = (LinearLayout) view.findViewById(R.id.lytSub8);
        lytSub9 = (LinearLayout) view.findViewById(R.id.lytSub9);
        lytSub10 = (LinearLayout) view.findViewById(R.id.lytSub10);
        lytSub11 = (LinearLayout) view.findViewById(R.id.lytSub11);
    }

}
