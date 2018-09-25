package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.models.FacilityModel;

import java.util.List;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class WorkDaysDialogAdapter extends RecyclerView.Adapter<WorkDaysDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<String> workDaysList;


    public WorkDaysDialogAdapter(Context context, List<String> workDaysList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.workDaysList = workDaysList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_facility_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final String currentObj = workDaysList.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return workDaysList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFacility;
        int position;
        public String current;

        myViewHolder(View itemView) {
            super(itemView);
            txtFacility = (TextView) itemView.findViewById(R.id.txtFacility);

        }

        private void setData(String current, int position) {

            String dayName = "";

            switch (current){

                case "1":
                    dayName = "شنبه";
                    break;
                case "2":
                    dayName = "یکشنبه";
                    break;

                case "3":
                    dayName = "دوشنبه";
                    break;
                case "4":
                    dayName = "سه شنبه";
                    break;
                case "5":
                    dayName = "چهارشنبه";
                    break;
                case "6":
                    dayName = "پنج شنبه";
                    break;
                case "7":
                    dayName = "جمعه";
                    break;

            }

            this.txtFacility.setText(dayName);

            this.position = position;
            this.current = current;

        }

    }
}