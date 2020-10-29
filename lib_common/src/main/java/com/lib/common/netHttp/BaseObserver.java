package com.lib.common.netHttp;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<T> {

    protected Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onError(Throwable e) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            Log.d("HTTPERR","code = "+code);
        }

    }



    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}