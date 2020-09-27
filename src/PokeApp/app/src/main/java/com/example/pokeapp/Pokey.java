package com.example.pokeapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Pokey implements Runnable{
    RequestQueue queue;// = Volley.newRequestQueue(this);
    String url;// ="https://www.google.com";
    PokeyMaker source;

    public Pokey(RequestQueue q, String u, PokeyMaker p) {
        queue = q;
        url = u;
        source = p;
    }

    //@Override
    public void run() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        if(size > 50) size = 50; //cut off length at 50 characters
                        Log.d("RESPONSE", response.substring(0,size));
                        source.setResult(response.substring(0,size));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                if(error.networkResponse != null) {
                    Log.d("RESPONSE", "Failed, code " + error.networkResponse.statusCode);
                }
            }
        });

        queue.add(stringRequest);
    }
}
