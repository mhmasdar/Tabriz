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
import com.example.tabrizguilds.tabrizguilds.fragments.categories.carsFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.clothesFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.educationFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.housingFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.medicalFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.officesFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.restaurantsListFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.servicesFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.shoppingFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.sportFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.stayFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.tourismFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.categories.transportFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.carSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.clothesSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.educationSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.housingSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.officeSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.restaurantSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.shoppingSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.sportSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.staySub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.transportSub;
import com.example.tabrizguilds.tabrizguilds.fragments.subGroups.utilitySub;
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

                idCategory = 1;
                restaurantSub fragment = new restaurantSub();
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
                shoppingSub fragment = new shoppingSub();
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

                idCategory = 3;
                staySub fragment = new staySub();
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

                idCategory = 4;
                clothesSub fragment = new clothesSub();
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

                idCategory = 5;
                housingSub fragment = new housingSub();
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

                idCategory = 6;
                educationSub fragment = new educationSub();
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

                idCategory = 7;
                sportSub fragment = new sportSub();
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

                idCategory = 8;
                carSub fragment = new carSub();
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

                idCategory = 9;
                transportSub fragment = new transportSub();
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

                idCategory = 10;
                utilitySub fragment = new utilitySub();
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

                idCategory = 11;
                officeSub fragment = new officeSub();
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

                idCategory = 12;
                medicalFragment fragment = new medicalFragment();
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
