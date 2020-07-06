package com.test.commentsapp.presentation.viewmodel.home;

import androidx.annotation.IntRange;
import androidx.lifecycle.MutableLiveData;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.domain.GetCommentsUseCase;
import com.test.commentsapp.toolchain.mvvmbase.BaseViewModel;
import com.test.commentsapp.toolchain.mvvmbase.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InputDataViewModel extends BaseViewModel {

    private final SingleLiveEvent mSearchClicked = new SingleLiveEvent();
    private  MutableLiveData<List<Comment>> mComments = new MutableLiveData<>();

    public MutableLiveData<List<Comment>> getDataComments() {
        return mComments;
    }

    public SingleLiveEvent getOnSearchClick() {
        return mSearchClicked;
    }

    public void onSearchClicked() {
        mSearchClicked.call();
    }

    public void getComments(@IntRange(from = 0, to = 499) int lowestBound,
                            @IntRange(from = 1, to = 499) int upperBound) {
        getDisposables().add(new GetCommentsUseCase()
                .execute(lowestBound, upperBound)
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
