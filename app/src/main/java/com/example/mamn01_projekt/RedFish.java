package com.example.mamn01_projekt;

import android.graphics.Color;


public class RedFish extends Fish{
    public RedFish() {
        super(30, 140);
        id = 2;
        name = "Red fish";
        primaryColor = Color.parseColor("#971313");
    }
    @Override
    public int getImageSource() {
        return R.drawable.redfish;
    }
}
