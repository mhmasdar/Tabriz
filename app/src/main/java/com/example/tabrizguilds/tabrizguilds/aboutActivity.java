package com.example.tabrizguilds.tabrizguilds;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class aboutActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private ImageView imgArka, imgAbout1, imgAbout2, imgAbout3, imgAbout4;
    private String url = "http://arkatech.ir";
    private LinearLayout lytCall;
    private TextView txtWebSite;
    private JustifiedTextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        txt1.setTypeface(typeface);

        Glide.with(this).load(R.drawable.arka).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgArka);
        Glide.with(this).load(R.drawable.ic_about1).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAbout1);
        Glide.with(this).load(R.drawable.ic_about2).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAbout2);
        Glide.with(this).load(R.drawable.ic_about3).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAbout3);
        Glide.with(this).load(R.drawable.ic_about4).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAbout4);

        imgArka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        txtWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.fromParts("tel", "04133303866", null));
                startActivity(intentCall);
            }
        });


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        imgArka = (ImageView) findViewById(R.id.imgArka);
        imgAbout1 = (ImageView) findViewById(R.id.imgAbout1);
        imgAbout2 = (ImageView) findViewById(R.id.imgAbout2);
        imgAbout3 = (ImageView) findViewById(R.id.imgAbout3);
        imgAbout4 = (ImageView) findViewById(R.id.imgAbout4);
        lytCall = (LinearLayout) findViewById(R.id.lytCall);
        txtWebSite = (TextView) findViewById(R.id.txtWebSite);
        txt1 = (JustifiedTextView) findViewById(R.id.txt1);
    }
}
