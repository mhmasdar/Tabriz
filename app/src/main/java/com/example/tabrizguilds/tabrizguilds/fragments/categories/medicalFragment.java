package com.example.tabrizguilds.tabrizguilds.fragments.categories;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.ViewPagerCustomDuration;
import com.example.tabrizguilds.tabrizguilds.adapter.categoriesSliderAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.restaurantListAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class medicalFragment extends Fragment {

    private RelativeLayout relativeBack;
    private TabLayout catListTabLayout;
    private RecyclerView recycler;
    private Typeface typeface;

    private int currentPage = 0;
    private int totalSlides = 3;
    private ViewPagerCustomDuration mPager;
    private DbGetPlacesList dbGetPlacesList;
    List<PlacesModel> placesList;
    List<PlacesModel> filteredList = new ArrayList<>();
    private int totalTabsCount;

    public medicalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical, container, false);

        initView(view);
        initSlider();

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");


        recycler.setNestedScrollingEnabled(false);

        dbGetPlacesList = new DbGetPlacesList(getContext(), "Tbl_Medicals");
        dbGetPlacesList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        catListTabLayout.addTab(catListTabLayout.newTab().setText("کلینیک"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("داروخانه"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("درمانگاه"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("بیمارستان"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("همه"));

        totalTabsCount = catListTabLayout.getTabCount();


        catListTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filteredList = new ArrayList<>();

                if (tab.getPosition() == totalTabsCount - 1){
                    setUpRecyclerView(placesList);
                }
                else {

                    for (int i = 0; i < placesList.size(); i++) {
                        if (placesList.get(i).type == totalTabsCount - (tab.getPosition() + 1))
                            filteredList.add(placesList.get(i));
                    }
                    setUpRecyclerView(filteredList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        View root = catListTabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimary));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        changeTabsFont();

        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                catListTabLayout.getTabAt(4).select();
            }
        }, 2);

        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        catListTabLayout = (TabLayout) view.findViewById(R.id.catListTabLayout);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        mPager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) catListTabLayout.getChildAt(0);
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

    private void initSlider() {

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.med1);
        images.add(R.drawable.med2);
        images.add(R.drawable.med3);
        mPager.setAdapter(new categoriesSliderAdapter(getContext(), images));

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

    private void setUpRecyclerView(List<PlacesModel> placesList){

        restaurantListAdapter adapter = new restaurantListAdapter(getContext(), placesList, "Tbl_Medicals");
        recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public class DbGetPlacesList extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;

        public DbGetPlacesList(Context context, String tblName) {
            this.context = context;
            this.tblName = tblName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            placesList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesList = databaseHelper.selectAllPlacesToList(tblName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (placesList != null)
                if (placesList.size() > 0)
                    setUpRecyclerView(placesList);

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(dbGetPlacesList != null && dbGetPlacesList.getStatus() == AsyncTask.Status.RUNNING)
            dbGetPlacesList.cancel(true);
    }
}
