package com.example.pokeapp;

public enum PokeType {
    // TODO add image src to enum constructor?
    POKETYPE_POKE (0, "Poke!", R.drawable.hey_image, R.font.poke_font, R.color.Black),
    POKETYPE_HEY (1, "Hey!", R.drawable.hey_image, R.font.cat, R.color.Black),
    POKETYPE_WHATS_UP (2, "What's up?", R.drawable.hey_image, R.font.ink, R.color.color11),
    POKETYPE_I_HAVE_COVID (3, "I have Covid!", R.drawable.hey_image, R.font.cloud, R.color.green);

    private final int id;
    private final String content;
    private final int img;
    private final int font;
    private final int color;


    PokeType(int id, String content, int img, int font, int color) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.font = font;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public static PokeType fromId(int id) { return PokeType.values()[id]; }

    public static PokeType fromId(String id) { return PokeType.fromId(Integer.parseInt(id)); }

}