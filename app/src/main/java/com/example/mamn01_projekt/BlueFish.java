package com.example.mamn01_projekt;

public class BlueFish extends Fish{
    public BlueFish(double factorSize) {
        super(1.5);
        name = "Blue Fish";
    }

    @Override
    public int getImageSource() {
        return R.drawable.bluefish;
    }
}
