package com.lieying.petcat.subscribe;



import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ControlSubscriber<T> implements Observer<T> {

    public ControlSubscriber(List<Disposable> subscriptionList) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
    }
}