package com.example.pokeapp;

public enum PokeType {
    // TODO add image src to enum constructor?
    POKETYPE_POKE (0, "Poke!", R.drawable.hey_image, 1),
    POKETYPE_HEY (1, "Hey!", R.drawable.hey_image, 1),
    POKETYPE_WHATS_UP (2, "What's up!", R.drawable.hey_image, 1),
    POKETYPE_CALL_ME (3, "Call me!", R.drawable.hey_image, 1);

    private final int id;
    private final String content;
    private final int img;
    private final int font;


    PokeType(int id, String content, int img, int font) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.font = font;
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

    public int getFont() {
        return font;
    }

    public static PokeType fromId(int id) { return PokeType.values()[id]; }

    public static PokeType fromId(String id) { return PokeType.fromId(Integer.parseInt(id)); }

}