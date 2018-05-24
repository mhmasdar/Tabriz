package com.example.tabrizguilds.tabrizguilds.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by mohamadHasan on 25/10/2017.
 */

public class MFirebaseMessagingService extends FirebaseMessagingService {

    private SharedPreferences prefs;
    private int idUserType;
    private Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getBody() , remoteMessage.getNotification().getTitle());
        }
    }

    private void showNotification(String messageBody, String messageTitle) {

            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.aras_logo)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(new Random().nextInt(100000), notificationBuilder.build());
        }

    }
