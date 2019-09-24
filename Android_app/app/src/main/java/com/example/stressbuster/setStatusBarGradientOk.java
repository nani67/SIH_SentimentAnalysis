package com.example.stressbuster;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;

public class setStatusBarGradientOk {

    public static void setStatusBarGradient(Activity activity) {
        Window window = activity.getWindow();
        Drawable background = activity.getResources().getDrawable(R.drawable.gradient_for_welcome);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

    }
}
