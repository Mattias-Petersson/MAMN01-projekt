package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity  {

    private SensorManager SensorManage;
    private Sensor mAccelerometer;

    private float distance;
    private float flightTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuActivity.class));
    }


}