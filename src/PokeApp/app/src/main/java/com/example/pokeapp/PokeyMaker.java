package com.example.pokeapp;

import java.util.concurrent.ThreadFactory;

public class PokeyMaker implements ThreadFactory {
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
