package com.example.tabrizguilds.tabrizguilds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class addFacilityActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private EditText edt1;
    private EditText edt2;
    private EditText edt3;
    private EditText edt4;
    private EditText edt5;
    private EditText edt6;
    private EditText edt7;
    private EditText edt8;
    private EditText edt9;
    private EditText edt10;
    private ImageView imgDelete;
    private ImageView imgAdd;
    private LinearLayout lytSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_facility);
        initView();

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
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        edt5 = (EditText) findViewById(R.id.edt5);
        edt6 = (EditText) findViewById(R.id.edt6);
        edt7 = (EditText) findViewById(R.id.edt7);
        edt8 = (EditText) findViewById(R.id.edt8);
        edt9 = (EditText) findViewById(R.id.edt9);
        edt10 = (EditText) findViewById(R.id.edt10);
        imgDelete = (ImageView) findViewById(R.id.imgDelete);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        lytSubmit = (LinearLayout) findViewById(R.id.lytSubmit);
    }
}
