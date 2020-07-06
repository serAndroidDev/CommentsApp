package com.test.commentsapp.presentation.viewmodel.comments;

import androidx.lifecycle.MutableLiveData;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.domain.GetCommentsUseCase;
import com.test.commentsapp.toolchain.mvvmbase.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentsViewModel extends BaseViewModel {
    private MutableLiveData<List<Comment>> mComments = new MutableLiveData<>();

    public MutableLiveData<List<Comment>> getDataComments() {
        return mComments;
    }

    public void getComments() {
        getDisposables().add(new GetCommentsUseCase()
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (value.isCompleteWithoutError()) {
                        if (value.getResult() != null)
                            mComments.setValue(value.getResult());
                    }
                }));
    }

}
