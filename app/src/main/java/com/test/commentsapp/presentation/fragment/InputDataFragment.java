package com.test.commentsapp.presentation.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.getOnSearchClick().observe(getViewLifecycleOwner(), o -> {
            String lowerBoundText = mBinding.editLowerValue.getText().toString();
            String upperBoundText = mBinding.editUpperValue.getText().toString();
            if (!lowerBoundText.isEmpty() && !upperBoundText.isEmpty()) {
                mLowerBound = Integer.parseInt(lowerBoundText);
                mUpperBound = Integer.parseInt(upperBoundText);
                if (mLowerBound >= 0 && mUpperBound > mLowerBound) {

                    // load first 10 items or less than 10
                    int totalItems = mUpperBound - mLowerBound;
                    int firstItems;
                    if (totalItems < 10) {
                        firstItems = mUpperBound;
                    }
                    else {
                        firstItems = 10;
                    }

                    mViewModel.getComments(mLowerBound, firstItems);
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
