package com.test.commentsapp.domain;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.data.repository.CommentsRepository;
import com.test.commentsapp.toolchain.rxjava.RxMessage;
import com.test.commentsapp.toolchain.rxjava.RxResponseException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class GetRangeComments {
    @NonNull
    public Observable<RxMessage<List<Comment>>> execute(@IntRange(from = 0, to = 499) int lowestBound,
                                                        @IntRange(from = 1, to = 499) int upperBound) {

        Single<RxMessage<List<Comment>>> startSingle = Single.just(RxMessage.onStart());

        Single<RxMessage<List<Comment>>> requestSingle = CommentsRepository.getInstance()
                .getRangeComments(String.valueOf(lowestBound), String.valueOf(upperBound))
                .map(response -> {
                    if (response.isSuccessful()) {
                        List<Comment> commentsList = response.body();
                        if (commentsList != null)
                            return RxMessage.onNextLast(commentsList);
                        else
                            throw new RxResponseException(response.code(), response.body().toString());
                    } else {
                        throw new RxResponseException(response);
                    }
                })
                .onErrorReturn(RxMessage::onError);

        return Observable.concat(startSingle.toObservable(), requestSingle.toObservable());
    }
}
