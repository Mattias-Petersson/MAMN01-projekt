package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

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
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

public class ThrowActivity extends GameActivities {

    private SensorManager SensorManage;
    private Sensor mAccelerometer;
    private double distance;
    private double flightTime =3;
    private Vibrator v;
    private String[] direction = {null, null};
    private String state;
    private AnimationDrawable tutorialAnimation;
    //private List<Float> xList = new LinkedList<>(); //stack to save the state at X-axle


    @Override
    protected void onStart() {
        super.onStart();
        tutorialAnimation.start();
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


        SensorManage.registerListener(new SensorEventListener() {
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //Reel sound
                            v.vibrate(VibrationEffect.createOneShot((long)flightTime*100, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        switchLayout();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
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
        intent.putExtra("distance", distance);
        startActivity(intent);
    }
}