package com.test.commentsapp.data.remote.api;

import com.test.commentsapp.data.model.Comment;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface CommentsApiService {
    @GET("/comments")
    Single<Response<List<Comment>>> getComments();
}
