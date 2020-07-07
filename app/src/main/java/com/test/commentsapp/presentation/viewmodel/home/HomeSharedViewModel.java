package com.test.commentsapp.presentation.viewmodel.home;

import androidx.annotation.NonNull;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.toolchain.Triple;
import com.test.commentsapp.toolchain.mvvmbase.BaseViewModel;
import com.test.commentsapp.toolchain.mvvmbase.SingleLiveEvent;

import java.util.List;

public class HomeSharedViewModel extends BaseViewModel {

    private final SingleLiveEvent<Triple<List<Comment>, Integer, Integer>> mNavigateToCommentsList = new SingleLiveEvent<>();

    public SingleLiveEvent<Triple<List<Comment>, Integer, Integer>> getNavigateToCommentsList() {
        return mNavigateToCommentsList;
    }

    public void navigateToCommentsList(@NonNull Triple<List<Comment>, Integer, Integer> commentsData) {
        mNavigateToCommentsList.setValue(commentsData);
    }
}
