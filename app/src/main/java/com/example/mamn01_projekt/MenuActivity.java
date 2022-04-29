package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

     public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }
    public void startReel(View v) {
        startActivity(new Intent(this, ReelActivity.class));
    }
    public void startCatch(View v) {
        startActivity(new Intent(this, CatchActivity.class));
    }
}