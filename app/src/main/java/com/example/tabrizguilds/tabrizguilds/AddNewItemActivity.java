package com.example.tabrizguilds.tabrizguilds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddNewItemActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private EditText edtStartTime;
    private Spinner startDaySpinner;
    private EditText edtEndTime;
    private Spinner endDaySpinner;
    private LinearLayout lytAddFacility;
    private EditText edtName;
    private EditText edtDes;
    private EditText edtPhone;
    private EditText edtWeb;
    private Spinner categorySpinner;
    private Spinner subCategorySpinner;
    private TextView txtMapSituation;
    private LinearLayout lytSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        initView();


        // set data for spinner
        List<String> terms = new ArrayList<>();
        terms.add("روز");
        terms.add("شنبه");
        terms.add("یکشنبه");
        terms.add("دوشنبه");
        terms.add("سه شنبه");
        terms.add("چهارشنبه");
        terms.add("پنج شنبه");
        terms.add("جمعه");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, terms);
        dataAdapter.setDropDownViewResource(R.layout.spinner_text);
        startDaySpinner.setAdapter(dataAdapter); // attaching data adapter to spinner
        endDaySpinner.setAdapter(dataAdapter); // attaching data adapter to spinner


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lytAddFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewItemActivity.this, addFacilityActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.top_to_bottom);
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        edtStartTime = (EditText) findViewById(R.id.edtStartTime);
        startDaySpinner = (Spinner) findViewById(R.id.startDaySpinner);
        edtEndTime = (EditText) findViewById(R.id.edtEndTime);
        endDaySpinner = (Spinner) findViewById(R.id.endDaySpinner);
        lytAddFacility = (LinearLayout) findViewById(R.id.lytAddFacility);
        edtName = (EditText) findViewById(R.id.edtName);
        edtDes = (EditText) findViewById(R.id.edtDes);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtWeb = (EditText) findViewById(R.id.edtWeb);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        subCategorySpinner = (Spinner) findViewById(R.id.subCategorySpinner);
        txtMapSituation = (TextView) findViewById(R.id.txtMapSituation);
        lytSubmit = (LinearLayout) findViewById(R.id.lytSubmit);
    }
}
