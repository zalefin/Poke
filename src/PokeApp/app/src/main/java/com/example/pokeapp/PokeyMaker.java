package com.example.pokeapp;

import java.util.concurrent.ThreadFactory;

public class PokeyMaker implements ThreadFactory {
    public String prevResult = "";
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }

    public void setResult(String r) {
        prevResult = r;
    }
    //Returns previous result, or null if there isn't one.
    public String getResult() {
        if(prevResult == "") return null;
        String r = prevResult;
        prevResult = "";
        return r;
    }
}
