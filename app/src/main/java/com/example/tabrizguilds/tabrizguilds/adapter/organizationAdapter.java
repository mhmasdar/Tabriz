package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.fragments.detailsOfficeFragment;
import com.example.tabrizguilds.tabrizguilds.models.PhoneModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.List;

/**
 * Created by mohamadHasan on 22/12/2017.
 */

public class organizationAdapter  extends RecyclerView.Adapter<organizationAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<PhoneModel> phoneList;

    public organizationAdapter(Context context, List<PhoneModel> phoneList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.phoneList = phoneList;
    }

    @Override
    public organizationAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_organization_list, parent, false);
        organizationAdapter.myViewHolder holder = new organizationAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(organizationAdapter.myViewHolder holder, int position) {
        final PhoneModel currentObj = phoneList.get(position);
        holder.setData(currentObj, position);

        holder.lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.fromParts("tel", "0" + currentObj.phone, null));
                context.startActivity(intentCall);
            }
        });

    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtPhoneNumber;
        private LinearLayout lytCall;

        int position;
        public PhoneModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            lytCall = itemView.findViewById(R.id.lytCall);

        }

        private void setData(PhoneModel current, int position) {

            this.txtName.setText(current.name);
            this.txtPhoneNumber.setText("0" + current.phone);
            this.position = position;
            this.current = current;

        }
    }
}
