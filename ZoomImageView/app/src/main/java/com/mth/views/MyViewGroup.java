package com.mth.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MyViewGroup extends ViewGroup {
    private ViewDragHelper mDragger;
    private View mDragView;
    private int width;
    public MyViewGroup(Context context) {
        this(context,null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initDrager();
    }

    private void init(Context context) {
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
    }

    private void initDrager() {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                System.out.println("segg6575----MyViewGroup.tryCaptureView---"+(child==mDragView));
                return child==mDragView;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView.getHeight();
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                System.out.println("segg6575---MyViewGroup.onEdgeDragStarted");
                mDragger.captureChildView(mDragView,pointerId);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    /**
     * {@inheritDoc}
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        System.out.println("segg6575----onLayout---childCount = " + childCount);
        for (int count = 0; count < childCount; count++) {
            View childAt = getChildAt(count);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            System.out.println("segg6575---MyViewGroup.onLayout height="+childAt.getMeasuredHeight());
            System.out.println("segg6575---MyViewGroup.onLayout width="+childAt.getMeasuredWidth());
            System.out.println("segg6575---layoutParams.leftMargin="+layoutParams.leftMargin);
            childAt.layout(layoutParams.leftMargin,layoutParams.topMargin,childAt.getMeasuredWidth(),childAt.getMeasuredHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        System.out.println("segg6575----onmeasure---childCount = " + childCount+"----width="+width);
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int a = getChildMeasureSpec(widthMeasureSpec, 30, width);
            int b = getChildMeasureSpec(heightMeasureSpec, 30, width);
//            measureChild(childAt,a,b);
            measureChildWithMargins(childAt,a,30,b,30);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();
            int leftMargin = layoutParams.leftMargin;
            System.out.println("segg6575----aaaaaaaaaaa----leftMargin = " + leftMargin);
        }

//        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

}