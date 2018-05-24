package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.artFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.eventsFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.medicalFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.officesFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.organizationFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.restaurantsListFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.servicesFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.shoppingFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.stayFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.tourismFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.transportFragment;
import com.example.tabrizguilds.tabrizguilds.navigationDrawerActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class categoryFragment extends Fragment {


    private RelativeLayout lytMenu;
    private RelativeLayout lytSearch;
    private LinearLayout lytStay;
    private LinearLayout lytShopping;
    private LinearLayout lytRestaurant;
    private LinearLayout lytTravel;
    private LinearLayout lytArt;
    private LinearLayout lytTourism;
    private LinearLayout lytMedical;
    private LinearLayout lytOffices;
    private LinearLayout lytUtilities;
    private LinearLayout lytOrganization;
    private LinearLayout lytEvents;
    private int idCategory;


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

                idCategory = 1;
                restaurantsListFragment fragment = new restaurantsListFragment();
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

                idCategory = 2;
                shoppingFragment fragment = new shoppingFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                idCategory = 3;
                stayFragment fragment = new stayFragment();
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

                idCategory = 4;
                tourismFragment fragment = new tourismFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                idCategory = 5;
                artFragment fragment = new artFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                idCategory = 6;
                transportFragment fragment = new transportFragment();
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

                idCategory = 7;
                servicesFragment fragment = new servicesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        lytOffices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                idCategory = 8;
                officesFragment fragment = new officesFragment();
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
                idCategory = 9;

                medicalFragment fragment = new medicalFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        lytEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                idCategory = 10;
                eventsFragment fragment = new eventsFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        lytOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.check = 5;

                organizationFragment fragment = new organizationFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        lytMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });

        return view;
    }

    private void initView(View view) {
        lytMenu = (RelativeLayout) view.findViewById(R.id.lytMenu);
        lytSearch = (RelativeLayout) view.findViewById(R.id.lytSearch);
        lytStay = (LinearLayout) view.findViewById(R.id.lytStay);
        lytShopping = (LinearLayout) view.findViewById(R.id.lytShopping);
        lytRestaurant = (LinearLayout) view.findViewById(R.id.lytRestaurant);
        lytTravel = (LinearLayout) view.findViewById(R.id.lytTravel);
        lytArt = (LinearLayout) view.findViewById(R.id.lytArt);
        lytTourism = (LinearLayout) view.findViewById(R.id.lytTourism);
        lytMedical = (LinearLayout) view.findViewById(R.id.lytMedical);
        lytOffices = (LinearLayout) view.findViewById(R.id.lytOffices);
        lytUtilities = (LinearLayout) view.findViewById(R.id.lytUtilities);
        lytOrganization = (LinearLayout) view.findViewById(R.id.lytOrganization);
        lytEvents = (LinearLayout) view.findViewById(R.id.lytEvents);
    }
}
