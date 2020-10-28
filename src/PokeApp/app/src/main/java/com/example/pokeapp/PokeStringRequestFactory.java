package com.example.pokeapp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

@Deprecated
public class PokeStringRequestFactory {


    public static StringRequest buildPokeStringRequest(String url, final Map<String, String> params) {
        return null;
        // StringRequest stringRequest = new StringRequest(Request.Method.POST, url, (response) -> RequestManager.requestThreadFactory.setResult(response), new Response.ErrorListener() {

        //     @Override
        //     public void onErrorResponse(VolleyError error) {
        //         Log.e("VOLLEY", error.toString());
        //     }
        // }) {
        //     @Override
        //     protected Map<String,String> getParams() {return params;}
        // };

        // return stringRequest;
    }
}
