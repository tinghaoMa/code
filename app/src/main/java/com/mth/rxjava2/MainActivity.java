package com.mth.rxjava2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mth.bean.Person;
import com.mth.bean.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "segg6575";
    private Integer i = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) {
//                Log.d(TAG, "======currentThread name: " + Thread.currentThread().getName());
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onComplete();
//            }
//        });
//
//
//        observable.subscribe(new MyObserver());
//
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) {
//                e.onNext("a");
//                e.onNext("b");
//                e.onNext("c");
//                e.onComplete();
//
//            }
//        }).subscribe(new MyObserver<String>());
//
//
//        Observable.just(1, 2, 3).subscribe(new MyObserver<Integer>());
//
//        Integer[] array = {11, 22, 33};
//        Observable.fromArray(array).subscribe(new MyObserver<Integer>());
//
//        Observable.fromCallable(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return 1;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println("segg6575---integer = " + integer);
//            }
//        });
//
//
//        final FutureTask<String> task = new FutureTask<>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "hello RxJava2";
//            }
//        });
//        Observable.fromFuture(task).doOnSubscribe(new Consumer<Disposable>() {
//            @Override
//            public void accept(Disposable disposable) throws Exception {
//                task.run();
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("segg6575---s = " + s);
//            }
//        });
//
//
//        List<String> datas = new ArrayList<>();
//        datas.add("fromIterable a");
//        datas.add("fromIterable b");
//        datas.add("fromIterable c");
//        datas.add("fromIterable d");
//
//        Observable.fromIterable(datas).subscribe(new MyObserver<String>());
//
//
//        Observable<Integer> deferObservable = Observable.defer(new Callable<ObservableSource<?
//                extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> call() throws Exception {
//                return Observable.fromArray(i);
//            }
//        });
//
//        MyObserver<Integer> observer = new MyObserver<>();
//        deferObservable.subscribe(observer);
//        i = 200;
//        deferObservable.subscribe(observer);
//        i = 300;
//        deferObservable.subscribe(observer);
//
//
//        Observable.timer(2, TimeUnit.SECONDS).subscribe(new MyObserver<Long>());
//        Observable.interval(4,TimeUnit.SECONDS).subscribe(new MyObserver<Long>());
//
//        Observable.intervalRange(2, 5, 0, 1, TimeUnit.SECONDS)
//                .subscribe(new MyObserver<Long>());
//
//
//        Observable.range(0,6).subscribe(new MyObserver<Integer>());

//        Observable.empty().subscribe(new MyObserver());
//        Observable.never().subscribe(new MyObserver());
//        Observable.error(new Callable<Throwable>() {
//            @Override
//            public Throwable call() throws Exception {
//                return new RuntimeException("error");
//            }
//        }).subscribe(new MyObserver());


//        List<Integer> datas = new ArrayList<>();
//        datas.add(1);
//        datas.add(2);
//        datas.add(3);
//        Observable.fromIterable(datas).map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return "after map " + integer;
//            }
//        }).subscribe(new MyObserver<String>());


//        List<Plan> plans = new ArrayList<>();
//        plans.add(new Plan("1", "content 1"));
//        plans.add(new Plan("2", "content 2"));
//
//        List<Plan> plans2 = new ArrayList<>();
//        plans2.add(new Plan("1", "content 111111"));
//        plans2.add(new Plan("2", "content 2222222"));
//
//        Person person = new Person("mth", plans);
//        Person person1 = new Person("mth1", plans2);
//
//        final List<Person> personList = new ArrayList<>();
//        personList.add(person);
//        personList.add(person1);
//
//        Observable.fromIterable(personList).concatMap(new Function<Person,
// ObservableSource<Plan>>() {
//            @Override
//            public ObservableSource<Plan> apply(Person person) {
//                if ("mth".equals(person.getName())) {
//                    return Observable.fromIterable(person.getPlanList()).delay(10, TimeUnit
//                            .SECONDS);
//                }
//                return Observable.fromIterable(person.getPlanList());
//            }
//        }).subscribe(new Observer<Plan>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Plan plan) {
//                Log.d(TAG, "==================plan " + plan.getContent());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//

//
//        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).
//                buffer(2, 3).
//                flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
//                    @Override
//                    public ObservableSource<Integer> apply(List<Integer> integers) throws
// Exception {
//                        return Observable.fromIterable(integers);
//                    }
//                }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("segg6575---integer = " + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


//        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).groupBy(new Function<Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer) throws Exception {
//                return integer % 3;
//            }
//        }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(final GroupedObservable<Integer, Integer>
//                                       integerIntegerGroupedObservable) {
//                integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.d(TAG, "====================GroupedObservable onNext  groupName: " +
//                                integerIntegerGroupedObservable.getKey() + " value: " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//
//        Observable.just(1, 2, 3, 4, 5).scan(new BiFunction<Integer, Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer, Integer integer2) throws Exception {
//                System.out.println("segg6575----apply-----integer = " + integer + " integer2 =
// " + integer2);
//
//                return integer+integer2;
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("segg6575---integer = " + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("1");
//                emitter.onNext("2");
//                emitter.onNext("3");
//            }
//        }).doOnEach(new Consumer<Notification<String>>() {
//            @Override
//            public void accept(Notification<String> stringNotification) throws Exception {
//                String value = stringNotification.getValue();
//                System.out.println("segg6575---MainActivity.accept  doOnEach value = " + value);
//            }
//        }).doOnNext(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("segg6575---MainActivity.accept   doOnNext value =  " + s);
//            }
//        }).doAfterNext(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("segg6575---MainActivity.accept  doAfterNext  value =" + s);
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("segg6575---s = " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Exception("404"));
                e.onNext(4);
            }
        }).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {
                System.out.println("segg6575---MainActivity.getAsBoolean  i = " + i);
                if (i == 6) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "==================onSubscribe ");
            }

            @Override
            public void onNext(Integer integer) {
                i += integer;
                Log.d(TAG, "==================onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "==================onError ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "==================onComplete ");
            }
        });
    }
}
