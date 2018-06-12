package com.mth.myrxjava;

public class MaybeMap extends Maybe {
    final Function mapper;
    final MaybeSource source;

    public MaybeMap(MaybeSource source, Function mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(MaybeObserver observer) {
        MapMaybeObserver maybeObserver = new MapMaybeObserver(observer, mapper);
        source.subscribe(maybeObserver);
    }

    static final class MapMaybeObserver implements MaybeObserver {
        final MaybeObserver actual;

        final Function mapper;

        MapMaybeObserver(MaybeObserver actual, Function mapper) {
            this.actual = actual;
            this.mapper = mapper;
        }

        @Override
        public void onSuccess(int value) {
            int apply = this.mapper.apply(value);
            this.actual.onSuccess(apply);
        }
    }
}