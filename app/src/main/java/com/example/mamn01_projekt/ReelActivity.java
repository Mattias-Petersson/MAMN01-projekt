package com.example.mamn01_projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;
import android.os.VibrationEffect;
import java.util.*;

public class ReelActivity extends AppCompatActivity {

    private ImageView reelImage;
    private TextView text,distanceText;
    private double currentAngle = 0;
    private int counter = 0;
    private boolean pointingUp = true;
    private boolean pointingDown = false;
    private SensorManager SensorManage;
    private Sensor mAccelerometer;
    private double distance;
    private double flightTime = 2;
    private Vibrator v;
    private String[] direction = {null, null};
    private String state;
    //private List<Float> xList = new LinkedList<>(); //stack to save the state at X-axle


    

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reel_instruction);
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = SensorManage.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        SensorManage.registerListener(new SensorEventListener() {
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
        distanceText= findViewById(R.id.distance);
    }



    private boolean checkStateChange(String direction){
        if(state !=direction){
            state =direction;
            return true;
        }
        return false;
    }
    private double getAngle(double x, double y) {

        double xCenter = reelImage.getWidth() / 2.0;
        double yCenter = reelImage.getHeight() / 2.0;
        return Math.toDegrees(Math.atan2(x - xCenter, yCenter - y));
    }

    private void animate(double fromDegrees, double toDegrees) {
        RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(0);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        reelImage.startAnimation(rotate);
    }

    //TODO ska man kunna snurra åt båda hållen eller är ett bakåt och ett framåt? Just nu kan man snurra åt båda
    private void checkLaps(double angle) {
        angle = (angle + 360) % 360; // Gets the angle in 0..359 instead of -180..180.
        if (angle < 30 || angle > 330)
            pointingUp = true;
        // Reset the pointingUp so that users have to go down then up and not only register halves.
        if ((angle < 120 && angle > 50) || (angle < 210 && angle > 150))
            pointingUp = false;
        if (angle < 210 && angle > 150)
            pointingDown = true;

        if (pointingUp && pointingDown) {
            counter++;
            pointingUp = pointingDown = false;
        }
        text.setText("Antal varv snurrade: " + counter);
    }
    private void switchLayout(){
        setContentView(R.layout.activity_reel);
        reelImage = findViewById(R.id.imageReel);
        text = findViewById(R.id.textView);
        distanceText = findViewById(R.id.distance);
        reelImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(distance<=0){ 
                    distanceText.setText("Distance: 0");

                }
                
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        reelImage.clearAnimation();
                        currentAngle = getAngle(event.getX(), event.getY());


                        break;
                    case MotionEvent.ACTION_MOVE:
                        double startAngle = currentAngle;
                        checkLaps(currentAngle);
                        currentAngle = getAngle(event.getX(), event.getY());
                        if(currentAngle-startAngle<-180){
                            distance = distance- ((360+(currentAngle-startAngle))*0.01);
                        }else if(currentAngle-startAngle>180){
                            distance =  distance+ ((360-(currentAngle-startAngle))*0.01);
                        }else{
                            distance =  distance- ((currentAngle-startAngle)*0.01);
                        }

                        if(distance>0){
                            distanceText.setText("Distance: "+ Double.toString(distance));
                        }
                        animate(startAngle, currentAngle);
                        break;
                }
                return true;
            }
        });
    }
}



