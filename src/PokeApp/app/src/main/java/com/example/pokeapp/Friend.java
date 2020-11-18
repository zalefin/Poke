package com.example.pokeapp;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Friend {
    // name is subject to change, UUID is not.
    private final String UUID;
    private String name;
    private Queue<Poke> receivedPokes;

    public Friend(@Nullable String name, String uuid) {
        // Args are name, uuid because that is what the endpoint returns
        this.UUID = uuid;
        this.name = name != null ? name : "???";
        this.receivedPokes = new LinkedList<>();
    }

    public String getUUID() {
        return UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addReceivedPoke(Poke p){
        receivedPokes.add(p);
        Log.i("Friend", "Added Poke " + p.getPokeType().getContent() + " to " + name);
    }

    public Poke getMostRecentPoke(){
        return receivedPokes.poll();
    }

    public int getNumPokes(){
        return receivedPokes.size();
    }

    public boolean hasPokes(){
        return !receivedPokes.isEmpty();
    }

}

//7add896f-46bf-42f4-a991-12ba5a42e27c
