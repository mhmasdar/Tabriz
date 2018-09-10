package com.example.tabrizguilds.tabrizguilds;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.fragments.supportFragment;
import com.example.tabrizguilds.tabrizguilds.introPage.IntroActivity;
import com.example.tabrizguilds.tabrizguilds.models.LogoModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT;
    private SharedPreferences prefs;
    private WebServiceCallBack callBack;
    private WebServiceLogo callBackLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("global");

        if (app.isInternetOn())
            SPLASH_TIME_OUT = 1000;
        else
            SPLASH_TIME_OUT = 4000;



        prefs = getApplicationContext().getSharedPreferences("login", 0);


        callBackLogo = new WebServiceLogo();
        callBackLogo.execute();

    }

    // timer of splash
    public void setUpTimer() {


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                callBack = new WebServiceCallBack();
                callBack.execute();


            }
        }, SPLASH_TIME_OUT);
    }


    private class WebServiceLogo extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        LogoModel logoModel;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            logoModel = new LogoModel();
        }

        @Override
        protected Void doInBackground(Object... params) {

            logoModel = webService.getLogo(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SharedPreferences.Editor editor = prefs.edit();

            if (logoModel != null){
                if (logoModel.logo != null){
                    if (!logoModel.logo.equals("null") && !logoModel.logo.equals("") && !logoModel.text.equals("null") && !logoModel.text.equals("")){

                        //logo and text are changed

                        editor.putString("LogoImgName", logoModel.logo);
                        editor.putString("LogoText", logoModel.text);
                        editor.apply();



                    }
                    else if (!logoModel.logo.equals("null") && !logoModel.logo.equals("") && (logoModel.text.equals("null") || logoModel.text.equals(""))){

                        //logo is changed

                        editor.putString("LogoImgName", logoModel.logo);
                        editor.putString("LogoText", "");
                        editor.apply();


                    }
                    else if ((logoModel.logo.equals("null") || logoModel.logo.equals("")) && (!logoModel.text.equals("null") && !logoModel.text.equals(""))){

                        //text is changed

                        editor.putString("LogoImgName", "");
                        editor.putString("LogoText", logoModel.text);
                        editor.apply();



                    }
                    else {

                        //none of them is changed

                        editor.putString("LogoImgName", "");
                        editor.putString("LogoText", "");
                        editor.apply();


                    }
                }
            } else{

                String LogoImgName = prefs.getString("LogoImgName", "");
                String LogoText = prefs.getString("LogoText", "");

                if (LogoImgName.equals("") && LogoText.equals("")){

                }
                else if (!LogoImgName.equals("") && LogoText.equals("")){

                }
                else if (LogoImgName.equals("") && !LogoText.equals("")){
                    //Glide.with(getContext()).load(app.imgMainAddr + "logo/" + LogoImgName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);

                }
                else{

                }

            }

            Animation logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo);
            Animation text = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text);


            setUpTimer();

        }

    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        int result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.getPlaces(app.isInternetOn());

            result = webService.getImages(app.isInternetOn());

            result = webService.getHomePage(app.isInternetOn());

            result = webService.getSubCategory(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


//            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MYPREFS", 0);
//            Intent i;
//
//
//            if (prefs.getBoolean("LogIn_Check", false))
//            {
//                i = new Intent(getApplicationContext(), MainActivity.class);
//            }
//            else
//            {
//                i = new Intent(getApplicationContext(), loginActivity.class);
//            }


            if (prefs.getBoolean("firstTime", false) == true)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
//                    Pair[] pairs = new Pair[2];
//                    pairs[0] = new Pair<View, String>(imgAras, "App_Logo");
//                    pairs[1] = new Pair<View, String>(txtSplash, "App_text");
//
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
//                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                    ChangeBounds bounds = new ChangeBounds();
//                    bounds.setDuration(2000);
//                    getWindow().setSharedElementEnterTransition(bounds);
//                    getWindow().setSharedElementExitTransition(bounds);
//                    startActivity(i, options.toBundle());
//                    finish();

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            else
            {
                Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                startActivity(i);
                finish();
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
