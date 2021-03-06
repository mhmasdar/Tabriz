package com.example.tabrizguilds.tabrizguilds.fragments.categories;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.ViewPagerCustomDuration;
import com.example.tabrizguilds.tabrizguilds.adapter.restaurantListAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.categoriesSliderAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class restaurantsListFragment extends Fragment {


    private RelativeLayout relativeBack;
//    private TabLayout catListTabLayout;
    private RecyclerView recycler;
    private TextView txtTitle;


    private int currentPage = 0;
    private int totalSlides = 3;
    private ViewPagerCustomDuration mPager;

    List<PlacesModel> placesList;
    List<PlacesModel> filteredList = new ArrayList<>();
    private int totalTabsCount;
    private DatabaseCallback databaseCallback;

    private int rootCategory = 0;
    private int subCategory = 0;
    TextView txtToolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants_list, container, false);
        initView(view);


        Bundle args = getArguments();
        rootCategory = args.getInt("rootCategory");
        subCategory = args.getInt("subCategory");
        txtToolbar.setText(args.getString("subCategoryName"));

        initSlider();

        databaseCallback = new DatabaseCallback(getContext(), rootCategory, subCategory);
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        recycler.setNestedScrollingEnabled(false);

        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
//        catListTabLayout = (TabLayout) view.findViewById(R.id.catListTabLayout);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        mPager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtToolbar = view.findViewById(R.id.txtToolbar);
    }

    private void setUpRecyclerView(List<PlacesModel> placesList) {

        restaurantListAdapter adapter = new restaurantListAdapter(getContext(), placesList);
        recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void initSlider() {

        List<Integer> images = new ArrayList<>();

        switch (rootCategory)
        {
            case 1:
                images.add(R.drawable.eat1);
                images.add(R.drawable.eat2);
                images.add(R.drawable.eat3);
                break;

            case 2:
                images.add(R.drawable.shop1);
                images.add(R.drawable.shop2);
                images.add(R.drawable.shop3);
                break;

            case 3:
                images.add(R.drawable.hotel1);
                images.add(R.drawable.hotel2);
                images.add(R.drawable.hotel3);
                break;

            case 4:
                images.add(R.drawable.stay1);
                images.add(R.drawable.stay2);
                images.add(R.drawable.stay3);
                break;

            case 5:
                images.add(R.drawable.cloth1);
                images.add(R.drawable.cloth2);
                images.add(R.drawable.cloth3);
                break;

            case 6:
                images.add(R.drawable.house1);
                images.add(R.drawable.house2);
                images.add(R.drawable.house3);
                break;

            case 7:
                images.add(R.drawable.edu1);
                images.add(R.drawable.edu2);
                images.add(R.drawable.edu3);
                break;

            case 8:
                images.add(R.drawable.sport1);
                images.add(R.drawable.sport2);
                images.add(R.drawable.sport3);
                break;

            case 9:
                images.add(R.drawable.car1);
                images.add(R.drawable.car2);
                images.add(R.drawable.car3);
                break;

            case 10:
                images.add(R.drawable.trans1);
                images.add(R.drawable.trans2);
                images.add(R.drawable.trans3);
                break;

            case 11:
                images.add(R.drawable.ser1);
                images.add(R.drawable.ser2);
                images.add(R.drawable.ser3);
                break;

            case 12:
                images.add(R.drawable.off1);
                images.add(R.drawable.off2);
                images.add(R.drawable.off3);
                break;

            case 13:
                images.add(R.drawable.med1);
                images.add(R.drawable.med2);
                images.add(R.drawable.med3);
                break;
        }

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


    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;

        private Context context;
        private int rootCategory;
        private int subCategory;

        public DatabaseCallback(Context context, int rootCategory, int subCategory) {
            this.context = context;
            this.subCategory = subCategory;
            this.rootCategory = rootCategory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            placesList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesList = databaseHelper.selectPlacesToList(rootCategory, subCategory);

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

        if(databaseCallback != null && databaseCallback.getStatus() == AsyncTask.Status.RUNNING)
            databaseCallback.cancel(true);
    }
}
