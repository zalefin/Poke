package com.example.pokeapp;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class PokeRequest extends StringRequest {
    private final Map<String, String> params;

    public PokeRequest(
            String url,
            Response.Listener<String> listener,
            @Nullable Response.ErrorListener errorListener,
            final Map<String, String> params
            ) {
        super(Request.Method.POST, url, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() {
        return this.params;
    }
}
