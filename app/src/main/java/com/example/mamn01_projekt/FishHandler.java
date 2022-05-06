package com.example.mamn01_projekt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class FishHandler {
    Random r = new Random();
    HashSet<Fish> typesOfFish = new HashSet<>(); // A list to keep a number of all types of fish.
    public FishHandler() {
        addFish();
    }
    /*
    A method to add all types of Fish to a List, made so that the class is following the
    open-closed principle. It's easy to add new types of fish to this list.
     */
    private void addFish() {
        typesOfFish.add(new BlueFish());
        typesOfFish.add(new RedFish());
    }
    public Fish returnCatch() {
        return returnCatchHelper(r.nextInt(typesOfFish.size()));
    }
    private Fish returnCatchHelper(int index) {
       List<Fish> a = new ArrayList<>(typesOfFish);
       return a.get(index);
    }

}
