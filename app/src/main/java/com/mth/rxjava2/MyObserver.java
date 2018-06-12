package com.mth.rxjava2;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyObserver<T> implements Observer<T> {

    private static final String TAG = "segg6575";

    public MyObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "======================onSubscribe");
    }

    @Override
    public void onNext(T o) {
        Log.d(TAG, "======================onNext " + o);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "======================onError");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "======================onComplete");
    }
}
