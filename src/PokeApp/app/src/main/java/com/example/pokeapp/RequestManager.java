package com.example.pokeapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RequestManager {
    public static final String BASE_URL = "https://poke.zachlef.in/";

    // public static RequestThreadFactory requestThreadFactory;
    public static RequestQueue queue;

    public static void init(Context context) {
        // requestThreadFactory = new RequestThreadFactory();
        queue = Volley.newRequestQueue(context);
    }

    public static void addRegisterRequest(String name, Response.Listener<String> callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("name", name);
        PokeRequest request = new PokeRequest(BASE_URL + "poke/register",
                callback, null, params);
        queue.add(request);
    }

    public static void addPollRequest(String userUUID, Response.Listener<String> callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", userUUID);
        PokeRequest request = new PokeRequest(BASE_URL + "poke/poll",
                callback, null, params);
        queue.add(request);
    }

    public static void addPokeRequest(
            String userUUID,
            String targetUUID,
            String payload,
            Response.Listener<String> callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", userUUID);
        params.put("target", targetUUID);
        params.put("payload", payload);
        PokeRequest request = new PokeRequest(BASE_URL + "poke/poke",
                callback, null, params);
        queue.add(request);
    }

    public static void addUpdateRequest(String userUUID, Response.Listener<String> callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", userUUID);
        PokeRequest request = new PokeRequest(BASE_URL + "poke/update",
                callback, null, params);
        queue.add(request);
    }

    public static void addAddFriendRequest(
            String userUUID,
            String targetUUID,
            Response.Listener<String> callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", userUUID);
        params.put("target", targetUUID);
        PokeRequest request = new PokeRequest(BASE_URL + "poke/friends/add",
                callback, null, params);
        queue.add(request);
    }

    public static void addRemoveFriendRequest(
            String userUUID,
            String targetUUID,
            Response.Listener<String> callback) {
        final Map<String, String> params = new HashMap<>();
        params.put("user", userUUID);
        params.put("target", targetUUID);
        PokeRequest request = new PokeRequest(BASE_URL + "poke/friends/delete",
                callback, null, params);
        queue.add(request);
    }
}
