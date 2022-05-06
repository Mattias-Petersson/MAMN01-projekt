package com.example.mamn01_projekt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
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
    Random r = new Random();
    Handler mHandler;
    long timeWaiting = 0;
    long timeSinceVibration = 0;
    long falseFinish= 0;
    long realFinish = 0;
    int goalFalse = 1;
    int passedFalse = 0;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.state = State.ENDED;
        mHandler = new Handler();
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sensorEventListenerAccelerometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (state == State.FALSE) {
                    if(System.currentTimeMillis() > falseFinish){
                        state = State.WAIT;
                        passedFalse += 1;
                        timeWaiting = System.currentTimeMillis();
                    }
                    if (System.currentTimeMillis() - timeSinceVibration > 100) {
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
                    if(System.currentTimeMillis() > realFinish){
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startGame(android.view.View view) {
        this.state = State.WAIT;
        vibrationEffect1 = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        timeSinceVibration = System.currentTimeMillis();
        vibrator.vibrate(vibrationEffect1);
        timeWaiting = System.currentTimeMillis();
    }

    /*
    idleGame should vibrate every second (can be changed) +/- some delay with one fake-bite
    and one real-bite.
     */
    private void idleGame() {

        //vibrator.vibrate(vibrationEffect1);
        //long delayInMs = r.nextInt(200) -100;
        //mHandler.postDelayed(UpdateRunnable, 1000 + delayInMs);
    }


    Runnable UpdateRunnable = new Runnable() {
        @Override
        public void run() {
            idleGame();
        }
    };

}
