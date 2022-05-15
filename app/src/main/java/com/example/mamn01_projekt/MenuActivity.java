package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    int backButtonCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backButtonCounter = 0;
    }

    public void startGame(View v) {
        this.startActivity(new Intent(this, ThrowActivity.class));
    }
    public void startTutorial(View v) { this.startActivity(new Intent(this, TutorialActivity.class)); }

    /* public void startReel(View v) {
        startActivity(new Intent(this, ReelActivity.class));
    }
    public void startCatch(View v) {
        startActivity(new Intent(this, CatchActivity.class));
    }
    public void startThrow(View v) {
        startActivity(new Intent(this, ThrowActivity.class));
    } */

    @Override
    public void onBackPressed() {
        if(backButtonCounter >= 1) {
            moveTaskToBack(true);
        } else{
            Toast.makeText(this, "Press the back button again to exit the application.", Toast.LENGTH_SHORT).show();
            backButtonCounter++;
        }


    }
}