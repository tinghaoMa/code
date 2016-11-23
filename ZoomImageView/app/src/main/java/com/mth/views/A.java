package com.mth.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


public class A extends RelativeLayout {
    private Button button;

    public A(Context context) {
        this(context, null);
    }


    public A(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public A(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        button = new Button(context);
        MarginLayoutParams layoutParams = new MarginLayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 300;
        layoutParams.height = 200;
        button.setLayoutParams(layoutParams);
        button.setBackgroundColor(Color.BLUE);
        addView(button);
        setWillNotDraw(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("segg6575---A.onDraw");
        canvas.drawColor(Color.RED);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("segg6575---A.onSizeChanged");
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int finalI = i;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("segg6575---finalI = " + finalI);
                             button.setLeft(finalI *30);

//                            MarginLayoutParams layoutParams = (MarginLayoutParams) button
// .getLayoutParams();
//                            layoutParams.leftMargin = finalI * 30;
//                            button.setLayoutParams(layoutParams);

//                            button.setX(finalI*10);


                        }
                    });
                }


            }
        }).start();
    }
}
