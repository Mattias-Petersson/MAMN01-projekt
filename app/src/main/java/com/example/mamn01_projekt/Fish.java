package com.example.mamn01_projekt;

import java.util.Random;

public abstract class Fish {
    protected int id;
    protected String name;
    protected double length;
    protected double weight;
    private final double decimalPlaces = 100d;
    public Fish(double baseWeight, double baseLength) {
        Random r = new Random();
        double scale = r.nextDouble();
        weight = Math.round(baseWeight * scale * decimalPlaces) / decimalPlaces;
        length = Math.round(baseLength * scale * decimalPlaces) / decimalPlaces;
    }
    public abstract int getImageSource();
}
