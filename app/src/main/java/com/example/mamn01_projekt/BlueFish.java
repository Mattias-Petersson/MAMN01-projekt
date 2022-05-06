package com.example.mamn01_projekt;

import java.util.Random;

public class BlueFish extends Fish{
    public BlueFish() {
        super(5, 60); // A yellowfin bream is about 60 cm and weighs roughly 6kg.
        id = 0001;
        name = "Blue fish";
    }


    @Override
    public int getImageSource() {
        return R.drawable.bluefish;
    }
}
