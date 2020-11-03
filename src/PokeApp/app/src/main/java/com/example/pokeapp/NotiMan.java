package com.example.pokeapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotiMan {
    private static String CHANNEL_ID = "0";
    private int notificationId = 0; //need better way to track these
    private Context c;
    //constructor
    public NotiMan(Context from) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        c = from;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = c.getString(R.string.channel_name);
            String description = c.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = c.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //create notification
    public void createNotification() {
        //create intent to open app
        Intent openApp = new Intent(c, MainActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //create pending intent with openApp
        PendingIntent onTap = PendingIntent.getActivity(c, 0, openApp, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.qrbackgroundtest) //icon
                .setContentTitle("Notification") //notification name
                .setContentText("This is a notification.") //notification content
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //priority
                .setContentIntent(onTap) //set action on tap
                .setAutoCancel(true); //remove notification automatically

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }

    public void createNotification(String body) {
        //create intent to open app
        Intent openApp = new Intent(c, MainActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //create pending intent with openApp
        PendingIntent onTap = PendingIntent.getActivity(c, 0, openApp, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.qrbackgroundtest)
                .setContentTitle("Poked!")
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(onTap)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }
}
