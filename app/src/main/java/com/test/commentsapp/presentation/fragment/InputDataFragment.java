package com.test.commentsapp.presentation.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.test.commentsapp.R;
import com.test.commentsapp.databinding.FragmentInputDataBinding;
import com.test.commentsapp.presentation.viewmodel.home.HomeSharedViewModel;
import com.test.commentsapp.presentation.viewmodel.home.InputDataViewModel;
import com.test.commentsapp.toolchain.Triple;
import com.test.commentsapp.toolchain.mvvmbase.BaseFragment;

public class InputDataFragment extends BaseFragment<InputDataViewModel, HomeSharedViewModel,
        FragmentInputDataBinding> {

    private int mLowerBound, mUpperBound;

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

    @SuppressWarnings({"unchecked"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.loading(false);

        mViewModel.getOnSearchClick().observe(getViewLifecycleOwner(), o -> {
            String lowerBoundText = mBinding.editLowerValue.getText().toString();
            String upperBoundText = mBinding.editUpperValue.getText().toString();
            if (!lowerBoundText.isEmpty() && !upperBoundText.isEmpty()) {
                mLowerBound = Integer.parseInt(lowerBoundText);
                mUpperBound = Integer.parseInt(upperBoundText);
                if (mLowerBound >= 0 && mUpperBound > mLowerBound) {
                    mViewModel.loading(true);

                    // load first 10 items
                    int totalItems = mUpperBound - mLowerBound;
                    if (totalItems <= 10)
                        mViewModel.getRangeComments(mLowerBound, mUpperBound);
                    else
                        mViewModel.getRangeComments(mLowerBound, mLowerBound + 10);

                } else {
                    Toast.makeText(getContext(), "The first number must be bigger than the second",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter all data fields", Toast.LENGTH_SHORT).show();
            }

            hideKeyboard();
        });

        mViewModel.getDataComments().observe(getViewLifecycleOwner(), comments ->
                mSharedViewModel.navigateToCommentsList(new Triple<>(comments, mLowerBound, mUpperBound)));

        mViewModel.getLoading().observe(getViewLifecycleOwner(), this::showAnimation);

    }

    private void showAnimation(boolean needToShow) {
        if (needToShow) {
            mBinding.progressBar.setVisibility(View.VISIBLE);
        }
        else
            mBinding.progressBar.setVisibility(View.GONE);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onBackPressed() {
        boolean isLoading = mViewModel.getLoading().getValue();
        if (isLoading) {
            mViewModel.getDisposables().clear();
            mViewModel.loading(false);
            return true;
        } else
            return false;
    }

    private void hideKeyboard() {
        Context context = getContext();
        if (context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mBinding.getRoot().getWindowToken(), 0);
            }
        }
    }

}
