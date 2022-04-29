package com.example.mamn01_projekt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class CatchActivity extends AppCompatActivity implements SensorEventListener {



    enum State {
        WAIT,
        FALSE,
        REAL,
        ENDED
    }
    private State state;
    //final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        this.state = State.ENDED;

    }

    public void startGame(android.view.View view){
        this.state = State.WAIT;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final VibrationEffect vibrationEffect1;
        if(state != State.FALSE){
            //Falsk bett, drar man nu misslyckas fisket

        }
        else if(state != State.REAL){
            //Riktigt bett på kroken, dra nu för att komma till ReelActivity!

        }
        else if(state != State.WAIT){
            //vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
            //vibrator.cancel();
            //vibrator.vibrate(vibrationEffect1);
            state = State.ENDED;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //not used
    }
}