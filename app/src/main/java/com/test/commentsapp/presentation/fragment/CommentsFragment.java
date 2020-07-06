package com.test.commentsapp.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.test.commentsapp.R;
import com.test.commentsapp.data.model.Comment;
import com.test.commentsapp.databinding.FragmentCommentsBinding;
import com.test.commentsapp.presentation.adapter.CommentsAdapter;
import com.test.commentsapp.presentation.viewmodel.comments.CommentsViewModel;
import com.test.commentsapp.presentation.viewmodel.home.HomeSharedViewModel;
import com.test.commentsapp.toolchain.mvvmbase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.test.commentsapp.presentation.UiConstants.ARGUMENT_COMMENTS;

public class CommentsFragment extends BaseFragment<CommentsViewModel, HomeSharedViewModel,
        FragmentCommentsBinding> {

    private ArrayList<Comment> mComments;

    public static CommentsFragment newInstance(@NonNull List<Comment> comments) {
        Bundle args = new Bundle();
        ArrayList<Comment> commentArrayList;
        if (comments instanceof ArrayList) {
            commentArrayList = (ArrayList<Comment>) comments;
        } else {
            commentArrayList = new ArrayList<>(comments);
        }
        args.putParcelableArrayList(ARGUMENT_COMMENTS, commentArrayList);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comments;
    }

    @Override
    protected void setViewModelVariableInBinding() {
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args!= null) {
            mComments = args.getParcelableArrayList(ARGUMENT_COMMENTS);
        }

        mBinding.recyclerComments.setLayoutManager(new LinearLayoutManager(getContext()));

        CommentsAdapter adapter = (CommentsAdapter) mBinding.recyclerComments.getAdapter();
        adapter = new CommentsAdapter(getContext());
        adapter.setItems(mComments);
        mBinding.recyclerComments.setAdapter(adapter);





    }
}
