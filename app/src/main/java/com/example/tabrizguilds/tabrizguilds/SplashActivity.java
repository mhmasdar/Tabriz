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
    private ImageView imgSp1, imgSp2, imgSp3;
    private LinearLayout splashBack;
    private ImageView imgAras;
    private ImageView txtSplash;
    private SharedPreferences prefs;
    private WebServiceCallBack callBack;
    private WebServiceLogo callBackLogo;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();

        FirebaseMessaging.getInstance().subscribeToTopic("global");

        if (app.isInternetOn())
            SPLASH_TIME_OUT = 1000;
        else
            SPLASH_TIME_OUT = 4000;



        Animation sp0 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash0);
        Animation sp1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash1);
        Animation sp2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash2);
        Animation logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo);
        Animation text = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text);
        splashBack.startAnimation(sp0);
        imgSp1.startAnimation(sp2);
        imgSp3.startAnimation(sp2);
        imgSp2.startAnimation(sp1);
//        imgAras.setVisibility(View.VISIBLE);
//        imgAras.startAnimation(logo);
//        txtSplash.setVisibility(View.VISIBLE);
//        txtSplash.startAnimation(text);

//        Glide.with(this).load(R.drawable.aras_logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
//        Glide.with(this).load(R.drawable.aras_text).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(txtSplash);


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

    private void initView() {
        imgSp1 = (ImageView) findViewById(R.id.imgSp1);
        imgSp2 = (ImageView) findViewById(R.id.imgSp2);
        imgSp3 = (ImageView) findViewById(R.id.imgSp3);
        splashBack = (LinearLayout) findViewById(R.id.splashBack);
        imgAras = (ImageView) findViewById(R.id.imgAras);
        txtSplash = (ImageView) findViewById(R.id.txtSplash);
        txtTitle = findViewById(R.id.txtTitle);
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

                        Glide.with(SplashActivity.this).load(app.imgMainAddr + "logo/" + logoModel.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
                        txtTitle.setText(logoModel.text);
                        txtSplash.setVisibility(View.GONE);
                        txtTitle.setVisibility(View.VISIBLE);


                    }
                    else if (!logoModel.logo.equals("null") && !logoModel.logo.equals("") && (logoModel.text.equals("null") || logoModel.text.equals(""))){

                        //logo is changed

                        editor.putString("LogoImgName", logoModel.logo);
                        editor.putString("LogoText", "");
                        editor.apply();

                        Glide.with(SplashActivity.this).load(app.imgMainAddr + "logo/" + logoModel.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
                        //txtTitle.setText(logoModel.text);
                        txtSplash.setVisibility(View.VISIBLE);
                        txtTitle.setVisibility(View.GONE);

                    }
                    else if ((logoModel.logo.equals("null") || logoModel.logo.equals("")) && (!logoModel.text.equals("null") && !logoModel.text.equals(""))){

                        //text is changed

                        editor.putString("LogoImgName", "");
                        editor.putString("LogoText", logoModel.text);
                        editor.apply();

                        imgAras.setImageResource(R.drawable.aras_logo1);
                        //Glide.with(SplashActivity.this).load(app.imgMainAddr + "logo/" + logoModel.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
                        txtTitle.setText(logoModel.text);
                        txtSplash.setVisibility(View.GONE);
                        txtTitle.setVisibility(View.VISIBLE);

                    }
                    else {

                        //none of them is changed

                        editor.putString("LogoImgName", "");
                        editor.putString("LogoText", "");
                        editor.apply();

                        imgAras.setImageResource(R.drawable.aras_logo1);
                        txtSplash.setImageResource(R.drawable.aras_text2);
                        txtSplash.setVisibility(View.VISIBLE);
                        txtTitle.setVisibility(View.GONE);
                    }
                }
            } else{

                String LogoImgName = prefs.getString("LogoImgName", "");
                String LogoText = prefs.getString("LogoText", "");

                if (LogoImgName.equals("") && LogoText.equals("")){
                    imgAras.setImageResource(R.drawable.aras_logo1);
                    txtSplash.setImageResource(R.drawable.aras_text2);
                    txtSplash.setVisibility(View.VISIBLE);
                    txtTitle.setVisibility(View.GONE);
                }
                else if (!LogoImgName.equals("") && LogoText.equals("")){
                    Glide.with(SplashActivity.this).load(app.imgMainAddr + "logo/" + LogoImgName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
                    //txtTitle.setText(LogoText);
                    txtSplash.setVisibility(View.VISIBLE);
                    txtTitle.setVisibility(View.GONE);
                }
                else if (LogoImgName.equals("") && !LogoText.equals("")){
                    //Glide.with(getContext()).load(app.imgMainAddr + "logo/" + LogoImgName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
                    imgAras.setImageResource(R.drawable.aras_logo1);
                    txtTitle.setText(LogoText);
                    txtSplash.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                }
                else{
                    Glide.with(SplashActivity.this).load(app.imgMainAddr + "logo/" + LogoImgName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
                    txtTitle.setText(LogoText);
                    txtSplash.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                }

            }

            Animation logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo);
            Animation text = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text);

            imgAras.startAnimation(logo);
            txtSplash.startAnimation(text);
            txtTitle.startAnimation(text);

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

            result = webService.getCulture(app.isInternetOn());

            result = webService.getOffices(app.isInternetOn());

            result = webService.getEatings(app.isInternetOn());

            result = webService.getMedicals(app.isInternetOn());

            result = webService.getServices(app.isInternetOn());

            result = webService.getShoppings(app.isInternetOn());

            result = webService.getTourisms(app.isInternetOn());

            result = webService.getTransports(app.isInternetOn());

            result = webService.getRests(app.isInternetOn());

            result = webService.getEvents(app.isInternetOn());

            result = webService.getImages(app.isInternetOn());

            result = webService.getHomePage(app.isInternetOn());

            result = webService.getOfficePhone(app.isInternetOn());

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
