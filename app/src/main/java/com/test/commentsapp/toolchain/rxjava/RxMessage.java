package com.test.commentsapp.toolchain.rxjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.test.commentsapp.toolchain.rxjava.RxJavaConstants.COMPLETE;
import static com.test.commentsapp.toolchain.rxjava.RxJavaConstants.PROCESSING;
import static com.test.commentsapp.toolchain.rxjava.RxJavaConstants.START;

@SuppressWarnings({"unused"})
public class RxMessage<T> {
    @ProcessStatus
    private int mStatus;
    @Nullable
    private T mResult;
    @Nullable
    private Throwable mError;

    private RxMessage(@ProcessStatus int status, @Nullable T result, @Nullable Throwable error) {
        mStatus = status;
        mResult = result;
        mError = error;
    }

    @NonNull
    public static <T> RxMessage<T> onNext(@Nullable T result) {
        return new RxMessage<>(PROCESSING, result, null);
    }

    @NonNull
    public static <T> RxMessage<T> onNext(@ProcessStatus int status, @Nullable T result) {
        return new RxMessage<>(status, result, null);
    }

    @NonNull
    public static <T> RxMessage<T> onNextLast(@Nullable T result) {
        return new RxMessage<>(COMPLETE, result, null);
    }

    @NonNull
    public static <T> RxMessage<T> onError(@Nullable Throwable error) {
        return new RxMessage<>(COMPLETE, null, error);
    }

    @NonNull
    public static <T> RxMessage<T> onStart() {
        return new RxMessage<>(START, null, null);
    }

    @NonNull
    public static <T> RxMessage<T> onComplete() {
        return new RxMessage<>(COMPLETE, null, null);
    }

    @ProcessStatus
    public int getStatus() {
        return mStatus;
    }

    public void setStatus(@ProcessStatus int status) {
        mStatus = status;
    }

    @Nullable
    public T getResult() {
        return mResult;
    }

    public void setResult(@Nullable T result) {
        mResult = result;
    }

    @Nullable
    public Throwable getError() {
        return mError;
    }

    public void setError(@Nullable Throwable error) {
        mError = error;
    }

    public boolean isStart() {
        return mStatus == START;
    }

    public boolean isProcessing() {
        return mStatus == PROCESSING;
    }

    public boolean isComplete() {
        return mStatus == COMPLETE;
    }

    public boolean hasError() {
        return mError != null;
    }

    public boolean isCompleteWithoutError() {
        return isComplete() && !hasError();
    }

}
