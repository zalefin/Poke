package com.example.pokeapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestManager {
    public static Context rootContext;
    public static RequestThreadFactory requestThreadFactory;
    public static RequestQueue queue;

    public static void init(Context context) {
        rootContext = context;
        requestThreadFactory = new RequestThreadFactory(rootContext);
        queue = Volley.newRequestQueue(rootContext);
    }
}
