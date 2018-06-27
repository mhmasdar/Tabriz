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
    private ImageView img2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        initView();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);



        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Glide.with(this).load(R.drawable.ic_intro1).into(img1);
        Glide.with(this).load(R.drawable.intro2).into(img2);
//        Glide.with(this).load(R.drawable.intro3).into(img3);
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

    }
}
