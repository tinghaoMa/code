package com.mth.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;


public class ColorView extends View {

    public ColorView(Context context) {
        this(context,null);
    }

    public ColorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int a = MeasureSpec.makeMeasureSpec(30, MeasureSpec.getMode(widthMeasureSpec));
        int b= MeasureSpec.makeMeasureSpec(30, MeasureSpec.getMode(heightMeasureSpec));

        setMeasuredDimension(a,b);
//    setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
