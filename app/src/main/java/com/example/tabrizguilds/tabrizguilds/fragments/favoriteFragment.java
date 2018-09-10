package com.example.tabrizguilds.tabrizguilds.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.eventsListAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.restaurantListAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.EventModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;

public class favoriteFragment extends Fragment {

    private RelativeLayout lytSearch;
    private RelativeLayout lytBack;
    private LinearLayout lytMain;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private RecyclerView recycleEvents, recyclePlaces;

    private List<EventModel> eventList;
    private List<PlacesModel> placesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(view);

        recycleEvents.setNestedScrollingEnabled(true);
        recyclePlaces.setNestedScrollingEnabled(true);

        DatabaseCallback databaseCallback = new DatabaseCallback(getActivity());
        databaseCallback.execute();

        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });



        return view;
    }




    private void initView(View view) {
        lytSearch = (RelativeLayout) view.findViewById(R.id.lytSearch);
        lytBack = (RelativeLayout) view.findViewById(R.id.lytBack);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        recyclePlaces = view.findViewById(R.id.recyclePlaces);
        recycleEvents = view.findViewById(R.id.recycleEvents);
    }

    private void setUpRecyclerViewEvent(List<EventModel> list) {

        eventsListAdapter adapter2 = new eventsListAdapter(getContext(), list, false);
        recycleEvents.setAdapter(adapter2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recycleEvents.setLayoutManager(gridLayoutManager);
    }

    private void setUpRecyclerViewPlaces(List<PlacesModel> placesList) {

        restaurantListAdapter adapter = new restaurantListAdapter(getContext(), placesList, "");
        recyclePlaces.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclePlaces.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;

        public DatabaseCallback(Context context) {
            this.context = context;
            //this.tblName = tblName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventList = new ArrayList<>();
            placesList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Eating"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Shoppings"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Rests"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Tourisms"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Culturals"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Transports"));
            //placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Offices"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Medicals"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Services"));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (eventList != null) {
                if (eventList.size() > 0) {
                    setUpRecyclerViewEvent(eventList);
                }
            }
            if (placesList != null) {
                if (placesList.size() > 0) {
                    List<PlacesModel> tmp = placesList;
                    setUpRecyclerViewPlaces(tmp);
                }
            }
            if (eventList != null && placesList != null) {
                if (eventList.size() == 0 && placesList.size() == 0) {
                    lytMain.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }
            }


        }

    }


}
