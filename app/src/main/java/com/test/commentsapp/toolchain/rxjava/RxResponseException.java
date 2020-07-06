package com.test.commentsapp.toolchain.rxjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Response;

@SuppressWarnings("unused")
public class RxResponseException extends Exception {
    private int mCode;

    public RxResponseException(int code, @Nullable String message) {
        super(message);
        mCode = code;
    }

    public RxResponseException(@NonNull Response response) {
        this(response.code(), getErrorBodyString(response));
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    @Nullable
    private static String getErrorBodyString(@NonNull Response response) {
        String message = null;
        try {
            if (response.errorBody() != null) {
                message = response.errorBody().string();
            }
        } catch (Throwable ignored) {
        }
        return message;
    }
}
