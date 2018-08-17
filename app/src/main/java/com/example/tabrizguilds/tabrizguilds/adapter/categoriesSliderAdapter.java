package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class categoriesSliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Integer> images = new ArrayList<>();

    public categoriesSliderAdapter(Context context, List<Integer> images) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.images = images;
//        images.add(R.drawable.test4);
//        images.add(R.drawable.test2);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.organization_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);

//        //set image dark
        imageView.setColorFilter(Color.rgb(200, 200, 200), android.graphics.PorterDuff.Mode.MULTIPLY);

        Glide.with(context).load(images.get(position)).into(imageView);


        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click listener
            }
        });

        return imageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}