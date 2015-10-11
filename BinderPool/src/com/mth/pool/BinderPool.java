package com.mth.pool;

import android.os.IBinder;

public class BinderPool {
    private static BinderPool pool;

    private BinderPool() {
    }

    public static BinderPool getInstance() {
        if (pool == null) {
            synchronized (BinderPool.class) {
                if (pool == null) {
                    pool = new BinderPool();
                }
            }
        }
        return pool;
    }

    public IBinder queryBinder(int code) {
        return mListener.query(code);
    }

    private OnQueryBinderListener mListener;

    public interface OnQueryBinderListener {
        IBinder query(int code);
    }

    public void setmListener(OnQueryBinderListener mListener) {
        this.mListener = mListener;
    }
}
