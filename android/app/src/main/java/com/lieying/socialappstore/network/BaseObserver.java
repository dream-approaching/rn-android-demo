package com.lieying.socialappstore.network;

import android.accounts.NetworkErrorException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by liyi on 2019/3/27.
 *  观察者Observer 的二次封装
 *
 */

public abstract class BaseObserver<T>  implements Observer<T> {

    public BaseObserver() {

    }
    private Disposable disposable;

    public Disposable getDisposable(){
        return disposable;
    }
    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
        disposable = d;
    }



    @Override
    public void onNext(T tBaseEntity) {

        onRequestEnd();

        if (tBaseEntity!=null) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                onError(e);
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException
                    || e instanceof NullPointerException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(T t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(T t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
        showProgressDialog();

    }

    protected void onRequestEnd() {
        closeProgressDialog();
    }

    public void showProgressDialog() {
    }

    public void closeProgressDialog() {
    }

}
