package com.example.pokeapp;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PollTask implements Runnable{
    public static final int INTERVAL = 10000; // time in milliseconds between each activation

    private Handler handler;
    private NotiMan notiMan;
    private FileMan fileMan;
    private Context context;
    private MainActivity root;

    // TODO change min build version so we don't need these
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PollTask(Handler handler, MainActivity root) {
        this.handler = handler;
        this.context = root.getApplicationContext();
        this.notiMan = new NotiMan(context);
        this.fileMan = new FileMan(context);
        this.root = root;
    }

    @Override
    public void run() {
        poll();
    }

    private synchronized void poll() {
        Log.d("POLL", "did poll()");
        String uuid = fileMan.getUUID();
        RequestManager.addPollRequest(uuid, response -> {
            // TODO move this away
            try {
                JSONObject resJSON = new JSONObject(response);
                JSONArray pokes = resJSON.getJSONArray("pokes");
                for (int i = 0; i < pokes.length(); i++) {
                    JSONArray poke = pokes.getJSONArray(i);
                    String senderUUID = poke.getString(0);
                    String name = Friend.friendsList.get(senderUUID).getName();
                    String payload = poke.getString(1);
                    String message = PokeType.fromId(payload).getContent();
                    if(!root.visible) {
                        notiMan.createNotification(name + " Poked You! \n" + message);
                    }else{
                        View mainLayout = root.findViewById(R.id.main_layout);
                        Snackbar.make(mainLayout, R.string.poked, Snackbar.LENGTH_LONG).show();
                    }
                    //adds received poke to receivedPokes queue in each friend
                    Friend.friendsList.get(senderUUID).addReceivedPoke(new Poke(poke.getString(0), uuid, PokeType.fromId(payload)));
                }
                root.friendAdapter.notifyDataSetChanged();//update list view
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        handler.postDelayed(this, INTERVAL); // delay handler
    }
}
