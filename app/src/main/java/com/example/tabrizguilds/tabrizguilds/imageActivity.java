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

    int mainType;
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = (TouchImageView) findViewById(R.id.img);
        lytLoading = (CircularProgressBar) findViewById(R.id.lytLoading);

        mainType = getIntent().getIntExtra("MainType", 0);
        imageName = getIntent().getStringExtra("ImgName");

        if (imageName != null)
            if (!imageName.equals(""))
                Glide.with(this).load(app.imgMainAddr + getImgAddr(mainType) + imageName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bm, GlideAnimation<? super Bitmap> glideAnimation) {
                        img.setImageBitmap(bm);
                        lytLoading.setVisibility(View.GONE);
                        img.setVisibility(View.VISIBLE);
                    }
                });
    }

    public String getImgAddr(int type) {

        String imgAddress = "";

        switch (type) {
            case 1:
                imgAddress = app.eatingImgAddr;
                break;
            case 2:
                imgAddress = app.shoppingImgAddr;
                break;
            case 3:
                imgAddress = app.restImgAddr;
                break;
            case 4:
                imgAddress = app.tourismImgAddr;
                break;
            case 5:
                imgAddress = app.culturalImgAddr;
                break;
            case 6:
                imgAddress = app.transportImgAddr;
                break;
            case 7:
                imgAddress = app.serviceImgAddr;
                break;
            case 8:
                imgAddress = app.officeImgAddr;
                break;
            case 9:
                imgAddress = app.medicalImgAddr;
                break;
            case 10:
                imgAddress = app.eventImgAddr;
                break;
            default:
                imgAddress = "";
        }
        return imgAddress;
    }

}
