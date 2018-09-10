package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.SubCategoryAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.driversAdapter;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.DriverModel;
import com.example.tabrizguilds.tabrizguilds.models.SubCategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {


    public SubCategoryFragment() {
        // Required empty public constructor
    }

    private int rootCategory = 0;
    TextView txtToolbar;
    RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);

        txtToolbar = view.findViewById(R.id.txtToolbar);
        recycler = view.findViewById(R.id.recycler);


        Bundle args = getArguments();
        rootCategory = args.getInt("rootCategory");
        txtToolbar.setText(args.getString("categoryName"));

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext());
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return view;
    }


    private void setUpRecyclerView(List<SubCategoryModel> list) {

        SubCategoryAdapter adapter = new SubCategoryAdapter(getContext(), list);
        recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private List<SubCategoryModel> subCategoryList;

        public DatabaseCallback(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            subCategoryList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            if (rootCategory > 0)
                subCategoryList = databaseHelper.selectSubCategoryToList(rootCategory);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (subCategoryList != null) {

                setUpRecyclerView(subCategoryList);
            }

        }

    }

}
