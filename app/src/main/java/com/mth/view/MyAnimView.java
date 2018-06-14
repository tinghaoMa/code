package com.mth.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * ValueAnimator 高级用法
 */
public class MyAnimView extends View {
    private static final int RADIUS = 50;

    private Point mPoint;
    private Paint mPaint;

    private boolean startedAnim = false;

    public MyAnimView(Context context) {
        this(context, null);
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);

        mPoint = new Point(RADIUS, RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!startedAnim) {
            startAnimation();
            startedAnim = true;
        }
        drawCircle(canvas);
    }

    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(5000);
        anim.start();
    }

    private void drawCircle(Canvas canvas) {
        int x = mPoint.x;
        int y = mPoint.y;
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }


    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
            int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
            Point point = new Point(x, y);
            return point;
        }

    }
}
