package com.example.mamn01_projekt;

import java.util.Random;

public class RedFish extends Fish{
    public RedFish() {
        super(30, 140);
        id = 0002;
        name = "Red fish";
    }
    @Override
    public int getImageSource() {
        return R.drawable.redfish;
    }
}
