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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.competitionsAdapter;
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
public class competitionFragment extends Fragment {

    private SmoothProgressBar lytLoading;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytMain;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect, lytRepetitive;
    private TextView txtCompetitionTitle, txtSend;
    private TextView repetitiveTitle;
    private TextView txtAward;
    private WebServiceCallBack webServiceCallBack;
    private List<ReferendumModel> referendumList;
    private List<Integer> idQuestions;
    private SharedPreferences prefs;
    private int idUser, idCompetition;
    private boolean isAnsweredCompt;
    private WebServiceCallAnswers callBackAnswer;

    public competitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_competition, container, false);
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
                    if (!prefs.getBoolean("IsAnswered" + idCompetition, false)) {
                        if (competitionsAdapter.answers.size() != 0) {
                            callBackAnswer = new WebServiceCallAnswers();
                            callBackAnswer.execute();
                        } else {
                            Toast.makeText(getContext(), "هیچ گزینه ای انتخاب نکرده اید", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "قبلا شرکت کرده اید", Toast.LENGTH_LONG).show();
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

    private void setUpRecyclerView(List<ReferendumModel> list) {

        competitionsAdapter adapter = new competitionsAdapter(getContext(), list);
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void initView(View view) {
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        txtCompetitionTitle = view.findViewById(R.id.txtCompetitionTitle);
        txtSend = view.findViewById(R.id.txtSend);
        lytLoading = view.findViewById(R.id.lytLoading);
        lytRepetitive = view.findViewById(R.id.lytRepetitive);
        repetitiveTitle = view.findViewById(R.id.repetitiveTitle);
        txtAward = view.findViewById(R.id.txtAward);
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

            referendumList = webService.getComptitions(app.isInternetOn());

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
                    txtCompetitionTitle.setVisibility(View.VISIBLE);
                    idCompetition = referendumList.get(0).id;
                    for (int i = 0; i < referendumList.size(); i++)
                        idQuestions.add(referendumList.get(i).idQuestion);
                    isAnsweredCompt = prefs.getBoolean("IsAnswered" + idCompetition, false);

                    if (idUser > 0) {
                        if (prefs.getBoolean("IsAnswered" + idCompetition, false)) {
                            txtSend.setText("قبلا شرکت کردین");
                            repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                            lytRepetitive.setVisibility(View.VISIBLE);
                            lytLoading.setVisibility(View.INVISIBLE);
                            lytMain.setVisibility(View.GONE);
                            lytEmpty.setVisibility(View.GONE);
                        } else {
                            txtSend.setText("ثبت پاسخ ها");
                            setUpRecyclerView(referendumList);
                            lytMain.setVisibility(View.VISIBLE);
                            lytDisconnect.setVisibility(View.GONE);
                            lytEmpty.setVisibility(View.GONE);
                            lytRepetitive.setVisibility(View.GONE);
                        }
                    } else {
                        txtSend.setText("ثبت نام/ورود");
                        setUpRecyclerView(referendumList);
                        lytMain.setVisibility(View.VISIBLE);
                        lytDisconnect.setVisibility(View.GONE);
                        lytEmpty.setVisibility(View.GONE);
                        lytRepetitive.setVisibility(View.GONE);
                    }


                } else if (referendumList.size() < 1) {
                    lytRepetitive.setVisibility(View.GONE);
                    lytMain.setVisibility(View.GONE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {
                lytRepetitive.setVisibility(View.GONE);
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

            lytLoading.setVisibility(View.VISIBLE);

            answers = competitionsAdapter.answers;
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
                    lytRepetitive.setVisibility(View.GONE);
                    lytLoading.setVisibility(View.INVISIBLE);
                    lytMain.setVisibility(View.VISIBLE);
                    lytEmpty.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnswered" + idCompetition, true);
                    editor.apply();

                } else if (Integer.parseInt(result) == 0) {
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(result) == -1) {
                    Toast.makeText(getContext(), "قبلا شرکت کردین", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کردین");
                    repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                    lytRepetitive.setVisibility(View.VISIBLE);
                    lytLoading.setVisibility(View.INVISIBLE);
                    lytMain.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnswered" + idCompetition, true);
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
            if (idCompetition > 0) {
                if (prefs.getBoolean("IsAnswered" + idCompetition, false)) {
                    txtSend.setText("قبلا شرکت کرده اید");
                    repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                    lytRepetitive.setVisibility(View.VISIBLE);
                    lytLoading.setVisibility(View.INVISIBLE);
                    lytMain.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                } else {
                    txtSend.setText("ثبت پاسخ ها");
//                    lytMain.setVisibility(View.VISIBLE);
//                    lytDisconnect.setVisibility(View.GONE);
//                    lytEmpty.setVisibility(View.GONE);
//                    lytRepetitive.setVisibility(View.GONE);
                }
            }
        } else {
            txtSend.setText("ثبت نام/ورود");
//            lytMain.setVisibility(View.VISIBLE);
//            lytDisconnect.setVisibility(View.GONE);
//            lytEmpty.setVisibility(View.GONE);
//            lytRepetitive.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCallBack != null && webServiceCallBack.getStatus() == AsyncTask.Status.RUNNING)
            webServiceCallBack.cancel(true);

        if (callBackAnswer != null && callBackAnswer.getStatus() == AsyncTask.Status.RUNNING)
            callBackAnswer.cancel(true);
    }
}
