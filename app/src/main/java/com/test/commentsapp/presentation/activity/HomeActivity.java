package com.test.commentsapp.presentation.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.commentsapp.R;
import com.test.commentsapp.databinding.ActivityHomeBinding;
import com.test.commentsapp.presentation.fragment.CommentsFragment;
import com.test.commentsapp.presentation.fragment.InputDataFragment;
import com.test.commentsapp.presentation.viewmodel.home.HomeSharedViewModel;
import com.test.commentsapp.toolchain.mvvmbase.BaseFragmentActivity;

public class HomeActivity extends BaseFragmentActivity<HomeSharedViewModel, ActivityHomeBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedViewModel.getNavigateToCommentsList().observe(this, dataComments ->
                addFragment(CommentsFragment.newInstance(dataComments.first, dataComments.second, dataComments.third), true));

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.fragment_container;
    }

    @Nullable
    @Override
    protected Fragment getStartFragmentInstance() {
        return InputDataFragment.newInstance();
    }
}
