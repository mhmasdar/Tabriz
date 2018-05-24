package com.example.tabrizguilds.tabrizguilds;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.services.WebService;

public class suggestionActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtTitle;
    private EditText edtBody;
    private Button btnSendSuggestion;

    Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        initView();

        btnSendSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtTitle.getText().toString().equals("") && !edtBody.getText().toString().equals("")){
                    WebServiceCallBack callBack = new WebServiceCallBack();
                    callBack.execute();
                }
                else{
                    Toast.makeText(getApplicationContext(), "عنوان و متن پیام را وارد کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtBody = (EditText) findViewById(R.id.edtBody);
        btnSendSuggestion = (Button) findViewById(R.id.btnSendSuggestion);

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String name, email, title, body, date;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            webService = new WebService();

            dialog2 = new Dialog(suggestionActivity.this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_waiting);
            dialog2.setCancelable(true);
            dialog2.setCanceledOnTouchOutside(true);
            dialog2.show();

            name = edtName.getText().toString();
            title = edtTitle.getText().toString();
            body = edtBody.getText().toString();
            email = edtEmail.getText().toString();
            date = app.getDate();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postSuggestion(app.isInternetOn(), name, email, title, body, date);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog2.dismiss();

            if (result != null) {

                if (Integer.parseInt(result) > 0) {

                    Toast.makeText(getApplicationContext(), "با موفقیت ارسال شد", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }



}
