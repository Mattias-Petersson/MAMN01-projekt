package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class CaughtActivity extends AppCompatActivity {
    private ImageView fishImage;
    private TextView fishName, fishStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caught);
    }
    @SuppressLint("ClickableViewAccessibility")
    public void catchFishPopup(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.popup_catch, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // Set focusable to true when testing, false when the application is done. The user should
        // Only be able to proceed by clicking buttons, false does that.
        final PopupWindow popupWindow = new PopupWindow(popup, width, height, true);
        getFields(popup);
        printFish();
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    private void getFields(View popup) {
        fishName =  popup.findViewById(R.id.textCaughtFishName);
        fishStats = popup.findViewById(R.id.textCaughtFishAttributes);
        fishImage = popup.findViewById(R.id.imageFish);
    }
    private void printFish() {
        Fish f = new FishHandler().returnCatch();
        fishName.setText(f.name);
        fishName.setTextColor(f.primaryColor);
        fishStats.setText("Weight: " + f.weight + " kg" + "\n" + "Length: " + f.length + " cm");
        fishImage.setImageResource(f.getImageSource());

    }
    public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }
}