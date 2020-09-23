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
        for(int n=0; n<1; n++) {
            Thread t = p.newThread(new Pokey(queue, "http://zachlef.in:8080"));
            t.run();
        }
    }
}