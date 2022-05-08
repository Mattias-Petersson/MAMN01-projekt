package com.example.mamn01_projekt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import java.util.Random;

public class CatchActivity extends AppCompatActivity {

    enum State {
        WAIT,
        FALSE,
        REAL,
        ENDED
    }

    private State state;
    Vibrator vibrator;
    VibrationEffect vibrationEffect1;
    Handler mHandler;
    long timeWaiting = 0;
    long timeSinceVibration = 0;
    long falseFinish= 0;
    long realFinish = 0;
    int goalFalse = 1;
    int passedFalse = 0;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGravity;
    float[] grav = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.state = State.ENDED;
        mHandler = new Handler();
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        SensorEventListener sensorEventListenerGravity = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                grav = event.values;
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        SensorEventListener sensorEventListenerAccelerometer = new SensorEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("NewApi")
            @Override
            public void onSensorChanged(SensorEvent event) {
                double acc = calculateAcc(event.values, grav);
                if (state == State.FALSE) {
                    if(acc > 5) {
                        state = state.ENDED;
                    }
                    else if(System.currentTimeMillis() > falseFinish){
                        state = State.WAIT;
                        passedFalse += 1;
                        timeWaiting = System.currentTimeMillis();
                    }
                    else if (System.currentTimeMillis() - timeSinceVibration > 100) {
                        vibrator.vibrate(vibrationEffect1);
                        timeSinceVibration = System.currentTimeMillis();
                    }

                }
                else if (state == State.WAIT) {
                    if (System.currentTimeMillis() - timeWaiting > 1000) {
                        if(goalFalse > passedFalse){
                            state = State.FALSE;
                            falseFinish = System.currentTimeMillis() + 1000;
                        }
                        else {
                            state = State.REAL;
                            realFinish = System.currentTimeMillis() + 1000;
                        }

                    }

                }
                else if (state == State.REAL) {
                    if(acc > 5) {
                        startReel();
                    }
                    else if(System.currentTimeMillis() > realFinish){
                        state = State.ENDED;
                    }
                    if (System.currentTimeMillis() - timeSinceVibration > 100) {
                        vibrator.vibrate(vibrationEffect1);
                        timeSinceVibration = System.currentTimeMillis();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

        };
        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerGravity, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startGame(android.view.View view) {
        this.state = State.WAIT;
        vibrationEffect1 = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        timeSinceVibration = System.currentTimeMillis();
        vibrator.vibrate(vibrationEffect1);
        timeWaiting = System.currentTimeMillis();
    }
    private double calculateAcc(float[] acc, float[] grav) {
        return Math.sqrt(Math.pow((double) acc[0] - grav[0], 2) + Math.pow((double) acc[1] - grav[1], 2) + Math.pow((double) acc[2] - grav[2], 2));
        //return Math.sqrt(Math.pow((double) acc[0], 2) + Math.pow((double) acc[1], 2) + Math.pow((double) acc[2], 2));
        //return (double) acc[0] - grav[0] + acc[1] - grav[1] + acc[2] - grav[2];
    }
    public void startReel() {
        startActivity(new Intent(this, ReelActivity.class));
    }
}
