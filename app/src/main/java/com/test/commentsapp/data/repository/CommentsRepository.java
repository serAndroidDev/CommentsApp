package com.test.commentsapp.data.repository;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.data.remote.RetrofitServiceHolder;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public class CommentsRepository {

    private static final CommentsRepository INSTANCE = new CommentsRepository();

    private CommentsRepository() {
    }

    public static CommentsRepository getInstance() {
        return INSTANCE;
    }

    @NonNull
    public Single<Response<List<Comment>>> getComments() {
        JsonObject data = new JsonObject();

        return RetrofitServiceHolder.getCommentsApiService().getComments();
    }

}
