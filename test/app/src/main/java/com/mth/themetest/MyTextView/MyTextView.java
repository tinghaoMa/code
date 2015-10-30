package com.mth.themetest.MyTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.mth.themetest.R;


/**
 * Created by tinghaoma on 2015/10/29.
 * 自定义属性2种方式获取
 */
public class MyTextView extends TextView {
    /**
     * style第一步
     */
    private static final  int ATTRS[]= new int[]{
            R.attr.myTextColor,
            R.attr.myTextSize,
            R.attr.isEdit
    };
    /**
     * style第二步
     */
    private static final int ATTR_TEXTCOLOR = 0;
    private static final int ATTR_TEXTSIZE = 1;
    private static final int ATTR_ISEDIT = 2;

    int color;
    float dimension;
    boolean isEdit;
    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
         xmlAttr(context, attrs, defStyleAttr);
//        styleAttr(context, attrs, defStyleAttr);
        setTextColor(color);
        setTextSize(dimension);
    }

    /**
     * 在主题里面声明的属性获取Style
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void styleAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(null, ATTRS, R.attr.mmm, 0);
        dimension= a.getDimensionPixelSize(ATTR_TEXTSIZE, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 11, getResources().getDisplayMetrics()));
        Log.e("MTH", dimension + "");
        color = a.getColor(ATTR_TEXTCOLOR, context.getResources().getColor(R.color.GREEN));
        isEdit = a.getBoolean(ATTR_ISEDIT, false);
        Log.e("MTH", isEdit + "");
        a.recycle();
    }

    /**
     * 获取XMl里面的自定义属性 只有在Activity那里写android:theme="@style/My_Style"才行
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void xmlAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0);
        dimension = ta.getDimensionPixelSize(R.styleable.MyTextView_myTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 11, getResources().getDisplayMetrics()));
        Log.e("MTH", dimension + "");
        color = ta.getColor(R.styleable.MyTextView_myTextColor, context.getResources().getColor(R.color.GREEN));
        isEdit = ta.getBoolean(R.styleable.MyTextView_isEdit, false);
        Log.e("MTH", isEdit + "");
        ta.recycle();
    }

}
