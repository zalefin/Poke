package com.example.pokeapp;

public enum PokeType {
    // TODO add image src to enum constructor?
    POKETYPE_POKE (0, "Poke!", R.drawable.poke_image, R.font.poke_font, 70, R.color.Black),
    POKETYPE_HEY (1, "Hey!", R.drawable.hey_image, R.font.cat, 70, R.color.Black),
    POKETYPE_WHATS_UP (2, "Judging you.", R.drawable.judging_image, R.font.ink, 40, R.color.color11),
    POKETYPE_I_HAVE_COVID (3, "I have Covid!", R.drawable.covid_image, R.font.cloud, 40, R.color.green);

    private final int id;
    private final String content;
    private final int img;
    private final int font;
    private final int color;
    private final int fontSize;


    PokeType(int id, String content, int img, int font, int fontSize, int color) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.font = font;
        this.color = color;
        this.fontSize = fontSize;
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

    public int getFontSize() {
        return fontSize;
    }

    public int getColor() {
        return color;
    }

    public static PokeType fromId(int id) { return PokeType.values()[id]; }

    public static PokeType fromId(String id) { return PokeType.fromId(Integer.parseInt(id)); }

}