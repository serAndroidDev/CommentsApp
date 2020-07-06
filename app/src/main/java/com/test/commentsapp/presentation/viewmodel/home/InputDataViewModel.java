package com.test.commentsapp.presentation.viewmodel.home;

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
