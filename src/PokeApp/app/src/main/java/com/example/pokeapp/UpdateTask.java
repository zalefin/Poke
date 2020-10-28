package com.example.pokeapp;

import android.os.Handler;
import android.util.Log;

public class UpdateTask implements Runnable{
    public static final int INTERVAL = 1000; // time in milliseconds between each activation

    private Handler handler;

    public UpdateTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        doUpdate();
    }

    private synchronized void doUpdate() {
        Log.d("UPDATE", "did doUpdate()");

        handler.postDelayed(this, INTERVAL); // delay handler
    }
}
