package com.example.tabrizguilds.tabrizguilds;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.tabrizguilds.tabrizguilds.fragments.favoriteFragment;

public class favoriteActivity extends AppCompatActivity {

    private RelativeLayout container;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();


        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container1, new favoriteFragment());
        ft.commit();
    }

    private void initView() {
        container = (RelativeLayout) findViewById(R.id.container1);
    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }
}
