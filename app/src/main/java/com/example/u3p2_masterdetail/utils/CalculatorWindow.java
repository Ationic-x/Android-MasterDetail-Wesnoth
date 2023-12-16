package com.example.u3p2_masterdetail.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

public class CalculatorWindow {
    Context context;

    public CalculatorWindow(Context context){
        this.context = context;
    }
    public int calculateHeight(double percentage) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenHeight = displayMetrics.heightPixels;
            return (int) (screenHeight * percentage);
        }
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
