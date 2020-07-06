package com.test.commentsapp.toolchain.mvvmbase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseViewModel extends ViewModel {
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private SingleLiveEvent mOnBackClicked = new SingleLiveEvent();

    @Override
    protected void onCleared() {
        super.onCleared();
        // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
        mDisposables.dispose();
        // mDisposables.clear(); // Using clear will clear all, but can accept new disposable
    }

    @NonNull
    public CompositeDisposable getDisposables() {
        return mDisposables;
    }

    public SingleLiveEvent getOnBackClicked() {
        return mOnBackClicked;
    }

    public void onBackClicked() {
        mOnBackClicked.call();
    }
}
