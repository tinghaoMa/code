package com.mth.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mth.viewdemo.R;


public class CustomViewGroup extends LinearLayout {
    private TextView textView;

    public CustomViewGroup(Context context) {
        this(context,null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        System.out.println("segg6575----CustomViewGroup.onFinishInflate");
    }
}
