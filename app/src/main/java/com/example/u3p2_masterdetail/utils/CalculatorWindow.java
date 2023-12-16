package com.example.u3p2_masterdetail.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

public class CalculatorWindow {
    private final Context context;

    // Constructor to initialize the context
    public CalculatorWindow(Context context) {
        this.context = context;
    }

    // Method to calculate the height of the window based on a percentage of the screen height
    public int calculateHeight(double percentage) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // Check if the window manager is available
        if (windowManager != null) {
            // Get the display metrics
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            // Calculate the height based on the percentage of the screen height
            int screenHeight = displayMetrics.heightPixels;
            return (int) (screenHeight * percentage);
        }

        // Return WRAP_CONTENT if the window manager is not available
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}