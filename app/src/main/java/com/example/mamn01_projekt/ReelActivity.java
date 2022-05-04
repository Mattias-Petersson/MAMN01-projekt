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

public class ReelActivity extends AppCompatActivity {

    private ImageView reelImage;
    private TextView text,distanceText;
    private double currentAngle = 0;
    private int counter = 0;
    private boolean pointingUp = true;
    private boolean pointingDown = false;
    private SensorManager SensorManage;
    private Sensor mAccelerometer;
    private float distance;
    private double flightTime = 2;
    private Vibrator v;

    

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
                    float  xAcc = Math.abs(event.values[0]);
                    if(xAcc>12){
                        distance= (float) (0.5*xAcc*flightTime*flightTime);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot((long)flightTime*100, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        SensorManage.unregisterListener(this);
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
        angle = (angle + 360) % 360; // Gets the angle in 0..360 instead of -180..180.
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
                distanceText.setText("Distance: "+ Float.toString(distance));
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        reelImage.clearAnimation();
                        currentAngle = getAngle(event.getX(), event.getY());

                        break;
                    case MotionEvent.ACTION_MOVE:
                        double startAngle = currentAngle;
                        currentAngle = getAngle(event.getX(), event.getY());
                        checkLaps(currentAngle);
                        if(distance>=0){
                            distance =  (float)(distance- ((currentAngle-startAngle+360)%360)*0.01); //radie 10cm
                        }
                        animate(startAngle, currentAngle);
                        break;
                }
                return true;
            }
        });
    }
}



