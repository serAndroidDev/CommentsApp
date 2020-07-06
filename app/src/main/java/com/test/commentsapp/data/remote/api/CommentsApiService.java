package com.test.commentsapp.data.remote.api;

import com.test.commentsapp.data.model.Comment;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CommentsApiService {
    @GET("/comments")
    Single<Response<List<Comment>>> getComments(@QueryMap HashMap<String, String> params);
}
