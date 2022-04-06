package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class ReelActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView reelImage;
    private double currentAngle = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reel);
        reelImage = findViewById(R.id.imageReel);
        reelImage.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                reelImage.clearAnimation();
                currentAngle = getAngle(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                double startAngle = currentAngle;
                currentAngle = getAngle(event.getX(), event.getY());
                animate(startAngle, currentAngle);
                break;
        }
        return true;
    }
    private double getAngle(double x, double y) {
        double xc = reelImage.getWidth() / 2.0;
        double yc = reelImage.getHeight() / 2.0;
        return Math.toDegrees(Math.atan2(x - xc, yc - y));
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
}


