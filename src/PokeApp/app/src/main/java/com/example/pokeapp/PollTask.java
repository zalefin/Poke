package com.example.pokeapp;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PollTask implements Runnable{
    public static final int INTERVAL = 10000; // time in milliseconds between each activation

    private Handler handler;
    private NotiMan notiMan;
    private FileMan fileMan;
    private Context context;

    // TODO change min build version so we don't need these
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PollTask(Handler handler, Context context) {
        this.handler = handler;
        this.notiMan = new NotiMan(context);
        this.fileMan = new FileMan(context);
        this.context = context;
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
                    notiMan.createNotification(name + " Poked You! \n" + message);
                    //adds received poke to receivedPokes queue in each friend
                    Friend.friendsList.get(senderUUID).addReceivedPoke(new Poke(poke.getString(0), uuid, PokeType.fromId(payload)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            if(error.networkResponse==null){
                Toast.makeText(context, "Check your connection.", Toast.LENGTH_SHORT).show();
            }else if(error.networkResponse.statusCode == 400){
                Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show();
            }
        });

        handler.postDelayed(this, INTERVAL); // delay handler
    }
}
