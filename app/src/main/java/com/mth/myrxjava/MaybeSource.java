package com.mth.myrxjava;

public interface MaybeSource {
    void subscribe(MaybeObserver observer);
}
