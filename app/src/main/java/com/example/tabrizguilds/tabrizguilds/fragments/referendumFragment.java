package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.competitionsAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.referendumAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.referendumAdapterQuestion;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.ReferendumModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class referendumFragment extends Fragment {


    private SmoothProgressBar lytLoading;
    private LinearLayout lytMain;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private TextView txtCompetitionTitle;
    private TextView txtSend;
    private TextView txtAward;
    private List<ReferendumModel> referendumList;
    private List<Integer> idQuestions;
    private SharedPreferences prefs;
    int idUser, idReferendum;
    boolean isAnsweredRef;
    private WebServiceCallAnswers callBackAnswer;
    private WebServiceCallBack webServiceCallBack;

    public referendumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referendum, container, false);
        initView(view);

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);

        webServiceCallBack = new WebServiceCallBack();
        webServiceCallBack.execute();


        //setUpRecyclerView();

        lytQuestionSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser > 0) {
                    if (!prefs.getBoolean("IsAnsweredRef" + idReferendum, false)) {

                        if (referendumAdapterQuestion.answers.size() != 0) {
                            callBackAnswer = new WebServiceCallAnswers();
                            callBackAnswer.execute();
                        }
                        else{
                            Toast.makeText(getContext(), "هیچ گزینه ای انتخاب نکرده اید", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "نظر شما برای این نظرسنجی قبلا ثبت شده", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent intent = new Intent(getContext(), loginActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.activity_enter, R.anim.stay);
                }
            }
        });

        return view;
    }

    private void initView(View view) {
        lytLoading = (SmoothProgressBar) view.findViewById(R.id.lytLoading);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        txtCompetitionTitle = (TextView) view.findViewById(R.id.txtCompetitionTitle);
        txtSend = (TextView) view.findViewById(R.id.txtSend);
        txtAward = (TextView) view.findViewById(R.id.txtAward);
        //repetitiveTitle = view.findViewById(R.id.repetitiveTitle);

    }

    private void setUpRecyclerView(List<ReferendumModel> list) {

        referendumAdapterQuestion adapter = new referendumAdapterQuestion(getContext(), list);
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void setUpRecyclerViewResult(List<ReferendumModel> list) {

        referendumAdapter adapter = new referendumAdapter(getContext(), list);
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            referendumList = new ArrayList<>();
            idQuestions = new ArrayList<>();
            webService = new WebService();
            lytLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            referendumList = webService.getReferendoms(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (referendumList != null) {

                if (referendumList.size() > 0) {
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    txtCompetitionTitle.setVisibility(View.VISIBLE);
                    if (referendumList.get(0).award != null)
                        if (!referendumList.get(0).award.equals("null") && !referendumList.get(0).award.equals("")) {
                            txtAward.setText("جوایز:" + referendumList.get(0).award);
                            txtAward.setVisibility(View.VISIBLE);
                        }
                    idReferendum = referendumList.get(0).id;
                    for (int i = 0; i < referendumList.size(); i++)
                        idQuestions.add(referendumList.get(i).idQuestion);
                    isAnsweredRef = prefs.getBoolean("IsAnsweredRef" + idReferendum, false);

                    if (idUser > 0) {
                        if (prefs.getBoolean("IsAnsweredRef" + idReferendum, false)) {
                            txtSend.setText("قبلا شرکت کردین");
                            txtCompetitionTitle.setText(referendumList.get(0).title);
                            txtCompetitionTitle.setVisibility(View.VISIBLE);
                            //repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                            //lytRepetitive.setVisibility(View.VISIBLE);
                            lytLoading.setVisibility(View.INVISIBLE);
                            lytMain.setVisibility(View.VISIBLE);
                            lytEmpty.setVisibility(View.GONE);
                            setUpRecyclerViewResult(referendumList);
                        } else {
                            txtSend.setText("ثبت پاسخ ها");
                            setUpRecyclerView(referendumList);
                            lytMain.setVisibility(View.VISIBLE);
                            lytDisconnect.setVisibility(View.GONE);
                            lytEmpty.setVisibility(View.GONE);

                        }
                    } else {
                        txtSend.setText("ثبت نام/ورود");
                        setUpRecyclerView(referendumList);
                        lytMain.setVisibility(View.VISIBLE);
                        lytDisconnect.setVisibility(View.GONE);
                        lytEmpty.setVisibility(View.GONE);

                    }


                } else if (referendumList.size() < 1) {

                    lytMain.setVisibility(View.GONE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {

                lytMain.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);
                lytEmpty.setVisibility(View.GONE);
            }

        }

    }

    private class WebServiceCallAnswers extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        List<Integer> answers;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            answers = referendumAdapterQuestion.answers;
            lytLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postCompetitionAnswers(app.isInternetOn(), idQuestions, idUser, answers);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (result != null) {

                if (Integer.parseInt(result) == 1) {

                    Toast.makeText(getContext(), "با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کردین");
//                    txtCompetitionTitle.setText(referendumList.get(0).Name);
                    //lytRepetitive.setVisibility(View.GONE);
//                    lytLoading.setVisibility(View.GONE);
//                    lytMain.setVisibility(View.VISIBLE);
//                    lytEmpty.setVisibility(View.GONE);
//                    setUpRecyclerViewResult(referendumList);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnsweredRef" + idReferendum, true);
                    editor.apply();

                    WebServiceCallBack callBack = new WebServiceCallBack();
                    callBack.execute();

                } else if (Integer.parseInt(result) == 0) {
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(result) == -1) {
                    Toast.makeText(getContext(), "قبلا شرکت کردین", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کردین");
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    //repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                    //lytRepetitive.setVisibility(View.VISIBLE);
                    txtCompetitionTitle.setVisibility(View.VISIBLE);
                    lytLoading.setVisibility(View.INVISIBLE);
                    lytMain.setVisibility(View.VISIBLE);
                    lytEmpty.setVisibility(View.GONE);
                    setUpRecyclerViewResult(referendumList);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnsweredRef" + idReferendum, true);
                    editor.apply();
                }


            } else {
                Toast.makeText(getContext(), "مشکل در ارتباط با سرور", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);

//        if (referendumList != null)
//            setUpRecyclerView(referendumList);

        if (idUser > 0) {
            if (idReferendum > 0) {
                if (prefs.getBoolean("IsAnsweredRef" + idReferendum, false)) {
                    txtSend.setText("قبلا شرکت کردین");
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    txtCompetitionTitle.setVisibility(View.VISIBLE);
                    //repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                    //lytRepetitive.setVisibility(View.VISIBLE);
                    lytLoading.setVisibility(View.INVISIBLE);
                    lytMain.setVisibility(View.VISIBLE);
                    lytEmpty.setVisibility(View.GONE);
                    setUpRecyclerViewResult(referendumList);
                } else {
                    txtSend.setText("ثبت پاسخ ها");
//                    lytMain.setVisibility(View.VISIBLE);
//                    lytDisconnect.setVisibility(View.GONE);
//                    lytEmpty.setVisibility(View.GONE);

                }
            }
        } else {
            txtSend.setText("ثبت نام/ورود");
//            lytMain.setVisibility(View.VISIBLE);
//            lytDisconnect.setVisibility(View.GONE);
//            lytEmpty.setVisibility(View.GONE);

        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(callBackAnswer != null && callBackAnswer.getStatus() == AsyncTask.Status.RUNNING)
            callBackAnswer.cancel(true);

        if(webServiceCallBack != null && webServiceCallBack.getStatus() == AsyncTask.Status.RUNNING)
            webServiceCallBack.cancel(true);
    }
}
