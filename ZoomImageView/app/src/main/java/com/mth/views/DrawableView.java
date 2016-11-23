package com.mth.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class DrawableView extends ImageView {
    private GradientDrawable gd;
    private GradientDrawable mDrawable;

    public DrawableView(Context context) {
        this(context, null);
    }


    public DrawableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    private void init() {
        gd = new GradientDrawable();
        gd.setColor(Color.RED);
        gd.setSize(300, 300);
        System.out.println("segg6575---gd.getIntrinsicWidth() = " + gd.getIntrinsicWidth());
        mDrawable = new GradientDrawable();
        mDrawable.setColor(Color.BLUE);
        mDrawable.setSize(400, 400);
        System.out.println("segg6575---mDrawable.getIntrinsicWidth = " + mDrawable.getIntrinsicWidth());
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{gd,mDrawable});
        layerDrawable.setLayerInset(0,100,200,100+gd.getIntrinsicWidth(),200+gd.getIntrinsicHeight());
        layerDrawable.setLayerInset(1,200,300,200+mDrawable.getIntrinsicWidth(),300+mDrawable.getIntrinsicHeight());
        setImageDrawable(layerDrawable);
    }

}
