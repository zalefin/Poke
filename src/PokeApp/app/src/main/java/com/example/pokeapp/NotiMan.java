package com.example.pokeapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.qrbackgroundtest)
                .setContentTitle("Notification")
                .setContentText("This is a notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }

    public void createNotification(String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.qrbackgroundtest)
                .setContentTitle("Poked!")
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        notificationId++;
    }
}
