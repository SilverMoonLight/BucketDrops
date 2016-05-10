package com.example.jonathan.bucketdrops.extra;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.List;

/**
 * Created by Jonathan on 5/9/2016.
 */
public class Utils {

    public static void showViews(List<View> views) {
        for (View view: views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views) {
        for (View view: views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setBackground(View itemView, Drawable drawable) {
        if (Build.VERSION.SDK_INT > 15) {
            itemView.setBackground(drawable);
        } else {
            itemView.setBackgroundDrawable(drawable);
        }
    }

}
