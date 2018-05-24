package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.restaurantListAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class categorySeatchFragment extends Fragment {


    private ExpandableLayout expandableLayout;
    private LinearLayout lytSearch;
    private LinearLayout lytSeperate;
    private EditText edtSearch;
    private RelativeLayout lytBack;
    private LinearLayout lytSort;
    private LinearLayout lytFilter;
    private Dialog dialog;
    private RecyclerView recycle;

    private String sortType = "name";
    private List<String> allFilters;
    private List<String> selectedFilters;
    private List<PlacesModel> placeList = new ArrayList<>();

    public boolean firstTime = true;

    public categorySeatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_seatch, container, false);
        initView(view);

        allFilters = new ArrayList<>();
        allFilters.add("Tbl_Eating");
        allFilters.add("Tbl_Shoppings");
        allFilters.add("Tbl_Rests");
        allFilters.add("Tbl_Tourisms");
        allFilters.add("Tbl_Culturals");
        allFilters.add("Tbl_Transports");
        allFilters.add("Tbl_Services");
        allFilters.add("Tbl_Offices");
        allFilters.add("Tbl_Medicals");

        if (firstTime) {
            selectedFilters = allFilters;
            firstTime = false;
        }

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                expandableLayout.expand();

                Animation fade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                Animation fade1 = AnimationUtils.loadAnimation(getContext(), R.anim.search_btn_back);


                lytSort.setVisibility(View.VISIBLE);
                lytFilter.setVisibility(View.VISIBLE);
                lytBack.setVisibility(View.VISIBLE);
                lytSeperate.setVisibility(View.VISIBLE);


                lytSort.startAnimation(fade);
                lytFilter.startAnimation(fade);
                lytBack.startAnimation(fade1);
                lytSeperate.startAnimation(fade);


            }
        }, 200);


        lytSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();
            }
        });


        lytFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFiltersDialog();
            }
        });


        lytSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtSearch.getText().toString().equals("")) {
                    DatabaseCallback callback = new DatabaseCallback(getContext());
                    callback.execute();
                }
                else
                    Toast.makeText(getContext(), "عبارت جستوجو را وارد کنید", Toast.LENGTH_LONG).show();

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                placeList = new ArrayList<>();
                setUpRecyclerView(placeList);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return view;
    }

    private void initView(View view) {
        expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
        lytSearch = (LinearLayout) view.findViewById(R.id.lytSearch);
        edtSearch = (EditText) view.findViewById(R.id.edt_search);
        lytBack = (RelativeLayout) view.findViewById(R.id.lytBack);
        lytSort = (LinearLayout) view.findViewById(R.id.lytSort);
        lytFilter = (LinearLayout) view.findViewById(R.id.lytFilter);
        lytSeperate = (LinearLayout) view.findViewById(R.id.lytSeperate);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
    }


    private void showFiltersDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filters);
        Button btnFilter = (Button) dialog.findViewById(R.id.btnFilter);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final CheckBox checkAll = dialog.findViewById(R.id.checkAll);
        final CheckBox checkEating = dialog.findViewById(R.id.checkEating);
        final CheckBox checkShopping = dialog.findViewById(R.id.checkShopping);
        final CheckBox checkStay = dialog.findViewById(R.id.checkStay);
        final CheckBox checkTourism = dialog.findViewById(R.id.checkTourism);
        final CheckBox checkArt = dialog.findViewById(R.id.checkArt);
        final CheckBox checkTransport = dialog.findViewById(R.id.checkTransport);
        final CheckBox checkServices = dialog.findViewById(R.id.checkServices);
        final CheckBox checkOffices = dialog.findViewById(R.id.checkOffices);
        final CheckBox checkMedical = dialog.findViewById(R.id.checkMedical);
        final CheckBox checkEvent = dialog.findViewById(R.id.checkEvents);
        final TextView txtEvent = dialog.findViewById(R.id.txtEvent);
        checkEvent.setVisibility(View.GONE);
        txtEvent.setVisibility(View.GONE);

        if (selectedFilters.size() == 9){
            checkAll.setChecked(true);
        }
        else{
            for (int i = 0; i< selectedFilters.size(); i++){
                if (selectedFilters.get(i).equals("Tbl_Eating"))
                    checkEating.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Shoppings"))
                    checkShopping.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Rests"))
                    checkStay.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Tourisms"))
                    checkTourism.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Culturals"))
                    checkArt.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Transports"))
                    checkTransport.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Services"))
                    checkServices.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Offices"))
                    checkOffices.setChecked(true);
                if (selectedFilters.get(i).equals("Tbl_Medicals"))
                    checkMedical.setChecked(true);
            }
        }

        checkArt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });

        checkEating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkShopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkStay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkTourism.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkTransport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkServices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkOffices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkMedical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkEating.setChecked(false);
                    checkShopping.setChecked(false);
                    checkStay.setChecked(false);
                    checkTourism.setChecked(false);
                    checkArt.setChecked(false);
                    checkTransport.setChecked(false);
                    checkServices.setChecked(false);
                    checkOffices.setChecked(false);
                    checkMedical.setChecked(false);

                }

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFilters = new ArrayList<>();

                if (checkAll.isChecked()){
                    selectedFilters = allFilters;
                }
                else{
                    selectedFilters = new ArrayList<>();
                    if (checkEating.isChecked())
                        selectedFilters.add(allFilters.get(0));
                    if (checkShopping.isChecked())
                        selectedFilters.add(allFilters.get(1));
                    if (checkStay.isChecked())
                        selectedFilters.add(allFilters.get(2));
                    if (checkTourism.isChecked())
                        selectedFilters.add(allFilters.get(3));
                    if (checkArt.isChecked())
                        selectedFilters.add(allFilters.get(4));
                    if (checkTransport.isChecked())
                        selectedFilters.add(allFilters.get(5));
                    if (checkServices.isChecked())
                        selectedFilters.add(allFilters.get(6));
                    if (checkOffices.isChecked())
                        selectedFilters.add(allFilters.get(7));
                    if (checkMedical.isChecked())
                        selectedFilters.add(allFilters.get(7));
                }

                if (selectedFilters.size() == 0){
                    Toast.makeText(getContext(), "حداقل یک مورد انتخاب کنید", Toast.LENGTH_LONG).show();
                }
                else {
                    if (!edtSearch.getText().toString().equals("")) {
                        DatabaseCallback callback = new DatabaseCallback(getContext());
                        callback.execute();
                        dialog.dismiss();
                    }
                    else
                        Toast.makeText(getContext(), "عبارت جستوجو را وارد کنید", Toast.LENGTH_LONG).show();

                }

            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showSortDialog() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sort);
        Button btnSort = (Button) dialog.findViewById(R.id.btnSort);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final RadioButton radioName = dialog.findViewById(R.id.radio1);
        final RadioButton radioLike = dialog.findViewById(R.id.radio2);
        final RadioButton radioRate = dialog.findViewById(R.id.radio3);

        radioName.setChecked(true);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioName.isChecked()){
                    sortType = "name";
                }
                else if (radioLike.isChecked()){
                    sortType = "likeCount";
                }
                else if (radioRate.isChecked()){
                    sortType = "star";
                }

                if (!edtSearch.getText().toString().equals("")) {
                    DatabaseCallback callback = new DatabaseCallback(getContext());
                    callback.execute();
                    dialog.dismiss();
                }
                else
                    Toast.makeText(getContext(), "عبارت جستوجو را وارد کنید", Toast.LENGTH_LONG).show();

            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    private void setUpRecyclerView(List<PlacesModel> placesList) {

        restaurantListAdapter adapter = new restaurantListAdapter(getContext(), placesList, "");
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        String searchValue;

        public DatabaseCallback(Context context) {
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
            placeList = new ArrayList<>();
            searchValue = edtSearch.getText().toString();
        }


        @Override
        protected Void doInBackground(Object... objects) {

//            for (int i = 0 ; i < selectedFilters.size(); i++) {
                placeList.addAll(databaseHelper.selectAllPlacesbySearch(selectedFilters, searchValue, sortType));
//            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (placeList != null)
                    setUpRecyclerView(placeList);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!edtSearch.getText().toString().equals("")) {
            DatabaseCallback callback = new DatabaseCallback(getContext());
            callback.execute();
        }
    }
}
