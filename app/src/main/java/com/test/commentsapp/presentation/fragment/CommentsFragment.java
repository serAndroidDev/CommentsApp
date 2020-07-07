package com.test.commentsapp.presentation.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import static com.test.commentsapp.presentation.UiConstants.ARGUMENT_LOWER_BOUND;
import static com.test.commentsapp.presentation.UiConstants.ARGUMENT_UPPER_BOUND;

public class CommentsFragment extends BaseFragment<CommentsViewModel, HomeSharedViewModel,
        FragmentCommentsBinding> {
    private int lastVisibleItem;
    private int totalVisibleItemCount;
    private int mUpperBound;
    private int mLowerBound;
    private int mTotalCount;
    private final int VISIBLE_THRESHOLD = 1;

    public static CommentsFragment newInstance(@NonNull List<Comment> comments, int lowerBound, int upperBound) {
        Bundle args = new Bundle();
        ArrayList<Comment> commentArrayList;
        if (comments instanceof ArrayList) {
            commentArrayList = (ArrayList<Comment>) comments;
        } else {
            commentArrayList = new ArrayList<>(comments);
        }
        args.putParcelableArrayList(ARGUMENT_COMMENTS, commentArrayList);
        args.putInt(ARGUMENT_LOWER_BOUND, lowerBound);
        args.putInt(ARGUMENT_UPPER_BOUND, upperBound);
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args!= null) {
            ArrayList<Comment> commentsList = args.getParcelableArrayList(ARGUMENT_COMMENTS);
            mViewModel.getDataComments().setValue(commentsList);
            mLowerBound = args.getInt(ARGUMENT_LOWER_BOUND);
            mUpperBound = args.getInt(ARGUMENT_UPPER_BOUND);
            mTotalCount = mUpperBound - mLowerBound;

            mViewModel.getLoadMore().setValue(false);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        mBinding.recyclerComments.setLayoutManager(layoutManager);

        mViewModel.getDataComments().observe(getViewLifecycleOwner(), comments -> {
            CommentsAdapter adapter = (CommentsAdapter) mBinding.recyclerComments.getAdapter();
            if (adapter == null) {
                adapter = new CommentsAdapter(getContext());
                adapter.setItems(comments);
                mBinding.recyclerComments.setAdapter(adapter);

                mBinding.recyclerComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        totalVisibleItemCount = layoutManager.getItemCount();
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                        if (!mViewModel.getLoadMore().getValue() &&
                                totalVisibleItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD) &&
                                totalVisibleItemCount < mTotalCount) {
                            mViewModel.getLoadMore().setValue(true);
                            mViewModel.loadComments(totalVisibleItemCount + mLowerBound, 10);
                        }
                    }
                });
            } else {
                List<Comment> addedComments = new ArrayList<>(comments);
                adapter.addAll(addedComments);
            }
        });

        mViewModel.getLoadMore().observe(getViewLifecycleOwner(), this::showAnimation);

    }

    private void showAnimation(boolean needToShow) {
        if (needToShow) {
            mBinding.progressBar.setVisibility(View.VISIBLE);
        }
        else
            mBinding.progressBar.setVisibility(View.GONE);
    }

}
