package com.example.tabrizguilds.tabrizguilds;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.adapter.SlidingImage_Adapter;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.HomePageModel;
import com.example.tabrizguilds.tabrizguilds.models.ReligiousTimesModel;
import com.example.tabrizguilds.tabrizguilds.models.WeatherModel;
import com.example.tabrizguilds.tabrizguilds.services.WeatherService;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPagerCustomDuration mPager;
    private static int currentPage = 0;
    private RelativeLayout relative_Menu;
    private LinearLayout lytWeather;
    TextView cityField, txtPray, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

    TextView currentTemperatureFieldZonoz, currentTemperatureFieldKhod, humidity_fieldZonoz, humidity_fieldKhod, weatherIconZonoz, weatherIconKhod;

    private boolean checkWeather = true;
    private CirclePageIndicator indicator;
    private Dialog dialog;
    private List<HomePageModel> homePageModelList;
    private TextView txtSobh;
    private TextView txttolo;
    private TextView txtZohr;
    private TextView txtGhorob;
    private TextView txtMaghreb;
    private TextView txtNimeShab;
    TextView txtSobhKhod, txttoloKhod, txtZohrKhod, txtGhorobKhod, txtMaghrebKhod, txtNimeShabKhod, txtSobhZonoz, txttoloZonoz, txtZohrZonoz, txtGhorobZonoz, txtMaghrebZonoz, txtNimeShabZonoz;
    private Button btnCancel;
    private LinearLayout lytLoading, lytMain, lytDisconnect;
    private WeatherServiceCallBack WcallBack;
    private DatabaseCallback databaseCallback;
    private ReligiousTimesCallBack timesCallBack;

    private ReligiousTimesModel timesModelJolfa, timesModelZonoz, timesModelKhod;

    public boolean flagPermission = false;

    TextView txtTitle;
    private ImageView imgAras;
    private ImageView txtSplash;

    private SharedPreferences prefs;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        currentTemperatureFieldZonoz = (TextView) view.findViewById(R.id.current_temperature_field_zonoz);
        currentTemperatureFieldKhod = (TextView) view.findViewById(R.id.current_temperature_field_khod);
        humidity_field = (TextView) view.findViewById(R.id.humidity_field);
        humidity_fieldZonoz = (TextView) view.findViewById(R.id.humidity_field_zonoz);
        humidity_fieldKhod = (TextView) view.findViewById(R.id.humidity_field_khod);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        weatherIconZonoz = (TextView) view.findViewById(R.id.weather_icon_zonoz);
        weatherIconKhod = (TextView) view.findViewById(R.id.weather_icon_khod);
        txtPray = (TextView) view.findViewById(R.id.txtPray);
        relative_Menu = (RelativeLayout) view.findViewById(R.id.relative_Menu);
        lytWeather = (LinearLayout) view.findViewById(R.id.lytWeather);
        mPager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        imgAras = (ImageView) view.findViewById(R.id.imgAras);
        txtSplash = (ImageView) view.findViewById(R.id.txtSplash);
        txtTitle = view.findViewById(R.id.txtTitle);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (getContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || getContext().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
//            } else {
//                flagPermission = true;
//            }
//        } else {
//            flagPermission = true;
//        }


        prefs = getContext().getSharedPreferences("login", 0);


        String LogoImgName = prefs.getString("LogoImgName", "");
        String LogoText = prefs.getString("LogoText", "");

        if (LogoImgName.equals("") && LogoText.equals("")){
            imgAras.setImageResource(R.drawable.aras_logo1);
            txtSplash.setImageResource(R.drawable.aras_text2);
            txtSplash.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.GONE);
        }
        else if (!LogoImgName.equals("") && LogoText.equals("")){
            Glide.with(getContext()).load(app.imgMainAddr + "logo/" + LogoImgName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
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
            Glide.with(getContext()).load(app.imgMainAddr + "logo/" + LogoImgName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgAras);
            txtTitle.setText(LogoText);
            txtSplash.setVisibility(View.GONE);
            txtTitle.setVisibility(View.VISIBLE);
        }


        databaseCallback = new DatabaseCallback(getContext());
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //initSlider();


        txtPray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrayDialog();
            }
        });

        if (checkWeather && app.isInternetOn()) {
            WcallBack = new WeatherServiceCallBack();
            WcallBack.execute();
            timesCallBack = new ReligiousTimesCallBack();
            timesCallBack.execute();


        }

        relative_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity)getActivity()).drawer();
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });


        return view;
    }


    private class WeatherServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WeatherService weatherService;
        private WeatherModel weatherModelTabriz, weatherModelJolfa, weatherModelHashtrood;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weatherService = new WeatherService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            weatherModelTabriz = weatherService.getWeatherTabriz();
            weatherModelJolfa = weatherService.getWeatherJolfa();
            weatherModelHashtrood = weatherService.getWeatherHashtrood();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //set weather
            if (weatherModelTabriz != null) {
                currentTemperatureField.setText(weatherModelTabriz.temperature);
                humidity_field.setText(weatherModelTabriz.humidity);
                weatherIcon.setText(Html.fromHtml(weatherModelTabriz.iconText));
                checkWeather = false;
                lytWeather.setVisibility(View.VISIBLE);
            }
            if (weatherModelJolfa != null) {
                currentTemperatureFieldZonoz.setText(weatherModelJolfa.temperature);
                humidity_fieldZonoz.setText(weatherModelJolfa.humidity);
                weatherIconZonoz.setText(Html.fromHtml(weatherModelJolfa.iconText));
                checkWeather = false;
                lytWeather.setVisibility(View.VISIBLE);
            }
            if (weatherModelHashtrood != null) {
                currentTemperatureFieldKhod.setText(weatherModelHashtrood.temperature);
                humidity_fieldKhod.setText(weatherModelHashtrood.humidity);
                weatherIconKhod.setText(Html.fromHtml(weatherModelHashtrood.iconText));
                checkWeather = false;
                lytWeather.setVisibility(View.VISIBLE);
            }


        }
    }

    private class ReligiousTimesCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            timesModelJolfa = webService.getReligiousTimesJolfa(app.isInternetOn());
            timesModelZonoz = webService.getReligiousTimesNordoz(app.isInternetOn());
            timesModelKhod = webService.getReligiousTimesKhod(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private void initSlider() {


        mPager.setAdapter(new SlidingImage_Adapter(getContext(), homePageModelList));
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) //slider is on the last image
                    currentPage = 0;

                mPager.setCurrentItem(currentPage++, true);
            }
        };

        app.isScheduled = false;

        if (app.isScheduled == false) {
            app.swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            app.isScheduled = true;
        }

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
//        private String tblName;
//        int id;

        public DatabaseCallback(Context context) {
            this.context = context;
//            this.tblName = tblName;
//            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            homePageModelList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            homePageModelList = databaseHelper.selectAllHomePages();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            if (homePageModelList != null) {

            initSlider();
//            }

        }

    }


    private void showPrayDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pray);

        txtSobh = (TextView) dialog.findViewById(R.id.txtSobh);
        txttolo = (TextView) dialog.findViewById(R.id.txttolo);
        txtZohr = (TextView) dialog.findViewById(R.id.txtZohr);
        txtGhorob = (TextView) dialog.findViewById(R.id.txtGhorob);
        txtMaghreb = (TextView) dialog.findViewById(R.id.txtMaghreb);
        txtNimeShab = (TextView) dialog.findViewById(R.id.txtNimeShab);

        txtSobhKhod = (TextView) dialog.findViewById(R.id.txtSobh2);
        txttoloKhod  = (TextView) dialog.findViewById(R.id.txttolo2);
        txtZohrKhod  = (TextView) dialog.findViewById(R.id.txtZohr2);
        txtGhorobKhod  = (TextView) dialog.findViewById(R.id.txtGhorob2);
        txtMaghrebKhod  = (TextView) dialog.findViewById(R.id.txtMaghreb2);
        txtNimeShabKhod  = (TextView) dialog.findViewById(R.id.txtNimeShab2);

        txtSobhZonoz = (TextView) dialog.findViewById(R.id.txtSobh3);
        txttoloZonoz = (TextView) dialog.findViewById(R.id.txttolo3);
        txtZohrZonoz = (TextView) dialog.findViewById(R.id.txtZohr3);
        txtGhorobZonoz = (TextView) dialog.findViewById(R.id.txtGhorob3);
        txtMaghrebZonoz = (TextView) dialog.findViewById(R.id.txtMaghreb3);
        txtNimeShabZonoz = (TextView) dialog.findViewById(R.id.txtNimeShab3);

        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        lytDisconnect = dialog.findViewById(R.id.lytDisconnect);
        lytMain = dialog.findViewById(R.id.lytMain);
        lytLoading = dialog.findViewById(R.id.lytLoading);


        if (timesModelJolfa != null && timesModelZonoz != null && timesModelKhod != null) {

            txtSobh.setText(timesModelJolfa.Imsaak);
            txttolo.setText(timesModelJolfa.Sunrise);
            txtZohr.setText(timesModelJolfa.Noon);
            txtGhorob.setText(timesModelJolfa.Sunset);
            txtMaghreb.setText(timesModelJolfa.Maghreb);
            txtNimeShab.setText(timesModelJolfa.Midnight);

            txtSobhKhod.setText(timesModelKhod.Imsaak);
            txttoloKhod.setText(timesModelKhod.Sunrise);
            txtZohrKhod.setText(timesModelKhod.Noon);
            txtGhorobKhod.setText(timesModelKhod.Sunset);
            txtMaghrebKhod.setText(timesModelKhod.Maghreb);
            txtNimeShabKhod.setText(timesModelKhod.Midnight);

            txtSobhZonoz.setText(timesModelZonoz.Imsaak);
            txttoloZonoz.setText(timesModelZonoz.Sunrise);
            txtZohrZonoz.setText(timesModelZonoz.Noon);
            txtGhorobZonoz.setText(timesModelZonoz.Sunset);
            txtMaghrebZonoz.setText(timesModelZonoz.Maghreb);
            txtNimeShabZonoz.setText(timesModelZonoz.Midnight);


        } else {
            lytDisconnect.setVisibility(View.VISIBLE);
            lytMain.setVisibility(View.GONE);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            flagPermission = true;
        } else
            flagPermission = false;

    }

    @Override
    public void onStop() {
        super.onStop();

        if (WcallBack != null && WcallBack.getStatus() == AsyncTask.Status.RUNNING)
            WcallBack.cancel(true);
        if (timesCallBack != null && timesCallBack.getStatus() == AsyncTask.Status.RUNNING)
            timesCallBack.cancel(true);
        if (databaseCallback != null && databaseCallback.getStatus() == AsyncTask.Status.RUNNING)
            databaseCallback.cancel(true);
    }
}
