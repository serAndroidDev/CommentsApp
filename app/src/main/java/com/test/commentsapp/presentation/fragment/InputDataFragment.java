package com.test.commentsapp.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.test.commentsapp.R;
import com.test.commentsapp.databinding.FragmentInputDataBinding;
import com.test.commentsapp.presentation.viewmodel.home.HomeSharedViewModel;
import com.test.commentsapp.presentation.viewmodel.home.InputDataViewModel;
import com.test.commentsapp.toolchain.mvvmbase.BaseFragment;

public class InputDataFragment extends BaseFragment<InputDataViewModel, HomeSharedViewModel,
        FragmentInputDataBinding> {

    public static InputDataFragment newInstance() {
        return new InputDataFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_input_data;
    }

    @Override
    protected void setViewModelVariableInBinding() {
        mBinding.setViewModel(mViewModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.getOnSearchClick().observe(getViewLifecycleOwner(), o -> {
            mViewModel.getComments();
        });

        mViewModel.getDataComments().observe(getViewLifecycleOwner(), comments -> {
            mSharedViewModel.navigateToCommentsList(comments);
        });

    }
}
