package com.example.tabrizguilds.tabrizguilds.fragments.categories;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.eventsListAdapter;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.EventModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsFragment extends Fragment {


    private RelativeLayout relativeBack;
    private RelativeLayout lytSearch;
    private RelativeLayout lytSearchCancel;
    private RecyclerView recycler;
    private ExpandableLayout expandable_layout;
    private LinearLayout lytMain, lytEmpty, lytDisconnect;
    private EditText edt_search;
    private DatabaseCallback databaseCallback;
    List<EventModel> searchList = new ArrayList<>();

    private List<EventModel> eventList;

    public eventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        initView(view);

//        if (app.isInternetOn()) {
//            WebServiceCallBack webServiceCallBack = new WebServiceCallBack();
//            webServiceCallBack.execute();
//        } else {
//            lytMain.setVisibility(View.GONE);
//            lytEmpty.setVisibility(View.GONE);
//            lytDisconnect.setVisibility(View.VISIBLE);
//        }

        //setUpRecyclerView();

        databaseCallback = new DatabaseCallback(getContext(), "Tbl_Events");
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        lytSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens search layout
                expandable_layout.toggle();
                if (!expandable_layout.isExpanded()) {
                    edt_search.setText("");
                    if (eventList != null)
                        setUpRecyclerView(eventList);
                }

                edt_search.setText("");
            }
        });


        lytSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandable_layout.toggle();
                edt_search.setText("");
                if (eventList != null)
                    setUpRecyclerView(eventList);
            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {

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
                    if (eventList != null)
                        setUpRecyclerView(eventList);
                    else
                        Toast.makeText(getContext(), "هیچ موردی موجود نمی باشد", Toast.LENGTH_SHORT).show();
                } else {
                    if (eventList != null) {
                        searchList.clear();
                        for (int i = 0; i < eventList.size(); i++) {
                            if (eventList.get(i).name.contains(s)) {
                                searchList.add(eventList.get(i));
                            }
                        }
                        if (searchList.size() > 0)
                            setUpRecyclerView(searchList);
                        else {
                            Toast.makeText(getContext(), "هیچ موردی یافت نشد", Toast.LENGTH_SHORT).show();
                            setUpRecyclerView(searchList);
                        }
                    } else
                        Toast.makeText(getContext(), "هیچ موردی موجود نمی باشد", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        lytSearch = (RelativeLayout) view.findViewById(R.id.lytSearch);
        lytSearchCancel = (RelativeLayout) view.findViewById(R.id.lytSearchCancel);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        expandable_layout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);
        lytEmpty = view.findViewById(R.id.lytEmpty);
        edt_search = view.findViewById(R.id.edt_search);
    }

    private void setUpRecyclerView(List<EventModel> list) {

        eventsListAdapter adapter = new eventsListAdapter(getContext(), list, true);
        recycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler.setLayoutManager(gridLayoutManager);
    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;

        public DatabaseCallback(Context context, String tblName) {
            this.context = context;
            this.tblName = tblName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

//            eventList = databaseHelper.selectAllEventsToList(tblName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (eventList != null)
                if (eventList.size() > 0)
                    setUpRecyclerView(eventList);

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(databaseCallback != null && databaseCallback.getStatus() == AsyncTask.Status.RUNNING)
            databaseCallback.cancel(true);
    }

}
