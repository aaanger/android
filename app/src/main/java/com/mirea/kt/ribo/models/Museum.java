package com.mirea.kt.ribo.models;

public class Museum {
    private String name;
    private int image;

    public Museum(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }
}