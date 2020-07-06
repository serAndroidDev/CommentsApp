package com.test.commentsapp.presentation;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * Every Android app is guaranteed to have exactly one {@link android.app.Application} instance
     * for the lifetime of the app.
     * But there is no guarantee that the non-static onCreate() will have been called before
     * some static initialization code tries to fetch your Context object.
     * (in some cases null check is required)
     */
    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
