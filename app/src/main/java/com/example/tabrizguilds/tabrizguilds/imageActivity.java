package com.example.tabrizguilds.tabrizguilds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class imageActivity extends Activity {

    private TouchImageView img;
    private CircularProgressBar lytLoading;

    String imageName, imageAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = (TouchImageView) findViewById(R.id.img);
        lytLoading = (CircularProgressBar) findViewById(R.id.lytLoading);

        imageName = getIntent().getStringExtra("ImgName");
        imageAddress = getIntent().getStringExtra("ImgAddress");

        if (imageName != null)
            if (!imageName.equals(""))
                Glide.with(this).load(app.imgMainAddr + imageAddress + imageName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bm, GlideAnimation<? super Bitmap> glideAnimation) {
                        img.setImageBitmap(bm);
                        lytLoading.setVisibility(View.GONE);
                        img.setVisibility(View.VISIBLE);
                    }
                });
    }

}
