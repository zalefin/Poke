package com.example.pokeapp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Pokey implements Runnable{
    RequestQueue queue;// = Volley.newRequestQueue(this);
    PokeyMaker source;
    String url;// ="https://www.google.com";
    String[] args;

    /*
     * Jake, writing documentation? Surely not.
     * args[0] represents the type of request, denoted by its endpoint. The rest depend. Here's a list.
     * args[0] = register, args[1] = name
     * args[0] = friends/add, args[1] = user_UUID, args[2] = target_UUID
     * The arguments are organized so that addXRequest takes arguments in order.
     */
    public Pokey(RequestQueue queue, PokeyMaker source, String url, String[] args) {
        this.queue = queue;
        this.source = source;
        this.url = url;
        this.args = args;
    }

    //@Override
    public void run() {
        Log.d("POKEY", "Starting pokey with endpoint " + args[0]);
        switch(args[0]) {
            case "register":
                addRegiRequest(args[1]); //name
                break;
            case "friends/add":
                addAddFriendRequest(args[1], args[2]); //UUID, target ID
                break;
            case "friends/delete":
                addRemoveFriendRequest(args[1], args[2]); //UUID, target ID
                break;
            case "poll":
                addPollRequest(args[1]); //UUID
                break;
            case "poke":
                addPokeRequest(args[1],args[2],args[3]); //UUID, target ID, payload
                break;
            case "update":
                addUpdateRequest(args[1]);
                break;
            default:
                Log.d("POKEY", "endpoint not found");
        }
    }

    void addRegiRequest(String name) {
        final Map<String, String> params = new HashMap<>();
        params.put("name", name);
        StringRequest stringRequest = PokeStringRequestFactory.buildPokeStringRequest(source, url, params);
        queue.add(stringRequest);
    }

    void addPollRequest(String user_UUID) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", user_UUID);
        StringRequest stringRequest =  PokeStringRequestFactory.buildPokeStringRequest(source, url, params);
        queue.add(stringRequest);
    }

    void addPokeRequest(String user_UUID, String targ_UUID, String payload) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", user_UUID);
        params.put("target", targ_UUID);
        params.put("payload", payload);
        StringRequest stringRequest =  PokeStringRequestFactory.buildPokeStringRequest(source, url, params);
        queue.add(stringRequest);
    }

    void addUpdateRequest(String user_UUID) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", user_UUID);
        StringRequest stringRequest =  PokeStringRequestFactory.buildPokeStringRequest(source, url, params);
        queue.add(stringRequest);
    }

    void addAddFriendRequest(String user_UUID, String target_UUID) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", user_UUID);
        params.put("target", target_UUID);
        StringRequest stringRequest =  PokeStringRequestFactory.buildPokeStringRequest(source, url, params);
        queue.add(stringRequest);
    }

    void addRemoveFriendRequest(String user_UUID, String target_UUID) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", user_UUID);
        params.put("target", target_UUID);
        StringRequest stringRequest =  PokeStringRequestFactory.buildPokeStringRequest(source, url, params);
        queue.add(stringRequest);
    }
};
