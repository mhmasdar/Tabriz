package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.fragments.detailsFragment;
import com.example.tabrizguilds.tabrizguilds.models.DriverModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 19/12/2017.
 */

public class driversAdapter extends RecyclerView.Adapter<driversAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<DriverModel> driversList = new ArrayList<>();

    public driversAdapter(Context context, List<DriverModel> driversList ) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.driversList = driversList;
    }

    @Override
    public driversAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_drivers_list, parent, false);
        driversAdapter.myViewHolder holder = new driversAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(driversAdapter.myViewHolder holder, int position) {

        final DriverModel currentObj = driversList.get(position);
        holder.setData(currentObj, position);

        holder.txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentObj.Mobile != null && !currentObj.Mobile.equals("") && !currentObj.Mobile.equals("null")) {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.fromParts("tel","0" + currentObj.Mobile, null));
                    context.startActivity(intentCall);
                }
                else
                    Toast.makeText(context, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return driversList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtCarModel;
        private TextView txtCarColor;
        private TextView txtPlate;
        private TextView txtCall;
        private TextView txtPath;
        private ImageView imgTitle;

        int position;
        public DriverModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCarModel = (TextView) itemView.findViewById(R.id.txtCarModel);
            txtCarColor = (TextView) itemView.findViewById(R.id.txtCarColor);
            txtPlate = (TextView) itemView.findViewById(R.id.txtPlate);
            txtCall = (TextView) itemView.findViewById(R.id.txtCall);
            txtPath = (TextView) itemView.findViewById(R.id.txtPath);
            imgTitle = (ImageView) itemView.findViewById(R.id.imgTitle);

        }

        private void setData(DriverModel current, int position) {

            this.txtName.setText(current.Name + " " + current.LName);
            this.txtCarModel.setText(current.Model);
            if (current.Img != null)
                if (!current.Img.equals(""))
                    Glide.with(context).load(app.imgMainAddr + app.driverImgAddr + current.Img).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTitle);

            this.txtCarColor.setText("رنگ: " + current.Color);
            this.txtPlate.setText(current.Plate);
            this.txtPath.setText(current.Direction);

            this.position = position;
            this.current = current;

        }
    }
}