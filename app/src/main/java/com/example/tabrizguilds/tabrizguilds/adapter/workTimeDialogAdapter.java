package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.models.FacilityModel;

import java.util.List;

public class workTimeDialogAdapter extends RecyclerView.Adapter<workTimeDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public workTimeDialogAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public workTimeDialogAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_worktime_list, parent, false);
        workTimeDialogAdapter.myViewHolder holder = new workTimeDialogAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(workTimeDialogAdapter.myViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        myViewHolder(View itemView) {
            super(itemView);
        }
    }
}