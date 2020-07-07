package com.test.commentsapp.data.repository;

import androidx.annotation.NonNull;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.data.remote.RetrofitServiceHolder;

import java.util.HashMap;
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
    public Single<Response<List<Comment>>> getComments(@NonNull String lowBound,
                                                       @NonNull String upperBound) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("_sort", "id");
//        queryParams.put("_order", "asc"); // uses by default
        queryParams.put("_start", lowBound);
        queryParams.put("_limit", upperBound);
        return RetrofitServiceHolder.getCommentsApiService().getComments(queryParams);
    }

    @NonNull
    public Single<Response<List<Comment>>> getRangeComments(@NonNull String lowBound,
                                                       @NonNull String upperBound) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("_sort", "id");
//        queryParams.put("_order", "asc"); // uses by default
        queryParams.put("_start", lowBound);
        queryParams.put("_end", upperBound);
        return RetrofitServiceHolder.getCommentsApiService().getComments(queryParams);
    }

}
