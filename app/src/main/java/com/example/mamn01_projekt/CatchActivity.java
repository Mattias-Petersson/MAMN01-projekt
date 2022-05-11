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
import android.widget.Toast;

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
    VibrationEffect vibrationEffectFalse;
    VibrationEffect vibrationEffectReal;
    VibrationEffect vibrationEffectEnd;
    Handler mHandler;
    long timeToWait = 0;
    long timeSinceVibration = 0;
    long falseFinish= 0;
    long realFinish = 0;
    int goalFalse = 1;
    int gameType = 0;
    int passedFalse = 0;
    Random r = new Random();
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
        vibrationEffectEnd = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
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
                if(acc > 5 && state != State.REAL) {
                    state = State.ENDED;
                    vibrator.vibrate(vibrationEffectEnd);
                }
                if (state == State.FALSE) {
                    if(System.currentTimeMillis() > falseFinish){
                        state = State.WAIT;
                        passedFalse += 1;
                        setWaitTime();
                    }
                    else if (System.currentTimeMillis() - timeSinceVibration > 100) {
                        vibrator.vibrate(vibrationEffectFalse);
                        timeSinceVibration = System.currentTimeMillis();
                    }

                }
                else if (state == State.WAIT) {
                    if (System.currentTimeMillis() > timeToWait) {
                        if(goalFalse > passedFalse){
                            state = State.FALSE;
                            setFalseFinish();
                        }
                        else {
                            state = State.REAL;
                            realFinish = System.currentTimeMillis() + 1000;
                        }

                    }

                }
                else if (state == State.REAL) {
                    if(acc > 5) {
                        System.out.println("Hallå");
                        startReel();
                    }
                    else if(System.currentTimeMillis() > realFinish){
                        state = State.ENDED;
                    }
                    if (System.currentTimeMillis() - timeSinceVibration > 100) {
                        vibrator.vibrate(vibrationEffectReal);
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
    public void startGame1(android.view.View view) {
        this.state = State.WAIT;
        gameType = 1;
        vibrationEffectFalse = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrationEffectReal = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrationEffectEnd = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(vibrationEffectFalse);
        timeSinceVibration = System.currentTimeMillis();
        setWaitTime();
        setGoalFalse();
    }

    public void startGame2(android.view.View view) {
        this.state = State.WAIT;
        gameType = 2;
        vibrationEffectFalse = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrationEffectReal = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrationEffectEnd = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(vibrationEffectFalse);
        timeSinceVibration = System.currentTimeMillis();
        setWaitTime();
        setGoalFalse();
    }

    public void startGame3(android.view.View view) {
        this.state = State.WAIT;
        gameType = 3;
        vibrationEffectFalse = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrationEffectReal = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrationEffectEnd = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(vibrationEffectFalse);
        timeSinceVibration = System.currentTimeMillis();
        setWaitTime();
        setGoalFalse();
    }

    private void setWaitTime(){
        if(gameType == 1) {
            timeToWait = System.currentTimeMillis() + 1000;
        }
        else if(gameType == 2) {
            timeToWait = System.currentTimeMillis() + 7000 + (int) r.nextInt(60)*100;
        }
        else{
            timeToWait = System.currentTimeMillis() + 3000 + (int) r.nextInt(40)*100;
        }
    }

    private void setGoalFalse() {
        goalFalse = r.nextInt(3);
    }

    public void setFalseFinish() {
        if(gameType == 1) {
            falseFinish = System.currentTimeMillis() + 100;
        }
        else if(gameType == 2) {
            falseFinish = System.currentTimeMillis() + 200;
        }
        else{
            falseFinish = System.currentTimeMillis() + 100 + r.nextInt(2)*100;
        }
    }

    public void setRealFinishFinish() {
        if(gameType == 1) {
            realFinish = System.currentTimeMillis() + 1000;
        }
        else if(gameType == 2) {
            realFinish = System.currentTimeMillis() + 2000;
        }
        else{
            realFinish = System.currentTimeMillis() + 500 + r.nextInt(10)*100;
        }
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
