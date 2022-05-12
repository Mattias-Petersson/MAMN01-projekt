package com.example.mamn01_projekt;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ReelActivity extends GameActivities {

    private ImageView reelImage;
    private TextView distanceText;
    private double currentAngle = 0;
    private double distance;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reel);
        distance = getIntent().getDoubleExtra("distance", 42.0);
        reelImage = findViewById(R.id.imageReel);
        distanceText = findViewById(R.id.distance);
        initializeScreen();

    }

    private double getAngle(double x, double y) {
        double xCenter = reelImage.getWidth() / 2.0;
        double yCenter = reelImage.getHeight() / 2.0;
        return Math.toDegrees(Math.atan2(x - xCenter, yCenter - y));
    }

    private void animate(double fromDegrees, double toDegrees) {
        if(disableCCRotation(fromDegrees, toDegrees)) {
            RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(0);
            rotate.setFillEnabled(true);
            rotate.setFillAfter(true);
            reelImage.startAnimation(rotate);
        }
    }
    private boolean disableCCRotation(double fromDegrees, double toDegrees) {
    //    double theta = 180.0 / Math.PI * Math.atan2(fromDegreesX - toDegreesX, toDegreesX - fromDegreesX);
        return fromDegrees <= toDegrees;
    }
    private String showDistance() {
        double roundedDistance = Math.round(distance * 100d) / 100d;
        return "Distance: " + roundedDistance + " m";
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void initializeScreen() {
        distanceText.setText(showDistance());
        reelImage.setOnTouchListener((v, e) -> {
            if (distance <= 0) {
                distanceText.setText("Distance: 0 m");
                reelImage.setEnabled(false);
                new Caught(this).catchFishPopup(v);
            }

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    reelImage.clearAnimation();
                    currentAngle = getAngle(e.getX(), e.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    double startAngle = currentAngle;
                    currentAngle = getAngle(e.getX(), e.getY());
                    if (disableCCRotation(startAngle, currentAngle)) {
                        if (currentAngle - startAngle < -180) {
                            distance = distance - ((360 + (currentAngle - startAngle)) * 0.01);
                        } else if (currentAngle - startAngle > 180) {
                            distance = distance + ((360 - (currentAngle - startAngle)) * 0.01);
                        } else {
                            distance = distance - ((currentAngle - startAngle) * 0.01);
                        }
                        if(distance > 0) {
                            distanceText.setText(showDistance());
                        }
                        animate(startAngle, currentAngle);
                        break;
                    }
            }
            return true;
    });
    }
}



