package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.TouchImageView;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.models.HomePageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 03/01/2018.
 */

public class imageAdapter extends PagerAdapter {


    private LayoutInflater inflater;
    private Context context;
    List<Integer> pageList;

    public imageAdapter(Context context, List<Integer> pageList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.pageList = pageList;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_gallery, view, false);

        assert imageLayout != null;
        final TouchImageView imageView = (TouchImageView) imageLayout
                .findViewById(R.id.img);

        Glide.with(context).load(pageList.get(position)).into(imageView);



        return imageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}