package com.test.commentsapp.toolchain;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Each OkHttp instance keep their own request pooling, disk cache, routing logic, etc..
 * Because they don't share these, overall app will unnecessarily lose performance.
 * 1) Sharing OkHttp instance win back the lost app performance.
 * 2) In order to create custom OkHttp clients (with different interceptors),
 * but with a shared core, you can make shallow copies of an OkHttp client by calling
 * .newBuilder() or using the same OkHttpClient.Builder.
 * <p>
 * The negative effect of not sharing resources is also happening (with a less dramatic
 * effect) for converters.
 */
@SuppressWarnings("unused")
public class RetrofitUtil {
    private static final Level HTTP_LOG_LEVEL = Level.BODY;

    private static final OkHttpClient.Builder OK_HTTP_CLIENT_BUILDER = new OkHttpClient.Builder()
            .dispatcher(getDispatcher())
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HTTP_LOG_LEVEL));

    private static final Retrofit.Builder RETROFIT_BUILDER = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    @NonNull
    public static Retrofit createRetrofit(@NonNull String baseUrl,
                                          @NonNull OkHttpClient okHttpClient) {
        return RETROFIT_BUILDER
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @NonNull
    private static Dispatcher getDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(100);
        dispatcher.setMaxRequestsPerHost(10);
        return dispatcher;
    }

    @NonNull
    public static OkHttpClient.Builder getSharedOkHttpClientBuilder() {
        return OK_HTTP_CLIENT_BUILDER.addInterceptor(new CommentsInterceptor());
    }

    /**
     * Seems like new Retrofit versions already check the network status before requests and
     * throw {@link java.net.UnknownHostException} if there is no internet.
     */
    public static Interceptor getNetworkConnectionInterceptor() {
        return chain -> {
            if (NetworkUtil.isNetworkAvailable()) {
                return chain.proceed(chain.request());
            } else {
                throw new ConnectException();
            }
        };
    }

    public static class CommentsInterceptor implements Interceptor {
        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request newRequest = originalRequest.newBuilder()
                    .header("Content-Type","application/json;")
                    .build();
            return chain.proceed(newRequest);
        }
    }
}

