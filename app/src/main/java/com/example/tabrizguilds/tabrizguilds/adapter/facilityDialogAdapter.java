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

public class facilityDialogAdapter extends RecyclerView.Adapter<facilityDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<FacilityModel> facilityList;


    public facilityDialogAdapter(Context context, List<FacilityModel> facilityList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.facilityList = facilityList;
    }

    @Override
    public facilityDialogAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_facility_list, parent, false);
        facilityDialogAdapter.myViewHolder holder = new facilityDialogAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(facilityDialogAdapter.myViewHolder holder, int position) {
        final FacilityModel currentObj = facilityList.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFacility;
        int position;
        public FacilityModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtFacility = (TextView) itemView.findViewById(R.id.txtFacility);

        }

        private void setData(FacilityModel current, int position) {

            this.txtFacility.setText(current.Name);

            this.position = position;
            this.current = current;

        }

    }
}