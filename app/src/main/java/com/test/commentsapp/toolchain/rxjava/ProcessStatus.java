package com.test.commentsapp.toolchain.rxjava;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.test.commentsapp.toolchain.rxjava.RxJavaConstants.COMPLETE;
import static com.test.commentsapp.toolchain.rxjava.RxJavaConstants.PROCESSING;
import static com.test.commentsapp.toolchain.rxjava.RxJavaConstants.START;

/**
 * Denotes that the annotated element must be one of the
 * defined process statuses of {@link RxMessage} and that
 * its value should be one of the explicitly named constants.
 *
 * @see IntDef
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({START, PROCESSING, COMPLETE})
public @interface ProcessStatus {
}
