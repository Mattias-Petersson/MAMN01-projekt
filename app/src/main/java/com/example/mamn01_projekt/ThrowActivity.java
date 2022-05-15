package com.example.mamn01_projekt;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class ThrowActivity extends GameActivities {

    private SensorManager SensorManage;
    private Sensor mAccelerometer;
    private double distance;
    private double flightTime =3;
    private Vibrator v;
    private String[] direction = {null, null};
    private String state;
    private AnimationDrawable tutorialAnimation;
    private SensorEventListener thrower;
    //private List<Float> xList = new LinkedList<>(); //stack to save the state at X-axle
    private MediaPlayer reelSound;

    @Override
    protected void onStart() {
        super.onStart();
        tutorialAnimation.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SensorManage.unregisterListener(thrower);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reel_instruction);
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = SensorManage.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        TextView instruction =findViewById(R.id.descriptionText);
        instruction.setBackgroundResource(R.drawable.kast);
        tutorialAnimation = (AnimationDrawable) instruction.getBackground();
        reelSound = MediaPlayer.create(this,R.raw.reelsound3s);


        thrower = new SensorEventListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor mySensor = event.sensor;
                if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    float xValue = event.values[0];
                    float yValue = event.values[1];
                    float zValue = event.values[2];
                    float xAcc = Math.abs(event.values[0]);

                    if(xValue < -0.5) {
                        direction[0] = "Right";

                    } else if (xValue > 0.5) {
                        direction[0] = "Left";

                    } else {
                        direction[0] = null;
                    }
                    // Gives direction depending on y-axis value
                    if(yValue < -0.5) {
                        direction[1] = "Down";
                    } else if (yValue > 0.5) {
                        direction[1] = "Up";
                    } else {
                        direction[1] = null;
                    }
                    if(checkStateChange(direction[0])  && xAcc >10){
                        distance= (float) (0.5*xAcc*flightTime*flightTime);
                        reelSound.start();
                        v.vibrate(VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE));
                        TimerTask task = new TimerTask(){
                            public void run(){
                                switchLayout();
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 3000);



                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SensorManage.registerListener(thrower, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private boolean checkStateChange(String direction){
        if(state != direction){
            state =direction;
            return true;
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void switchLayout(){
        Intent intent = new Intent(this, CatchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("distance", distance);
        startActivity(intent);
    }
}