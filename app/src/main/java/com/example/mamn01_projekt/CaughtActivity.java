package com.example.mamn01_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
        final PopupWindow popupWindow = new PopupWindow(popup, width, height, true);
        getFields(popup);
        printFish(view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popup.setOnTouchListener( (v, e) -> { // View v and Event e respectively.
            popupWindow.dismiss();
            return true;
        });
    }
    private void getFields(View popup) {
        fishName = (TextView) popup.findViewById(R.id.textCaughtFishName);
        fishStats = (TextView) popup.findViewById(R.id.textCaughtFishAttributes);
        fishImage = (ImageView) popup.findViewById(R.id.imageFish);
    }
    public void printFish(View v) {
        Fish f = new FishHandler().returnCatch();
        fishName.setText(f.name);
        fishStats.setText("Weight: " + f.weight + " kg" + "\n" + "Length: " + f.length + " cm");
        fishImage.setImageResource(f.getImageSource());

    }
}