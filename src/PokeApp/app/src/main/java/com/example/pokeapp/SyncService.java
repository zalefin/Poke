package com.example.pokeapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SyncService extends Service {

    private Handler pollHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        pollHandler = new Handler();
        PollTask pollTask = new PollTask(pollHandler, this);
        // Execute a runnable task as soon as possible
        pollHandler.post(pollTask);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}