package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.favoriteActivity;
import com.example.tabrizguilds.tabrizguilds.fragments.detailsFragment;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class restaurantListAdapter extends RecyclerView.Adapter<restaurantListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<PlacesModel> placesList = new ArrayList<>();
    private String tblName;
    private int lastPosition = -1;


    public restaurantListAdapter(Context context, List<PlacesModel> placesList, String tblName) {
        this.context = context;
        this.placesList = placesList;
        this.mInflater = LayoutInflater.from(context);
        this.tblName = tblName;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_restaurant_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final PlacesModel currentObj = placesList.get(position);
        holder.setData(currentObj, position);

        setAnimation(holder.itemView, position);

        //holder.rating.setRating(Float.parseFloat("2.0"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                detailsFragment fragment = new detailsFragment();
                Bundle args = new Bundle();
                args.putInt("ID", currentObj.id);

                if (context instanceof MainActivity) {
//                    MainActivity activity = (MainActivity) context;
                    args.putBoolean("isFromFaavorite", false);
                    fragment.setArguments(args);
                    FragmentTransaction ft = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                    ft.replace(R.id.container, fragment);
                    ft.addToBackStack("b");
                    ft.commit();
                } else {
                    args.putBoolean("isFromFaavorite", true);
                    fragment.setArguments(args);
                    FragmentTransaction ft = ((favoriteActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                    ft.replace(R.id.container1, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtRank;
        private TextView txtAddress;
        private TextView txtType;
        private RatingBar rating;
        private ImageView imgNews;

        int position;
        public PlacesModel current;


        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtRank = (TextView) itemView.findViewById(R.id.txtRank);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);

        }

        private void setData(PlacesModel current, int position) {

            this.rating.setRating(Float.parseFloat(current.Star + ""));
            this.txtName.setText(current.Name);
            this.txtAddress.setText(current.Address);
            //this.imgNews.setImageResource();
            if (current.image != null)
                if (!current.image.equals(""))
                    Glide.with(context).load(app.imgMainAddr + app.placesImgAddr + current.image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgNews);
            this.txtRank.setText(current.Star + "");

            this.position = position;
            this.current = current;

        }


    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}