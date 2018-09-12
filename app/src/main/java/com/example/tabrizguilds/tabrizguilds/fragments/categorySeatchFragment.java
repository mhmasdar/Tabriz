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

    private String sortType = "Name";
    private List<Integer> allFilters;
    private List<Integer> selectedFilters;
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
        allFilters.add(1);
        allFilters.add(2);
        allFilters.add(3);
        allFilters.add(4);
        allFilters.add(5);
        allFilters.add(6);
        allFilters.add(7);
        allFilters.add(8);
        allFilters.add(9);
        allFilters.add(10);
        allFilters.add(11);
        allFilters.add(12);
        allFilters.add(13);

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
                } else
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
        final CheckBox[] checkBox = new CheckBox[14]; // check[0] is all
        checkBox[0] = dialog.findViewById(R.id.check0);
        checkBox[1] = dialog.findViewById(R.id.check1);
        checkBox[2] = dialog.findViewById(R.id.check2);
        checkBox[3] = dialog.findViewById(R.id.check3);
        checkBox[4] = dialog.findViewById(R.id.check4);
        checkBox[5] = dialog.findViewById(R.id.check5);
        checkBox[6] = dialog.findViewById(R.id.check6);
        checkBox[7] = dialog.findViewById(R.id.check7);
        checkBox[8] = dialog.findViewById(R.id.check8);
        checkBox[9] = dialog.findViewById(R.id.check9);
        checkBox[10] = dialog.findViewById(R.id.check10);
        checkBox[11] = dialog.findViewById(R.id.check11);
        checkBox[12] = dialog.findViewById(R.id.check12);
        checkBox[13] = dialog.findViewById(R.id.check13);

        if (selectedFilters.size() == 13) {
            checkBox[0].setChecked(true);
        } else {
            for (int i = 0; i < selectedFilters.size(); i++) {
                if (selectedFilters.get(i) == 1)
                    checkBox[1].setChecked(true);
                if (selectedFilters.get(i) == 2)
                    checkBox[2].setChecked(true);
                if (selectedFilters.get(i) == 3)
                    checkBox[3].setChecked(true);
                if (selectedFilters.get(i) == 4)
                    checkBox[4].setChecked(true);
                if (selectedFilters.get(i) == 5)
                    checkBox[5].setChecked(true);
                if (selectedFilters.get(i) == 6)
                    checkBox[6].setChecked(true);
                if (selectedFilters.get(i) == 7)
                    checkBox[7].setChecked(true);
                if (selectedFilters.get(i) == 8)
                    checkBox[8].setChecked(true);
                if (selectedFilters.get(i) == 9)
                    checkBox[9].setChecked(true);
                if (selectedFilters.get(i) == 10)
                    checkBox[10].setChecked(true);
                if (selectedFilters.get(i) == 11)
                    checkBox[11].setChecked(true);
                if (selectedFilters.get(i) == 12)
                    checkBox[12].setChecked(true);
                if (selectedFilters.get(i) == 13)
                    checkBox[13].setChecked(true);
            }
        }


        for (int i = 1; i < 13; i++) {
            checkBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        checkBox[0].setChecked(false);
                }
            });
        }


        checkBox[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 1; i < 13; i++)
                        checkBox[i].setChecked(false);

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

                if (checkBox[0].isChecked()) {
                    selectedFilters = allFilters;
                } else {
                    selectedFilters = new ArrayList<>();


                    for (int i = 1; i < 13; i++) {
                        if (checkBox[i].isChecked())
                            selectedFilters.add(allFilters.get(i - 1));
                    }
                }

                if (selectedFilters.size() == 0) {
                    Toast.makeText(getContext(), "حداقل یک مورد انتخاب کنید", Toast.LENGTH_LONG).show();
                } else {
                    if (!edtSearch.getText().toString().equals("")) {
                        DatabaseCallback callback = new DatabaseCallback(getContext());
                        callback.execute();
                        dialog.dismiss();
                    } else
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
        Button btnSort = dialog.findViewById(R.id.btnSort);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
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

                if (radioName.isChecked()) {
                    sortType = "Name";
                } else if (radioLike.isChecked()) {
                    sortType = "likeCount";
                } else if (radioRate.isChecked()) {
                    sortType = "star";
                }

                if (!edtSearch.getText().toString().equals("")) {
                    DatabaseCallback callback = new DatabaseCallback(getContext());
                    callback.execute();
                    dialog.dismiss();
                } else
                    Toast.makeText(getContext(), "عبارت جستوجو را وارد کنید", Toast.LENGTH_LONG).show();

            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    private void setUpRecyclerView(List<PlacesModel> placesList) {

        restaurantListAdapter adapter = new restaurantListAdapter(getContext(), placesList);
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

            placeList = databaseHelper.selectAllPlacesbySearch(searchValue, sortType, selectedFilters);

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
