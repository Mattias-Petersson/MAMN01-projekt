package com.example.mamn01_projekt;

public class RedFish extends Fish{
    public RedFish(double factorSize) {
        super(6);
        name = "Red Fish";
    }

    @Override
    public int getImageSource() {
        return R.drawable.redfish;
    }
}
