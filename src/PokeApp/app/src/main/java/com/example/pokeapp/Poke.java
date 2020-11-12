package com.example.pokeapp;

public class Poke {

    private final String senderUUID;
    private final String targetUUID;
    private final PokeType pokeType;

    public Poke(
            String senderUUID,
            String targetUUID,
            PokeType pokeType) {
        // Args are sender UUID, pokeID because that is what the endpoint returns
        this.senderUUID = senderUUID;
        this.targetUUID = targetUUID;
        this.pokeType = pokeType;
    }

    public String getSenderUUID() {
        return senderUUID;
    }

    public String getTargetUUID() {
        return targetUUID;
    }

    public PokeType getPokeType() {
        return pokeType;
    }
}