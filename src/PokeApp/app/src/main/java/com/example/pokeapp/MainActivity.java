package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.*;
import java.lang.*;

import com.example.pokeapp.Pokey;
import com.example.pokeapp.PokeyMaker;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(this);

        final PokeyMaker p = new PokeyMaker();
        Thread test = p.newThread(new Pokey(queue, "https://www.google.com"));
        test.run();
        // ...

        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://www.google.com";

        /*
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        */
    }
}