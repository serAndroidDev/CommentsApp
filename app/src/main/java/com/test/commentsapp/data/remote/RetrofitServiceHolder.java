package com.test.commentsapp.data.remote;

import androidx.annotation.NonNull;

import com.test.commentsapp.toolchain.RetrofitUtil;
import com.test.commentsapp.data.DataConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitServiceHolder {
        private static final String TAG = RetrofitServiceHolder.class.getSimpleName();

        @NonNull
        private static final Retrofit PIC_LIFE_RETROFIT = RetrofitUtil.createRetrofit(
                DataConstants.COMMENTS_BASE_URL, getPicLifeOkHttpClient());

        @NonNull
        private static final CommentsApiService PIC_LIFE_API_SERVICE = PIC_LIFE_RETROFIT
                .create(CommentsApiService.class);

        @NonNull
        public static Retrofit getPicLifeRetrofit() {
            return PIC_LIFE_RETROFIT;
        }

        @NonNull
        public static CommentsApiService getPicLifeApiService() {
            return PIC_LIFE_API_SERVICE;
        }

        @NonNull
        private static OkHttpClient getPicLifeOkHttpClient() {
            return RetrofitUtil.getSharedOkHttpClientBuilder()
                    .connectTimeout(DataConstants.OK_HTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DataConstants.OK_HTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DataConstants.OK_HTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
}
