package com.example.pokeapp;

public class Poke {

    private String pokeID;
    private String senderUUID;
    private String message;

    public Poke(String senderUUID, String pokeID, String message ) {
        // Args are sender UUID, pokeID because that is what the endpoint returns
        this.senderUUID = senderUUID;
        this.pokeID = pokeID;
        this.message = message;
    }

    public String getSenderUUID() {
        return senderUUID;
    }

    public String getPokeID() {
        return pokeID;
    }

    public String getMessage() { return message; }

}
