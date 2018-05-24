package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.newsListAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.models.NewsModel;
import com.example.tabrizguilds.tabrizguilds.navigationDrawerActivity;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

import static com.example.tabrizguilds.tabrizguilds.app.context;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsListFragment extends Fragment {


    private RelativeLayout lytSearch;
    private RelativeLayout lytSearchCancel;
    private RelativeLayout lytSearchSubmit;
    private RelativeLayout lytMenu;
    private TabLayout newsTabLayout;
    private RecyclerView rc;
    private Typeface typeface;
    private LinearLayout lytMain;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private ExpandableLayout expandable_layout;
    private SmoothProgressBar lytLoading;
    private EditText edtSearch;
    private List<NewsModel> searchList = new ArrayList<>();

    private List<NewsModel> newsList = new ArrayList<>();
    //private int count = 0;

    List<NewsModel> filteredList = new ArrayList<>();
    private int totalTabsCount;

    LinearLayoutManager mLinearLayoutManagerVertical;
    WebServiceCallBackList callBackList;
    public boolean firstTimeCheck = true;



    public newsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        initView(view);
        app.check = 2;


        if (newsList.size() < 1) {
            callBackList = new WebServiceCallBackList();
            callBackList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        rc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (edtSearch.getText().toString().equals("")) {
                        //Toast.makeText(getContext(), "Last", Toast.LENGTH_LONG).show();
//                        WebServiceCallBackList callBackList = new WebServiceCallBackList();
//                        callBackList.execute();
                    }

                }
            }
        });

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");

        newsTabLayout.addTab(newsTabLayout.newTab().setText("فرهنگ و جامعه"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("گردشگری"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("عمرانی"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("اقتصادی"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("همه اخبار"));


        totalTabsCount = newsTabLayout.getTabCount();
//        referendimViewPager adapter = new referendimViewPager (getActivity().getSupportFragmentManager());
//        newsPager.setAdapter(adapter);
//        newsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(newsTabLayout));


        newsTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                newsPager.setCurrentItem(tab.getPosition());

                //newsListAdapter.selectedPosition = 0;
                filteredList = new ArrayList<>();

                if (tab.getPosition() == totalTabsCount - 1) {
                    setUpRecyclerView(newsList, false);
                } else {

                    for (int i = 0; i < newsList.size(); i++) {
                        if (newsList.get(i).Type == totalTabsCount - (tab.getPosition() + 1))
                            filteredList.add(newsList.get(i));
                    }
                    setUpRecyclerView(filteredList, true);
                }

                newsListAdapter.selectedPosition = 0;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        View root = newsTabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimary));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        changeTabsFont();

        //setUpRecyclerView();


//        lytSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                expandable_layout.expand();
//            }
//        });
        lytSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean handler = new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsTabLayout.getTabAt(4).select();
                    }
                }, 2);

                //opens search layout
                expandable_layout.toggle();
                if (!expandable_layout.isExpanded()) {
                    edtSearch.setText("");
                    if (newsList != null)
                        setUpRecyclerView(newsList, false);
                }

                edtSearch.setText("");
            }
        });


        lytSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandable_layout.toggle();
                edtSearch.setText("");
                if (newsList != null)
                    setUpRecyclerView(newsList, false);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0)
                    searchList.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    if (newsList != null)
                        setUpRecyclerView(newsList, false);
                    else
                        Toast.makeText(getContext(), "هیچ موردی موجود نمی باشد", Toast.LENGTH_SHORT).show();
                } else {
                    if (newsList != null) {
                        searchList.clear();
                        for (int i = 0; i < newsList.size(); i++) {
                            if (newsList.get(i).Title.contains(s)) {
                                searchList.add(newsList.get(i));
                            }
                        }
                        if (searchList.size() > 0)
                            setUpRecyclerView(searchList, true);
                        else {
                            Toast.makeText(getContext(), "هیچ موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            setUpRecyclerView(searchList, true);
                        }
                    } else // homeworkList is empty
                        Toast.makeText(getContext(), "هیچ موردی موجود نمی باشد", Toast.LENGTH_SHORT).show();
                }
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
        lytSearch = (RelativeLayout) view.findViewById(R.id.lytSearch);
        lytMenu = (RelativeLayout) view.findViewById(R.id.lytMenu);
        lytSearchCancel = (RelativeLayout) view.findViewById(R.id.lytSearchCancel);
        lytSearchSubmit = (RelativeLayout) view.findViewById(R.id.lytSearchSubmit);
        newsTabLayout = (TabLayout) view.findViewById(R.id.newsTabLayout);
        rc = (RecyclerView) view.findViewById(R.id.rc);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        expandable_layout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
        edtSearch = view.findViewById(R.id.edt_search);
        lytLoading = view.findViewById(R.id.lytLoading);
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) newsTabLayout.getChildAt(0);
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

    private void setUpRecyclerView(List<NewsModel> list, boolean searchFlag) {

        newsListAdapter adapter = new newsListAdapter(getContext(), list, searchFlag);
        rc.setAdapter(adapter);

        mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(mLinearLayoutManagerVertical);


// Assign the RecyclerView's LayoutManager.
        this.rc.setLayoutManager(mLinearLayoutManagerVertical);
// Scroll the RecyclerView to the Index.
        mLinearLayoutManagerVertical.smoothScrollToPosition(this.rc, new RecyclerView.State(), newsListAdapter.selectedPosition);



    }

    private class WebServiceCallBackList extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private List<NewsModel> tmpList;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            tmpList = new ArrayList<>();
            lytLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            tmpList = webService.getNews(app.isInternetOn(), 0);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.GONE);

            if (tmpList != null) {

                boolean handler = new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsTabLayout.getTabAt(4).select();
                    }
                }, 2);

                if (tmpList.size() > 0) {
                    lytMain.setVisibility(View.VISIBLE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    newsList.addAll(tmpList);
                    setUpRecyclerView(newsList, false);
                } else if (newsList.size() < 1) {
                    //Toast.makeText(getApplicationContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();

                    lytMain.setVisibility(View.GONE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);


                }

            } else {
                //Toast.makeText(getApplicationContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();

                lytMain.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);
                lytEmpty.setVisibility(View.GONE);

            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        WebServiceCallBackList callBackList = new WebServiceCallBackList();
//        callBackList.execute();
        //setUpRecyclerView(newsList);

        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsTabLayout.getTabAt(4).select();
            }
        }, 2);

        if (!firstTimeCheck) {
            lytLoading.setVisibility(View.GONE);
            lytMain.setVisibility(View.VISIBLE);
        } else {
            firstTimeCheck = false;
        }
    }

    public static void clearAsyncTask(AsyncTask<?, ?, ?> asyncTask) {
        if (asyncTask != null) {
            if (!asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
            asyncTask = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        Toast.makeText(getContext(), "stop", Toast.LENGTH_LONG).show();
        if(callBackList != null && callBackList.getStatus() == AsyncTask.Status.RUNNING)
            callBackList.cancel(true);
    }
}
