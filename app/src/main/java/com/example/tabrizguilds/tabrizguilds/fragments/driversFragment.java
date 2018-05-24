package com.example.tabrizguilds.tabrizguilds.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.driversAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.eventsListAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.models.DriverModel;
import com.example.tabrizguilds.tabrizguilds.models.EventModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class driversFragment extends Fragment {


    private RelativeLayout relativeBack;
    private LinearLayout lytMain;
    private RecyclerView recycler;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private SmoothProgressBar lytLoading;

    private List<DriverModel> driversList;
    int idRow;

    public driversFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);
        initView(view);

        Bundle args = getArguments();
        idRow = args.getInt("IdRow");

        WebServiceCallBack webServiceCallBack = new WebServiceCallBack();
        webServiceCallBack.execute();

        //setUpRecyclerView();

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytLoading = view.findViewById(R.id.lytLoading);
    }


    private void setUpRecyclerView(List<DriverModel> list) {

        driversAdapter adapter = new driversAdapter(getContext(), list);
        recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            driversList = new ArrayList<>();
            webService = new WebService();
            lytLoading.setVisibility(View.VISIBLE);
            lytDisconnect.setVisibility(View.GONE);
            lytEmpty.setVisibility(View.GONE);
            lytMain.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            driversList = webService.getDrivers(app.isInternetOn(), idRow);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (driversList != null) {

                if (driversList.size() > 0){
                    setUpRecyclerView(driversList);
                    lytMain.setVisibility(View.VISIBLE);

                }

                else if (driversList.size() < 1) {
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {

                lytDisconnect.setVisibility(View.VISIBLE);

            }

        }

    }

}
