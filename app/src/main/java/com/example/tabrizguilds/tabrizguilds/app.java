package com.example.tabrizguilds.tabrizguilds;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.webkit.WebView;

import com.crashlytics.android.Crashlytics;
import com.example.tabrizguilds.tabrizguilds.db.IOHelper;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import io.fabric.sdk.android.Fabric;
import java.util.Timer;

/**
 * Created by mohamadHasan on 22/11/2017.
 */

public class app extends Application {

    public static int check=0;
    public static Context context;
    public static Timer swipeTimer = new Timer();
    public static Timer detailsTimer = new Timer();

    public static boolean isScheduled = false;
    public static boolean isScheduledSlider = false;

    public static WebView webView;


    public static String supportAddr = "http://80.191.210.19:7862/";

    public static String imgMainAddr = "https://tabriz.touristsapp.com/Content/Upload/image/";
//    public static String imgMainAddr = "http://gsharing.ir/Content/Upload/Img/";

    public static String homeImgAddr = "Home/";
    public static String subCategoryImgAddr = "GroupsLogo/";
    public static String placesImgAddr = "Asnaf/";
    public static String newsImgAddr = "News/";
    public static String receivedImgAddr = "ReceivedImage/";


        @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();
        IOHelper.transferDatabaseFile(context);
    }

    public static boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =  (ConnectivityManager) app.context.getSystemService(CONNECTIVITY_SERVICE);
        // Check for network connections
        try {
            if (connec.getNetworkInfo(0).getState() != null)
            {
                if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    return true;
                }
                else if (
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    return false;
                }
            }
            else
            {
                if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING) {
                    return true;
                }
                else if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING) {
                return true;
            }
            else if (connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                return false;
            }
        }
        return false;
    }

    public static String getDate(){

        String date;

        PersianCalendar persianCalendar = new PersianCalendar();
        //date = persianCalendar.getPersianYear() + "" + persianCalendar.getPersianMonth() + "" + persianCalendar.getPersianDay() + "";
        boolean flagMonth = false, flagDay = false;

        if (persianCalendar.getPersianDay() / 10 < 1)
            flagDay = true;
        if ((persianCalendar.getPersianMonth()+1) / 10 < 1)
            flagMonth = true;

        date = persianCalendar.getPersianYear() + "";
        if (flagMonth)
            date += "0" + (persianCalendar.getPersianMonth() + 1);
        else
            date += "" + (persianCalendar.getPersianMonth() + 1);
        if (flagDay)
            date += "0" + persianCalendar.getPersianDay();
        else
            date += "" + persianCalendar.getPersianDay();


        return date;
    }

    public static String changeDateToString(int intDate){

        String stringDate;

        String year = String.valueOf(intDate).substring(0,4);
        String month = String.valueOf(intDate).substring(4,6);
        String day = String.valueOf(intDate).substring(6,8);

        stringDate = year + "/" + month + "/" + day;

        return stringDate;
    }

}
