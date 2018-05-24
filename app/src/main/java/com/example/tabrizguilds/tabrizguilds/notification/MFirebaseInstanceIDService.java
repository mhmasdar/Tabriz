package com.example.tabrizguilds.tabrizguilds.notification;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mohamadHasan on 25/10/2017.
 */

public class MFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//        try {
//            int v = getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }



}
