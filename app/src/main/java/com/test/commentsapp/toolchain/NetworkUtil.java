package com.test.commentsapp.toolchain;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.RequiresPermission;

import com.test.commentsapp.presentation.App;

public class NetworkUtil {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @SuppressWarnings({"unused"})
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
