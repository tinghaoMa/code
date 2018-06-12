package com.mth.myrxjava;

public class MaybeJust extends Maybe {
    final int value;

    public MaybeJust(int value) {
        this.value = value;
    }

    @Override
    public void subscribe(MaybeObserver observer) {
        observer.onSuccess(value);
    }
}