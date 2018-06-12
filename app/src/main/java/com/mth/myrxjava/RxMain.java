package com.mth.myrxjava;

/**
 * 自己里面有自己 循环赋值
 */
public class RxMain {

    public static void main(String[] args) {


        Maybe map = Maybe
                .just(1)
                .map(new Function() {

                    @Override
                    public int apply(int t) {
                        return t + 1;
                    }
                }).map(new Function() {

                    @Override
                    public int apply(int t) {
                        return t * 4;
                    }
                });



        map.subscribe(new MaybeObserver() {

            @Override
            public void onSuccess(int value) {
                System.out.println(value);
            }
        });

    }
}