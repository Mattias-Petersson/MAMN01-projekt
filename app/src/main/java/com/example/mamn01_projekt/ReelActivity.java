package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ReelActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView reelImage;
    private TextView text;
    private double currentAngle = 0;
    private int counter = 0;
    private boolean pointingUp = true;
    private boolean pointingDown = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reel);
        reelImage = findViewById(R.id.imageReel);
        text = findViewById(R.id.textView);
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
                checkLaps(currentAngle);
                animate(startAngle, currentAngle);
                break;
        }
        return true;
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
}


