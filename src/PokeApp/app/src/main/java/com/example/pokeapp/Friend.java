package com.example.pokeapp;

import androidx.annotation.Nullable;

public class Friend {
    // name is subject to change, UUID is not.
    private final String UUID;
    private String name;

    public Friend(@Nullable String name, String uuid) {
        // Args are name, uuid because that is what the endpoint returns
        this.UUID = uuid;
        this.name = name != null ? name : "???";
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
}
