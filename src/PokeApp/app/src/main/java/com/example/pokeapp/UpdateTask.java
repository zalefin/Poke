package com.example.pokeapp;

import android.os.Handler;
import android.util.Log;

public class UpdateTask implements Runnable{
    public static final int INTERVAL = 15000; // time in milliseconds between each activation

    private Handler handler;
    private MainActivity root;

    public UpdateTask(Handler handler, MainActivity root) {
        this.handler = handler;
        this.root = root;
    }

    @Override
    public void run() {
        update();
    }

    private synchronized void update() {
        Log.d("UPDATE", "did doUpdate()");
        root.updateFriends();

        handler.postDelayed(this, INTERVAL); // delay handler
    }
}
