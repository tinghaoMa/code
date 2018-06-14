package com.mth.animdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author itck_mth
 */
public class MainActivity extends AppCompatActivity {
    private TextView textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.tv);


        //ViewPropertyAnimator 简单动画写法
        textview.animate().x(500).y(500).setDuration(5000);

    }

    public void playSet() {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", -500f, 0f);

        moveIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                System.out.println("segg6575---MainActivity.onAnimationStart");
            }
        });

        ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
    }

    public void valueAnim() {

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                System.out.println("segg6575---animatedValue = " + animatedValue);
            }
        });
        animator.start();

        //比如说将一个值在5秒内从0过渡到5，再过渡到3，再过渡到10
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 5f, 3f, 10f);
        anim.setDuration(5000);
        anim.start();


        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = (int) animation.getAnimatedValue();
                System.out.println("segg6575---i = " + i);
            }
        });
        valueAnimator.start();

    }


    public void objectAnim() {

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
        alphaAnimator.setDuration(3000);
        alphaAnimator.start();


        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
        rotationAnimator.setDuration(3000);
        rotationAnimator.start();


        float curTranslationX = textview.getTranslationX();
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(textview, "translationX",
                curTranslationX, -800f, curTranslationX);
        translationAnimator.setDuration(5000);
        translationAnimator.start();


        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(textview, "scaleY", 1f, 3f, 1f);
        scaleAnimator.setDuration(3000);
        scaleAnimator.start();
    }

}
