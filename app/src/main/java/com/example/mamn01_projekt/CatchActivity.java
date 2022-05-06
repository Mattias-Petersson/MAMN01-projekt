package com.example.mamn01_projekt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CatchActivity extends AppCompatActivity implements SensorEventListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.state = State.ENDED;
        mHandler = new Handler();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startGame(android.view.View view){
        this.state = State.WAIT;
        vibrationEffect1 = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);
        idleGame();
    }
/*
idleGame should vibrate every second (can be changed) +/- some delay with one fake-bite
and one real-bite. 
 */
    private void idleGame() {
        vibrator.vibrate(vibrationEffect1);
        int delayInMs = r.nextInt(200) - 100; // Should create a delay of -100 to 100 ms.
        mHandler.postDelayed(UpdateRunnable, 1000 + delayInMs);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if(state != State.FALSE){
            //Falsk bett, drar man nu misslyckas fisket

        }
        //  else if(state != State.REAL){
        //Riktigt bett på kroken, dra nu för att komma till ReelActivity!

        //  }
        else if(state == State.WAIT){

            //vibrator.cancel();
            //vibrator.vibrate(vibrationEffect1);
            state = State.ENDED;

        }


    }
    Runnable UpdateRunnable = new Runnable() {
        @Override
        public void run() {
            idleGame();
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //not used
    }
}