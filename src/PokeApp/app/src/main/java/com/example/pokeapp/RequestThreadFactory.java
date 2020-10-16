package com.example.pokeapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ThreadFactory;

public class RequestThreadFactory implements ThreadFactory {
    private RequestQueue queue;
    public String prevResult = "";

    public RequestThreadFactory(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public Thread newThread(Runnable r) {
        return new Thread(r);
    }

    public void setResult(String r) {
        prevResult = r;
    }
    //Returns previous result, or null if there isn't one.
    public String getResult() {
        if(prevResult.equals("")) return null;
        String r = prevResult;
        prevResult = "";
        return r;
    }
}
