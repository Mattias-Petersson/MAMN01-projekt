package com.example.mamn01_projekt;

public abstract class Fish {
    protected final int baseSize = 2; // No fish weighs less than 2kg in this app for now.
    protected String name;
    protected double size;
    public Fish(double factorSize) {
        size = baseSize * factorSize;
    }
    public abstract int getImageSource();
}
