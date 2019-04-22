package com.lieying.socialappstore.subscribe;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public abstract class ControlOnSubscribe<T> extends Observable<T> {
    private List<Disposable> subscriptionList;

    public ControlOnSubscribe(List<Disposable> subscriptionList) {
        if (subscriptionList == null) {
            subscriptionList = new ArrayList<>();
        }
        this.subscriptionList = subscriptionList;
    }

}