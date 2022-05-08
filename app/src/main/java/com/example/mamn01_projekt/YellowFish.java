package com.example.mamn01_projekt;

import android.graphics.Color;

public class YellowFish extends Fish{
    public YellowFish() {
        super(100, 250);
        id = 3;
        name = "Yellow fish";
        primaryColor = Color.parseColor("#F2E900");
    }
    @Override
    public int getImageSource() {
        return R.drawable.yellowfish;
    }
}
