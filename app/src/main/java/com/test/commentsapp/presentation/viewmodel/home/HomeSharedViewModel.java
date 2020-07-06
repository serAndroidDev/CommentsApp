package com.test.commentsapp.presentation.viewmodel.home;

import androidx.annotation.NonNull;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.toolchain.mvvmbase.BaseViewModel;
import com.test.commentsapp.toolchain.mvvmbase.SingleLiveEvent;

import java.util.List;

public class HomeSharedViewModel extends BaseViewModel {

    private final SingleLiveEvent<List<Comment>> mNavigateToCommentsList = new SingleLiveEvent<>();

    public SingleLiveEvent<List<Comment>> getNavigateToCommentsList() {
        return mNavigateToCommentsList;
    }

    public void navigateToCommentsList(@NonNull List<Comment> comments) {
        mNavigateToCommentsList.setValue(comments);
    }
}
