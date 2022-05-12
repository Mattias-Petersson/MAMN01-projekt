package com.example.mamn01_projekt;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public abstract class GameActivities extends AppCompatActivity {
    private int backButtonCounter = 0;
     public void onBackPressed() {
         if(backButtonCounter >= 1) {
             Intent i = new Intent(this, MenuActivity.class);
             startActivity(i);
         } else{
             Toast.makeText(this, "Press the back button again \r\n to exit your current game", Toast.LENGTH_SHORT).show();
             backButtonCounter++;
         }
    }
}
