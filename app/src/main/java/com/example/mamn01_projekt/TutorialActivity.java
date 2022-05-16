package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class TutorialActivity extends GameActivities {

    private TextView stepText;
    Vibrator vibrator;
    private ImageView gifView;
    private Button nextButton;
    VibrationEffect vibrationEffectFalse = VibrationEffect.createOneShot(300, 75);
    VibrationEffect vibrationEffectReal = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
    private String[] gifs;
    private int index;

    @Override
    protected void onStop(){
        super.onStop();
        vibrator.cancel();
        index = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        stepText = findViewById(R.id.step);
        gifView = findViewById(R.id.gifImageView2);
        nextButton = findViewById(R.id.button2);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        index = 0;
        gifs = new String[]{"throw_gif", "fight_gif", "nibble", "hooked", "reel_gif", "success_gif"};

        Glide.with(this).load(R.drawable.throw_gif).into(gifView);
        stepText.setText("Step 1: Throw out the fishing line");
    }

    public void nextStep(android.view.View view)  {
        index++;

        if (index == 1)  {
            Glide.with(this).load(R.drawable.fight_gif).into(gifView);
            stepText.setText("Step 2: Pull the fish");
        } else if (index == 2)  {
            Glide.with(this).load(R.drawable.nibble).into(gifView);
            stepText.setText("Don't pull when the fish nibbles, it feels like this");
            replayFalse();
        } else if (index == 3)  {
            Glide.with(this).load(R.drawable.hooked).into(gifView);
            stepText.setText("Do pull when the fish hooks on, it feels like this");
            replayReal();
        } else if (index == 4)  {
            Glide.with(this).load(R.drawable.reel_gif).into(gifView);
            stepText.setText("Step 3: Reel in the fish");
        } else if (index == 5)  {
            Glide.with(this).load(R.drawable.success_gif).into(gifView);
            stepText.setText("Step 4: Feel proud about your catch");
        } else if (index == 6)  {
            startActivity(new Intent(this, ThrowActivity.class));
        }
    }

    /* Fick inte detta att funka */
    public void setGif(int index)    {
        String path = "R.drawable." + gifs[index];
        Glide.with(this).load(R.drawable.throw_gif).into(gifView);
    }

    public void replayFalse(){
        if(index == 2){
            vibrator.vibrate(vibrationEffectFalse);
            TimerTask task = new TimerTask(){
                public void run(){
                    replayFalse();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 3000);
        }
    }

    public void replayReal(){
        if(index == 3){
            vibrator.vibrate(vibrationEffectReal);
            TimerTask task = new TimerTask(){
                public void run(){
                    replayReal();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 4000);
        }
    }


}