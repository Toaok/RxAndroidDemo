package indi.toaok;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Toaok
 * @version 1.0  2019/2/27.
 */
public class RxAndroidTest {
    private final static String TAG = RxAndroidTest.class.getSimpleName() + ": ";

    @Test
    public void test() {
        //Observable create
        Observable<Integer> observableCreateInteger = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println(TAG + "subscribe");
                for (int i = 0; i < 10; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        });
        observableCreateInteger.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(TAG + this + " onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(TAG + this + " onNext is " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG + this + " onError");
            }

            @Override
            public void onComplete() {
                System.out.println(TAG + this + " onComplete");
            }
        });

        //Observable from Array
        Observable<Integer> observableFromArray = Observable.fromArray();
        observableFromArray.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(TAG + this + " onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(TAG + this + " onNext is " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG + this + " onError");
            }

            @Override
            public void onComplete() {
                System.out.println(TAG + this + " onComplete");
            }
        });

        //Observable from Callable
        Callable<Integer> printIntegerCallable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        };
        Observable<Integer> observablefromCallable = Observable.fromCallable(printIntegerCallable);
        observablefromCallable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(TAG + this + " onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(TAG + this + " onNext is " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG + this + " onError");
            }

            @Override
            public void onComplete() {
                System.out.println(TAG + this + " onComplete");
            }
        });

        //Observable from Future
        Future<List<Integer>> printIntegerFuture = new Future<List<Integer>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public List<Integer> get() throws InterruptedException, ExecutionException {
                List<Integer> data = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    data.add(i);
                }
                return data;
            }

            @Override
            public List<Integer> get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                if (unit.toSeconds(timeout) < 5) {
                    return get();
                } else {
                    cancel(Thread.currentThread().isInterrupted());
                    throw new TimeoutException();
                }
            }
        };

        Observable<List<Integer>> observablefromFuture = Observable.fromFuture(printIntegerFuture);
        observablefromFuture.subscribe(new Observer<List<Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(TAG + this + " onSubscribe");
            }

            @Override
            public void onNext(List<Integer> integers) {
                System.out.println(TAG + this + " onNext is " + integers);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG + this + " onError");
            }

            @Override
            public void onComplete() {
                System.out.println(TAG + this + " onComplete");
            }
        });

        //Observable just
        Observable observableJust=Observable.just(helloWorld());
        observableJust.subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(TAG + this + " onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                System.out.println(TAG + this + " onNext is " + o.toString());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(TAG + this + " onError");
            }

            @Override
            public void onComplete() {
                System.out.println(TAG + this + " onComplete");
            }
        });
    }

    private String helloWorld(){
        return "Hello World";
    }
}
