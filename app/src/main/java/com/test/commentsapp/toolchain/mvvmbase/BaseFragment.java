package com.test.commentsapp.toolchain.mvvmbase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings({"unused"})
public abstract class BaseFragment<VM extends BaseViewModel, SVM extends BaseViewModel,
        DB extends ViewDataBinding> extends Fragment implements IOnBackPressed {
    protected VM mViewModel;

    protected SVM mSharedViewModel;

    protected DB mBinding;

    @SuppressWarnings({"ConstantConditions"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        mBinding.setLifecycleOwner(this);
        mViewModel = ViewModelProviders.of(this).get(getViewModelClass());
        setViewModelVariableInBinding();
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(getSharedViewModelClass());
        return mBinding.getRoot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getOnBackClicked().observe(getViewLifecycleOwner(), o ->
                mSharedViewModel.onBackClicked());
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void setViewModelVariableInBinding();

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @NonNull
    private Class<VM> getViewModelClass() {
        return (Class<VM>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @NonNull
    private Class<SVM> getSharedViewModelClass() {
        return (Class<SVM>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
