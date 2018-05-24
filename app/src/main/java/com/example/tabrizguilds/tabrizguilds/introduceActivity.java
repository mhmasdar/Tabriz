package com.example.tabrizguilds.tabrizguilds;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class introduceActivity extends AppCompatActivity {


    private RelativeLayout relativeBack;
    private ImageView img1;
    private JustifiedTextView txt1;
    private JustifiedTextView txt2;
    private ImageView img2, img3;
    private JustifiedTextView txt3;
    private JustifiedTextView txt4;
    private JustifiedTextView txt5;
    private JustifiedTextView txt6;
    private JustifiedTextView txt7;
    private JustifiedTextView txt8;
    private JustifiedTextView txt9;
    private JustifiedTextView txt10;
    private JustifiedTextView txt11;
    private JustifiedTextView txt12;
    private JustifiedTextView txt13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        initView();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        txt3.setTypeface(typeface);
        txt4.setTypeface(typeface);
        txt5.setTypeface(typeface);
        txt6.setTypeface(typeface);
        txt7.setTypeface(typeface);
        txt8.setTypeface(typeface);
        txt9.setTypeface(typeface);
        txt10.setTypeface(typeface);
        txt11.setTypeface(typeface);
        txt12.setTypeface(typeface);
        txt13.setTypeface(typeface);


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Glide.with(this).load(R.drawable.ic_intro1).into(img1);
        Glide.with(this).load(R.drawable.intro2).into(img2);
        Glide.with(this).load(R.drawable.intro3).into(img3);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        img1 = (ImageView) findViewById(R.id.img1);
        txt1 = (JustifiedTextView) findViewById(R.id.txt1);
        txt2 = (JustifiedTextView) findViewById(R.id.txt2);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        txt3 = (JustifiedTextView) findViewById(R.id.txt3);
        txt4 = (JustifiedTextView) findViewById(R.id.txt4);
        txt5 = (JustifiedTextView) findViewById(R.id.txt5);
        txt6 = (JustifiedTextView) findViewById(R.id.txt6);
        txt7 = (JustifiedTextView) findViewById(R.id.txt7);
        txt8 = (JustifiedTextView) findViewById(R.id.txt8);
        txt9 = (JustifiedTextView) findViewById(R.id.txt9);
        txt10 = (JustifiedTextView) findViewById(R.id.txt10);
        txt11 = (JustifiedTextView) findViewById(R.id.txt11);
        txt12 = (JustifiedTextView) findViewById(R.id.txt12);
        txt13 = (JustifiedTextView) findViewById(R.id.txt13);
    }
}
