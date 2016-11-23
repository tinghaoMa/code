package com.mth.viewdemo;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.mth.views.A;
import com.mth.views.ColorView;
import com.mth.views.MyView;
import com.mth.views.MyViewGroup;

public class MainActivity extends Activity implements ViewTreeObserver.OnGlobalLayoutListener {
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;

        String model = Build.MODEL;
        String release = Build.VERSION.RELEASE;
        int sdkInt = Build.VERSION.SDK_INT;


        mView = findViewById(R.id.id_view);
        mView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int measuredWidth = mView.getMeasuredWidth();
                System.out.println("segg6575---main   measuredWidth = " + measuredWidth);
                int measuredHeight = mView.getMeasuredHeight();
                System.out.println("segg6575---main   measuredHeight = " + measuredHeight);
            }
        }, 1000);
    }

    /**
     * Callback method to be invoked when the global layout state or the visibility of views
     * within the view tree changes
     */
    @Override
    public void onGlobalLayout() {
        System.out.println("segg6575---MainActivity.onGlobalLayout");
    }
}
