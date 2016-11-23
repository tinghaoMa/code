package com.mth.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;



public class MyView extends View {
    private Paint mPaint;
    private Path path;
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setPathEffect(new CornerPathEffect(50));

        path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(320, 380);
        path.lineTo(80, 550);

        path.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    float aFloat;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getMeasuredWidth()/2,getMeasuredHeight()/2);
        canvas.scale(aFloat,aFloat);
        canvas.drawPath(path,mPaint);
        canvas.restore();
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(aFloat>=1)
                        break;
                    aFloat = aFloat + 0.01f;
                    System.out.println("segg6575----MyView.run aFloat="+aFloat);
                    postInvalidate();
                }
            }
        }).start();
    }
}
