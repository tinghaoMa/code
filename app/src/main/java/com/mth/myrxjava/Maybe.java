package com.mth.myrxjava;
public abstract class Maybe implements MaybeSource {

    public static Maybe just(int item) {
        return new MaybeJust(item);
    }

    public final Maybe map(Function mapper) {
        return new MaybeMap(this, mapper);
    }
}
