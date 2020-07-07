package com.test.commentsapp.presentation.viewmodel.comments;

import android.os.Handler;

import androidx.annotation.IntRange;
import androidx.lifecycle.MutableLiveData;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.domain.GetCommentsUseCase;
import com.test.commentsapp.toolchain.mvvmbase.BaseViewModel;
import com.test.commentsapp.toolchain.mvvmbase.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentsViewModel extends BaseViewModel {
    private MutableLiveData<List<Comment>> mComments = new MutableLiveData<>();
    private final SingleLiveEvent<Boolean> mLoadMore = new SingleLiveEvent<>();

    public MutableLiveData<List<Comment>> getDataComments() {
        return mComments;
    }

    public SingleLiveEvent<Boolean> getLoadMore() {
        return mLoadMore;
    }

    public void loadComments(@IntRange(from = 0, to = 499) int lowestBound,
                             @IntRange(from = 1, to = 499) int upperBound) {
        getDisposables().add(new GetCommentsUseCase()
                .execute(lowestBound, upperBound)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    // added delay for show animation
                    if (value.isCompleteWithoutError()) {
                        if (value.getResult() != null) {
                            new Handler().postDelayed(() -> {
                                mComments.setValue(value.getResult());
                                mLoadMore.setValue(false);
                            }, 1500);
                        }
                    }
                }));
    }

}
