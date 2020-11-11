package com.example.pokeapp;

public enum PokeType {
    // TODO add image src to enum constructor?
    POKETYPE_POKE (0, "Poke!"),
    POKETYPE_HEY (1, "Hey!"),
    POKETYPE_WHATS_UP (2, "What's up!"),
    POKETYPE_CALL_ME (3, "Call me!");

    private final int id;
    private final String content;

    PokeType(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public static PokeType fromId(int id) { return PokeType.values()[id]; }

    public static PokeType fromId(String id) { return PokeType.fromId(Integer.parseInt(id)); }

}