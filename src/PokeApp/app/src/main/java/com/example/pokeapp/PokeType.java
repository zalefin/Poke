package com.example.pokeapp;

public enum PokeType {
    // TODO add image src to enum constructor?
    POKETYPE_POKE (0, "Poke!", R.drawable.poke_test_image),
    POKETYPE_HEY (1, "Hey!", R.drawable.hey_test_image),
    POKETYPE_WHATS_UP (2, "What's up!", R.drawable.whats_test_image),
    POKETYPE_CALL_ME (3, "Call me!", R.drawable.call_test_image);

    private final int id;
    private final String content;
    private final int img;

    PokeType(int id, String content, int img) {
        this.id = id;
        this.content = content;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getImageSrc() {
        return img;
    }

    public static PokeType fromId(int id) { return PokeType.values()[id]; }

    public static PokeType fromId(String id) { return PokeType.fromId(Integer.parseInt(id)); }

}