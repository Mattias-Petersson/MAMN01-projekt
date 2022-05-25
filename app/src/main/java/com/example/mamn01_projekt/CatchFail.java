package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CatchFail extends GameActivities {
    TextView text;
    Button retry;
    String failMsg;
    ImageButton btnExit;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_fail);
        failMsg = getIntent().getStringExtra("failMsg");
        btnExit = findViewById(R.id.btnCloseCatchFail);
        btnExit.setOnTouchListener((v, e) -> exitGame());
        text = findViewById(R.id.textView);
        text.setText(failMsg);
        retry = findViewById(R.id.button);
    }

    public void retry(android.view.View view) {
        Intent i = new Intent(this, ThrowActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}