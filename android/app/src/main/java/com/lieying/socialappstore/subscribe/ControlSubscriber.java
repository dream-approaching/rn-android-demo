package com.lieying.socialappstore.subscribe;



import android.util.Log;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ControlSubscriber<T> implements Observer<T> {

    public ControlSubscriber(List<Disposable> subscriptionList) {
    }

    @Override
    public void onComplete() {
        Log.e("test" ,"onComplete");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("test" ,"Throwable");
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        Log.e("test" ,"tttttttttttt");
    }
}