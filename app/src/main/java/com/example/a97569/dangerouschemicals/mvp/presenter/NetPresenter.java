package com.example.a97569.dangerouschemicals.mvp.presenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *******************************************************************************
 * @FileName:  NetPresenter
 * @Package com.yidaoyun.newenergyvehicleandroid.base
 * @Description:  模块通用业务操作逻辑接口类
 * @author: lpz
 * @date:   2018/10/11  14:22 
 * @version V1.0 
 *******************************************************************************
 */ 
public abstract class NetPresenter{

    private CompositeDisposable compositeDisposable;

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void removeDisposable(Disposable disposable){
        if (compositeDisposable!=null){
            compositeDisposable.remove(disposable);
        }
    }

    protected void unSubscribe() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    //子线程运行，主线程回调
    protected  <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}