package com.test.commentsapp.data.remote;

import androidx.annotation.NonNull;

import com.test.commentsapp.data.DataConstants;
import com.test.commentsapp.data.remote.api.CommentsApiService;
import com.test.commentsapp.toolchain.RetrofitUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitServiceHolder {
        private static final String TAG = RetrofitServiceHolder.class.getSimpleName();

        @NonNull
        private static final Retrofit COMMENTS_RETROFIT = RetrofitUtil.createRetrofit(
                DataConstants.COMMENTS_BASE_URL, getOkHttpClient());

        @NonNull
        private static final CommentsApiService COMMENTS_API_SERVICE = COMMENTS_RETROFIT
                .create(CommentsApiService.class);

        @NonNull
        public static Retrofit getCommentRetrofit() {
            return COMMENTS_RETROFIT;
        }

        @NonNull
        public static CommentsApiService getCommentsApiService() {
            return COMMENTS_API_SERVICE;
        }

        @NonNull
        private static OkHttpClient getOkHttpClient() {
            return RetrofitUtil.getSharedOkHttpClientBuilder()
                    .connectTimeout(DataConstants.OK_HTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DataConstants.OK_HTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DataConstants.OK_HTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
}
