package com.test.commentsapp.toolchain.rxjava;

public class RxJavaConstants {
    /**
     * {@link io.reactivex.Observable} just started
     * via {@link RxMessage#onStart()} method.
     */
    public static final int START = 0;

    /**
     * {@link io.reactivex.Observable} has in progress status,
     * set via {@link RxMessage#onNext(Object)}.
     */
    public static final int PROCESSING = 1;

    /**
     * {@link io.reactivex.Observable} finished the process,
     * set via {@link RxMessage#onComplete()}
     *      or {@link RxMessage#onError(Throwable)}
     *      or {@link RxMessage#onNextLast(Object)}.
     */
    public static final int COMPLETE = 2;
}
