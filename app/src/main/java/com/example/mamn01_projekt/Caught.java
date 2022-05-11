package com.example.mamn01_projekt;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Caught {
    private final Context context;
    public Caught(Context context) {
        this.context = context;
    }
    private ImageView fishImage;
    private TextView fishName, fishStats;
    private Button playAgain;

    @SuppressLint("ClickableViewAccessibility")
    public void catchFishPopup(View view) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.popup_catch, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // Set focusable to true when testing, false when the application is done. The user should
        // Only be able to proceed by clicking buttons, false does that.
        final PopupWindow popupWindow = new PopupWindow(popup, width, height, false);
        getFields(popup);
        printFish();
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void getFields(View popup) {
        fishName = popup.findViewById(R.id.textCaughtFishName);
        fishStats = popup.findViewById(R.id.textCaughtFishAttributes);
        fishImage = popup.findViewById(R.id.imageFish);
        playAgain = popup.findViewById(R.id.buttonPlayAgain);
        playAgain.setOnClickListener(v -> context.startActivity(new Intent(context, GameActivity.class)));
    }

    private void printFish() {
        Fish f = new FishHandler().returnCatch();
        fishName.setText(f.name);
        fishName.setTextColor(f.primaryColor);
        fishStats.setText("Weight: " + f.weight + " kg" + "\n" + "Length: " + f.length + " cm");
        fishImage.setImageResource(f.getImageSource());
    }
}