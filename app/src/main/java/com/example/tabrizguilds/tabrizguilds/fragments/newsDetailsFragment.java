package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.commentsActivity;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.profileActivity;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.like.LikeButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsDetailsFragment extends Fragment {


    private RelativeLayout relativeBack;
    private TextView txtNewsTitle;
    private TextView txtLikeCount;
    private ImageView imgShare;
    private LikeButton imgLike;
    private TextView txtDate;
    private RelativeLayout lytNewsLink;
    private ImageView imgNews;
    private TextView txtNewsBody;
    private LinearLayout lytNewsComments;

    private int id, type, likeCount, date;
    private String img, title, body, newsLink;

    private SharedPreferences prefs;
    int idUser;
    private Typeface typeface;

    private boolean CanLike = true;

    public newsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");

        Bundle args = getArguments();
        id = args.getInt("ID");
        type = args.getInt("Type");
        likeCount = args.getInt("LikeCount");
        date = args.getInt("Date");
        img = args.getString("Img");
        title = args.getString("Title");
        body = args.getString("Body");
        newsLink = args.getString("NewsLink");

        initView(view);
        setViews();



        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0){
            if (prefs.getBoolean("NewsLike" + id, false))
                imgLike.setLiked(true);
            else
                imgLike.setLiked(false);
        }


        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, title + "\n" + "http://arkatech.ir/");
                startActivity(Intent.createChooser(share, "به اشتراک گذاری از طریق..."));
            }
        });

        lytNewsComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), commentsActivity.class);
                i.putExtra("IdRow", id);
                i.putExtra("MainType", 11);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });

        lytNewsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "";
                if (!newsLink.equals("") && !newsLink.equals("null")) {
                    url = newsLink;

                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getContext(), "لینک خبر موجود نمی باشد", Toast.LENGTH_LONG).show();
                }

            }
        });

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageLike(v);
            }
        });

        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relativeBack);
        txtNewsTitle = (TextView) view.findViewById(R.id.txtNewsTitle);
        txtLikeCount = (TextView) view.findViewById(R.id.txtLikeCount);
        imgShare = (ImageView) view.findViewById(R.id.imgShare);
        imgLike = (LikeButton) view.findViewById(R.id.btnLike);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        imgNews = (ImageView) view.findViewById(R.id.imgNews);
        txtNewsBody = (TextView) view.findViewById(R.id.txtNewsBody);
        lytNewsLink = (RelativeLayout) view.findViewById(R.id.lytNewsLink);
        lytNewsComments = (LinearLayout) view.findViewById(R.id.lytNewsComments);
    }

    private void setViews(){
        txtNewsBody.setTypeface(typeface);
        txtNewsTitle.setText(title);
        txtDate.setText(app.changeDateToString(date));
        txtNewsBody.setText(body);
        txtLikeCount.setText(likeCount + "");
        if (img != null)
            if (!img.equals(""))
                Glide.with(getContext()).load(app.imgMainAddr + app.newsImgAddr + img).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgNews);

    }

    private void manageLike(View view){

        if (CanLike) {

            if (idUser > 0) {

                CanLike = false;

                if (prefs.getBoolean("NewsLike" + id, false)) {
                    imgLike.setLiked(false);
                    likeCount--;
                    txtLikeCount.setText(likeCount + "");
                    WebServiceCallLikeDelete callDelete = new WebServiceCallLikeDelete();
                    callDelete.execute();
                } else {
                    imgLike.setLiked(true);
                    likeCount++;
                    txtLikeCount.setText(likeCount + "");
                    WebServiceCallLikeAdd callAdd = new WebServiceCallLikeAdd();
                    callAdd.execute();
                }

            } else {

                Snackbar snackbar = Snackbar.make(getView(), "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
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

    public class registerAction implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Intent i = new Intent(getContext(), loginActivity.class);
            startActivity(i);
        }
    }


    private class WebServiceCallLikeAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idUserLike;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            idUserLike = -1;
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(app.isInternetOn(), idUserLike, id, idUser, 1, -1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    idUserLike = Integer.parseInt(result);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("NewsLike" + id, true);
                    editor.putInt("IdUserLike" + id, idUserLike);
                    editor.apply();
                }
                else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    else if (app.context != null)
                        Toast.makeText(app.context, "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    imgLike.setLiked(false);
                    likeCount--;
                    txtLikeCount.setText(likeCount + "");
                }

            } else {
                if (getContext() != null)
                    Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                else if (app.context != null)
                    Toast.makeText(app.context, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                imgLike.setLiked(false);
                likeCount--;
                txtLikeCount.setText(likeCount + "");
            }

            CanLike = true;

        }

    }

    private class WebServiceCallLikeDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idUserLike;
//        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            idUserLike = prefs.getInt("IdUserLike" + id, -1);

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postLike(app.isInternetOn(), idUserLike, id, idUser, 0, -1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) >= 0) {
                    idUserLike = -1;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("NewsLike" + id, false);
                    editor.putInt("IdUserLike" + id, -1);
                    editor.apply();
                }
                else {
                    Toast.makeText(getContext(), "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    imgLike.setLiked(true);
                    likeCount++;
                    txtLikeCount.setText(likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                imgLike.setLiked(true);
                likeCount++;
                txtLikeCount.setText(likeCount + "");
            }

            CanLike = true;

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        idUser = prefs.getInt("UserId", -1);
        if (id > 0){
            if (prefs.getBoolean("NewsLike" + id, false))
                imgLike.setLiked(true);
            else
                imgLike.setLiked(false);
        }
    }

}
