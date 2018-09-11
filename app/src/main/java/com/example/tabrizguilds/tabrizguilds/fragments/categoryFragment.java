package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.navigationDrawerActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class categoryFragment extends Fragment {


    private RelativeLayout lytSearch;
    private RelativeLayout lytMenu;
    private LinearLayout lytTourism;
    private LinearLayout lytShopping;
    private LinearLayout lytRestaurant;
    private LinearLayout lytEducation;
    private LinearLayout lytHousing;
    private LinearLayout lytClothes;
    private LinearLayout lytTransport;
    private LinearLayout lytCars;
    private LinearLayout lytSport;
    private LinearLayout lytMedical;
    private LinearLayout lytOffice;
    private LinearLayout lytUtilities;

    private int rootCategory;


    public categoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        app.check = 1;


        lytMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity)getActivity()).drawer();
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });



        lytSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                categorySeatchFragment fragment = new categorySeatchFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 1;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "رستوران و خوراکی");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 2;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "مراکز خرید");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        lytTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 4;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "گردشگری");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 5;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "مد و لباس");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 6;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "خانه و مسکن");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 7;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "آموزش");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 8;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "ورزشی");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 9;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "اتومبیل");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 10;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "حمل و نقل");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 11;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "خدمات و تسهیلات");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 12;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "اماکن و ادارات");
                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                rootCategory = 13;
                SubCategoryFragment fragment = new SubCategoryFragment();
                Bundle args = new Bundle();
                args.putInt("rootCategory", rootCategory);
                args.putString("categoryName", "مراکز درمانی");
                fragment.setArguments(args);
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
        lytSearch = (RelativeLayout) view.findViewById(R.id.lytSearch);
        lytMenu = (RelativeLayout) view.findViewById(R.id.lytMenu);
        lytTourism = (LinearLayout) view.findViewById(R.id.lytTourism);
        lytShopping = (LinearLayout) view.findViewById(R.id.lytShopping);
        lytRestaurant = (LinearLayout) view.findViewById(R.id.lytRestaurant);
        lytEducation = (LinearLayout) view.findViewById(R.id.lytEducation);
        lytHousing = (LinearLayout) view.findViewById(R.id.lytHousing);
        lytClothes = (LinearLayout) view.findViewById(R.id.lytClothes);
        lytTransport = (LinearLayout) view.findViewById(R.id.lytTransport);
        lytCars = (LinearLayout) view.findViewById(R.id.lytCars);
        lytSport = (LinearLayout) view.findViewById(R.id.lytSport);
        lytMedical = (LinearLayout) view.findViewById(R.id.lytMedical);
        lytOffice = (LinearLayout) view.findViewById(R.id.lytOffice);
        lytUtilities = (LinearLayout) view.findViewById(R.id.lytUtilities);
    }
}
