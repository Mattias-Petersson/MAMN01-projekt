package com.example.mamn01_projekt;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import java.util.Timer;
import java.util.TimerTask;

public class CatchActivity extends GameActivities {

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
    double distance;
    long timeToWait = 0;
    long timeSinceVibration = 0;
    long falseFinish= 0;
    long realFinish = 0;
    int goalFalse = 3;
    int gameType = 0;
    int passedFalse = 0;
    Random r = new Random();
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorGravity;
    float[] grav = new float[3];
    SensorEventListener sensorEventListenerAccelerometer;



    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListenerAccelerometer);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onStart() {
        super.onStart();
        ((AnimationDrawable) findViewById(R.id.text1).getBackground()).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        distance = getIntent().getDoubleExtra("distance", 42.0);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        findViewById(R.id.text1).setBackgroundResource(R.drawable.fangst);
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
            sensorEventListenerAccelerometer = new SensorEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("NewApi")
            @Override
            public void onSensorChanged(SensorEvent event) {
                double acc = calculateAcc(event.values, grav);
                if(acc > 3 && (state == State.FALSE || state == State.WAIT)) {
                    vibrator.vibrate(vibrationEffectEnd);
                    state = State.ENDED;
                    goToFail(true);
                }
                else if (state == State.FALSE) {
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
                            setFalse();
                        }
                        else {
                            state = State.REAL;
                            setReal();
                        }
                    }
                }
                else if (state == State.REAL) {
                    if(acc > 3) {
                        state = State.ENDED;
                        startReel();
                    }
                    else if(System.currentTimeMillis() > realFinish){
                        state = State.ENDED;
                        goToFail(false);
                    }
                    if (System.currentTimeMillis() - timeSinceVibration > 100) {
                        vibrator.vibrate(vibrationEffectReal);
                        timeSinceVibration = System.currentTimeMillis();
                    }
                }
                else{
                    vibrator.cancel();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

        };
        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerGravity, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
        TimerTask task = new TimerTask(){
            public void run(){
                startGame2();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    public void startGame1(android.view.View view) {
        setGoalFalse();
        this.state = State.WAIT;
        gameType = 1;
        passedFalse = 0;
        vibrationEffectEnd = VibrationEffect.createOneShot(200, 255);
        setReal();
        setFalse();
        vibrator.vibrate(vibrationEffectFalse);
        timeSinceVibration = System.currentTimeMillis();
        setWaitTime();
        setGoalFalse();
    }

    public void startGame2() {
        setGoalFalse();
        passedFalse = 0;
        vibrationEffectEnd = VibrationEffect.createOneShot(200, 255);
        setReal();
        setFalse();
        timeSinceVibration = System.currentTimeMillis();
        setWaitTime();
        setGoalFalse();
        this.state = State.WAIT;
    }

    public void startGame3(android.view.View view) {
        setGoalFalse();
        this.state = State.WAIT;
        gameType = 3;
        passedFalse = 0;
        vibrationEffectEnd = VibrationEffect.createOneShot(200, 255);
        setReal();
        setFalse();
        vibrator.vibrate(vibrationEffectFalse);
        timeSinceVibration = System.currentTimeMillis();
        setWaitTime();

    }

    private void setWaitTime(){
        /**if(gameType == 1) {
            timeToWait = System.currentTimeMillis() + 1000 + r.nextInt(5)*100;
        }
        else if(gameType == 2) {
            timeToWait = System.currentTimeMillis() + 1000 + r.nextInt(30)*100;
        }
        else{*/
            timeToWait = System.currentTimeMillis() + 1000 + r.nextInt(30)*100;
        //}
    }

    private void setGoalFalse() {
        goalFalse = 1 + r.nextInt(3);
    }

    public void setFalse() {
        /**if(gameType == 1) {
            falseFinish = System.currentTimeMillis() + 1000;
            vibrationEffectFalse = VibrationEffect.createOneShot(50, 100);
        }
        else if(gameType == 2) {*/
            falseFinish = System.currentTimeMillis() + 300;
            vibrationEffectFalse = VibrationEffect.createOneShot(200, 75);
        /**}
        else{
            falseFinish = System.currentTimeMillis() + 300 + r.nextInt(3)*100;
            vibrationEffectFalse = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        }*/
    }

    public void setReal() {
        /**if(gameType == 1) {
            realFinish = System.currentTimeMillis() + 1000;
            vibrationEffectReal = VibrationEffect.createOneShot(100, 255);
        }
        else if(gameType == 2) {*/
            realFinish = System.currentTimeMillis() + 800 + r.nextInt(5)*100;
            vibrationEffectReal = VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE);
        /**}
        else{
            realFinish = System.currentTimeMillis() + 1000 + r.nextInt(5)*100;
            vibrationEffectReal = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        }*/
    }

    private double calculateAcc(float[] acc, float[] grav) {
        return Math.sqrt(Math.pow((double) acc[0] - grav[0], 2) + Math.pow((double) acc[1] - grav[1], 2) + Math.pow((double) acc[2] - grav[2], 2));
        //return Math.sqrt(Math.pow((double) acc[0], 2) + Math.pow((double) acc[1], 2) + Math.pow((double) acc[2], 2));
        //return (double) acc[0] - grav[0] + acc[1] - grav[1] + acc[2] - grav[2];
    }
    public void startReel() {
        vibrator.cancel();
        sensorManager.unregisterListener(sensorEventListenerAccelerometer);
        Intent i = new Intent(this, ReelActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("distance", distance);
        startActivity(i);
    }

    public void goToFail(boolean early){
        vibrator.cancel();
        sensorManager.unregisterListener(sensorEventListenerAccelerometer);
        Intent i = new Intent(this, CatchFail.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(early){
            i.putExtra("failMsg", "Ojdå! Du ryckte för tidigt och skrämmde fisken!");
        }
        else {
            i.putExtra("failMsg", "Fisken åt ditt bete och stack!");
        }
        startActivity(i);
    }
}
