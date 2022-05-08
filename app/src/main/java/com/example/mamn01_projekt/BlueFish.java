package com.example.mamn01_projekt;

import android.graphics.Color;


public class BlueFish extends Fish {
    public BlueFish() {
        super(5, 60); // A yellowfin bream is about 60 cm and weighs roughly 6kg.
        id = 1;
        name = "Blue fish";
        primaryColor = Color.parseColor("#99d9ea");
    }


    @Override
    public int getImageSource() {
        return R.drawable.bluefish;
    }
}
