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
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.models.HomePageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 20/11/2017.
 */

public class SlidingImage_Adapter extends PagerAdapter {


    private LayoutInflater inflater;
    private Context context;
    private List<String> images = new ArrayList<>();
    private String[] names = {"حمام تاریخی کردشت", "کلیسای سنت استپانوس", "آسیاب خرابه"};
    private String[] details = {"حمامی قدیمی در شهر جلفا", "واقع در اطراف شهر", "منطقه ای بسیار زیبا در شهر جلفا"};
    List<HomePageModel> pageList;

    public SlidingImage_Adapter(Context context, List<HomePageModel> pageList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.pageList = pageList;

        images.add(app.imgMainAddr + app.homeImgAddr + pageList.get(0).image);
        images.add(app.imgMainAddr + app.homeImgAddr + pageList.get(1).image);
        images.add(app.imgMainAddr + app.homeImgAddr + pageList.get(2).image);
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
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);
        final TextView txtSliderName = (TextView) imageLayout
                .findViewById(R.id.txtSliderName);
        final TextView txtSliderDetail = (TextView) imageLayout
                .findViewById(R.id.txtSliderDetail);

        //set image dark
        imageView.setColorFilter(Color.rgb(225, 225, 225), android.graphics.PorterDuff.Mode.MULTIPLY);

        if (position == 0) {
            if (pageList.get(position).image.equals("1514498484465.jpg"))
                Glide.with(context).load(R.drawable.back3).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            else
                Glide.with(context).load(images.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
        else if (position == 1) {
            if (pageList.get(position).image.equals("1514498425965.jpg"))
                Glide.with(context).load(R.drawable.back2).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            else
                Glide.with(context).load(images.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
        else {
            if (pageList.get(position).image.equals("1514498287547.png"))
                Glide.with(context).load(R.drawable.back1).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            else
                Glide.with(context).load(images.get(position)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        }

        //set value
        txtSliderName.setText(pageList.get(position).title);
        txtSliderDetail.setText(pageList.get(position).des);

        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click listener
            }
        });

        return imageLayout;
    }

    public static final Uri getUriToResource(@NonNull Context context,
                                             @AnyRes int resId)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
