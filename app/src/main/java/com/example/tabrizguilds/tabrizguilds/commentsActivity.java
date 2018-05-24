package com.example.tabrizguilds.tabrizguilds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.adapter.commentsAdapter;
import com.example.tabrizguilds.tabrizguilds.models.CommentModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class commentsActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private RecyclerView commentsRecycler;
    private LinearLayout lytLoginComment;
    private LinearLayout lytSubmitComment;
    private LinearLayout lytSendComments;
    private EditText edtComment;
    private LinearLayout lytMain;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private LinearLayout lytCommentReply;
    private ImageView btnCloseReply;
    private TextView txtReplyName, txtReplyBody;
    private SmoothProgressBar lytLoading;

    private List<CommentModel> commentList;
    private int mainType;
    private int idRow;

    private boolean canSend = true;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        idRow = getIntent().getIntExtra("IdRow", 0);
        mainType = getIntent().getIntExtra("MainType", 0);

        initView();


        WebServiceCallBackList webServiceCallList= new WebServiceCallBackList();
        webServiceCallList.execute();

        //setUpRecyclerView();

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lytSendComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("MYPREFS", 0);
                int idUser = prefs.getInt("UserId", -1);

                if (canSend) {

                    if (idUser > 0) {
                        if (!edtComment.getText().toString().equals("")) {
                            canSend = false;
                            WebServiceCallBackAdd callBackAdd = new WebServiceCallBackAdd(idUser);
                            callBackAdd.execute();
                        } else {
                            Toast.makeText(getApplicationContext(), "لطفا متن پیام را وارد کنید", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Snackbar snackbar = Snackbar.make(lytMain, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                        snackbar.setAction("ثبت نام", new registerAction());

                        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                        textView.setLayoutParams(parms);
                        textView.setGravity(Gravity.LEFT);
                        snackbar.setActionTextColor(getResources().getColor(R.color.yellow));
                        snackbar.show();

                    }
                }

            }
        });
    }

    public class registerAction implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            // Code to undo the user's last action
            Intent i = new Intent(commentsActivity.this, loginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
        }
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        commentsRecycler = (RecyclerView) findViewById(R.id.commentsRecycler);
        lytLoginComment = (LinearLayout) findViewById(R.id.lytLoginComment);
        lytSubmitComment = (LinearLayout) findViewById(R.id.lytSubmitComment);
        lytSendComments = (LinearLayout) findViewById(R.id.lytSendComments);
        edtComment = (EditText) findViewById(R.id.edtComment);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        lytCommentReply = (LinearLayout) findViewById(R.id.lytCommentReply);
        btnCloseReply = (ImageView) findViewById(R.id.btnCloseReply);
        txtReplyName = findViewById(R.id.txtReplyName);
        txtReplyBody = findViewById(R.id.txtReplyBody);
        lytLoading = findViewById(R.id.lytLoading);
    }

    private void setUpRecyclerView(List<CommentModel> list) {

        commentsAdapter adapter = new commentsAdapter(commentsActivity.this, lytCommentReply, btnCloseReply, txtReplyBody, txtReplyName, list);
        commentsRecycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        commentsRecycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceCallBackList extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            commentList = new ArrayList<>();
            webService = new WebService();
            lytLoading.setVisibility(View.VISIBLE);
            lytDisconnect.setVisibility(View.GONE);
            lytEmpty.setVisibility(View.GONE);
            lytMain.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            commentList = webService.getComments(app.isInternetOn(), idRow, mainType);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (commentList != null) {

                if (commentList.size() > 0) {
                    lytMain.setVisibility(View.VISIBLE);
                    setUpRecyclerView(commentList);
                }
                else {
                    //Toast.makeText(getApplicationContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {
                //Toast.makeText(getApplicationContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                lytDisconnect.setVisibility(View.VISIBLE);

            }

        }

    }

    private class WebServiceCallBackAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idUser;
        String commentBody;

        public WebServiceCallBackAdd(int idUser){
            this.idUser = idUser;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            commentBody = edtComment.getText().toString();
            lytLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postComment(app.isInternetOn(), idRow, idUser, commentsAdapter.idAnswer, mainType, 0, commentBody, Integer.parseInt(app.getDate()));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (result != null) {

                if (Integer.parseInt(result) > 0){
                    Toast.makeText(getApplicationContext(), "با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                    edtComment.setText("");
                    lytCommentReply.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "ناموفق", Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(getApplicationContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

            canSend = true;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
    }
}
