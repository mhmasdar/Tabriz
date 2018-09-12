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
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 26/11/2017.
 */

public class detailsSliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    //private List<Integer> images = new ArrayList<>();
    List<ImgModel> imageList = new ArrayList<>();

    public detailsSliderAdapter(Context context, List<ImgModel> imageList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.imageList = imageList;
//        images.add(R.drawable.test2);
//        images.add(R.drawable.test2);
//        images.add(R.drawable.test2);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {

        if (imageList.size() > 3)
            return 3;
        else
            return imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.organization_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);

//        //set image dark
        imageView.setColorFilter(Color.rgb(183, 183, 183), android.graphics.PorterDuff.Mode.MULTIPLY);

        if (imageList.get(position).Name != null)
            if (!imageList.get(position).Name.equals(""))
                Glide.with(context).load(app.imgMainAddr + app.placesImgAddr + imageList.get(position).Name).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);


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
