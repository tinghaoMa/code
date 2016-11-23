package com.mth.views;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        View.OnTouchListener {
    private boolean mOnce;

    private float mInitScale;//初始化时缩放的值
    private float mMidScale; //双击放大的最大值
    private float mMaxScale;  //放大的最大值

    private Matrix mScaleMartrix;
    //捕获用户多指触控
    private ScaleGestureDetector mScaleGestureDetector;

    private int mTouchSlop;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(ScaleType.MATRIX);
        init(context);
    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMartrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }


    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mScaleMartrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector
                .SimpleOnScaleGestureListener() {

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = getScale();
                float scaleFactor = detector.getScaleFactor();//缩放的值 大于1 想放大 小于1 想缩小
                if (getDrawable() == null) {
                    return true;
                }
                //1.比最大的小还想放大  2.比最小的大还想缩小
                if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale &&
                        scaleFactor < 1.0f)) {

                    if (scale * scaleFactor < mInitScale) { //小于最小
                        scaleFactor = mInitScale / scale;
                    }
                    if (scale * scaleFactor > mMaxScale) { //大于最大
                        scaleFactor = mMaxScale / scale;
                    }

                    mScaleMartrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(),
                            detector.getFocusY());
                    checkBorderAndCenterWhenScale();
                    setImageMatrix(mScaleMartrix);
                }

                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                super.onScaleEnd(detector);

            }
        }

        );
        setOnTouchListener(this);
    }

    /**
     * 检查边界控制以及位置控制
     * rectF.width() 放大后的宽高
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectf();
        System.out.println("segg6575---rectF = " + rectF);
        float detalX = 0;
        float detalY = 0;

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (rectF.width() >= width) {//大于屏幕宽度
            if (rectF.left > 0) {
                detalX = -rectF.left;
            }

            if (rectF.right < width) {
                detalX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) { //大于屏幕高度
            if (rectF.top > 0) {
                detalY = -rectF.top;
            }

            if (rectF.bottom < height) {
                detalY = height - rectF.bottom;
            }
        }
        //如果宽度或者高度小于控件的宽或者高 则让其居中

        if (rectF.width() < width) {
            detalX = width / 2 - rectF.right + rectF.width() / 2;
        }

        if (rectF.height() < height) {
            detalY = height / 2 - rectF.bottom + rectF.height() / 2;
        }

        mScaleMartrix.postTranslate(detalX, detalY);
    }

    /**
     * 得到缩放后的图片的宽高坐标
     * <p>
     * 首先得到图片的宽为width,高为height,你使用的matrix为m:
     * RectF rect = new RectF(0, 0, width, height);
     * m.mapRect(rect);
     * 这样rect.left，rect.right,rect.top,rect.bottom分别就就是当前屏幕离你的图片的边界的距离。
     * 而这样你要的点（0，0）其实就是界面的左上角（rect.left，rect.top）
     *
     * @return
     */
    private RectF getMatrixRectf() {
        Matrix matrix = mScaleMartrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("segg6575---ZoomImageView.onSizeChanged");
    }


    @Override
    public void onGlobalLayout() {
        System.out.println("segg6575---ZoomImageView.onGlobalLayout");
        if (!mOnce) {
            Drawable drawable = getDrawable();
            int dw = drawable.getIntrinsicWidth();//照片宽度
            int dh = drawable.getIntrinsicHeight();//照片高度
            System.out.println("segg6575---dw  = " + dw + "--------dh=" + dh);

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            System.out.println("segg6575---width = " + width + "-----height=" + height);
            float scale = 1.0f;
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }

            if (dw < width && dh > height) {
                scale = height * 1.0f / dh;
            }

            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            System.out.println("segg6575---scale = " + scale);
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;
            //将图片移植图片中心

            int detalX = width / 2 - dw / 2;
            int detalY = height / 2 - dh / 2;
            mScaleMartrix.postTranslate(detalX, detalY);

            mScaleMartrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMartrix);

            mOnce = true;
        }
    }

    //自由移动
    private int mLastPointerCount;
    private float mLastX;
    private float mLastY;
    private boolean isCanDrag;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        int pointerCount = event.getPointerCount();
        float x = 0;
        float y = 0;

        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;
        if (mLastPointerCount != pointerCount) {
            mLastX = x;
            mLastY = y;
            isCanDrag = false;
        }

        mLastPointerCount = pointerCount;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {
                    RectF rectf = getMatrixRectf();
                    if(rectf.width()<getWidth()){
                        dx = 0;
                    }

                    if(rectf.height()<getHeight()){
                        dy = 0;
                    }

                    mScaleMartrix.postTranslate(dx, dy);
                    checkBorderAndCenterWhenScale();
                    setImageMatrix(mScaleMartrix);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
        }

        return true;
    }

    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }
}
