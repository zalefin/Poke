package com.example.pokeapp;


import com.android.volley.RequestQueue;

import java.util.concurrent.ThreadFactory;

@Deprecated
public class RequestThreadFactory implements ThreadFactory {
    private RequestQueue queue;
    public String prevResult = "";

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
