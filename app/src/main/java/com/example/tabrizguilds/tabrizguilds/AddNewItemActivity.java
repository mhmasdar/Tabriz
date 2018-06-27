package com.example.tabrizguilds.tabrizguilds;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddNewItemActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private EditText edtStartTime;
    private Spinner startDaySpinner;
    private EditText edtEndTime;
    private Spinner endDaySpinner;

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

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        edtStartTime = (EditText) findViewById(R.id.edtStartTime);
        startDaySpinner = (Spinner) findViewById(R.id.startDaySpinner);
        edtEndTime = (EditText) findViewById(R.id.edtEndTime);
        endDaySpinner = (Spinner) findViewById(R.id.endDaySpinner);
    }
}
