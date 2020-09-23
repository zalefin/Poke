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

    public Pokey(RequestQueue q, String u) {
        queue = q;
        url = u;
    }

    //@Override
    public void run() {
        Log.d("POKEY", "Pokey has been run");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mainContext.textView.setText("Response is: "+ response.substring(0,500));
                        Log.d("RESPONSE", response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                Log.d("RESPONSE", "Failed");
            }
        });

        queue.add(stringRequest);
    }
}
