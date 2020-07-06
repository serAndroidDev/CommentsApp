package com.test.commentsapp.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.test.commentsapp.R;
import com.test.commentsapp.databinding.FragmentInputDataBinding;
import com.test.commentsapp.presentation.viewmodel.home.HomeSharedViewModel;
import com.test.commentsapp.presentation.viewmodel.home.InputDataViewModel;
import com.test.commentsapp.toolchain.mvvmbase.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputDataFragment extends BaseFragment<InputDataViewModel, HomeSharedViewModel, FragmentInputDataBinding> {

    public static InputDataFragment newInstance() {
        InputDataFragment fragment = new InputDataFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data, container, false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_input_data;
    }

    @Override
    protected void setViewModelVariableInBinding() {
        mBinding.setViewModel(mViewModel);
    }
}
