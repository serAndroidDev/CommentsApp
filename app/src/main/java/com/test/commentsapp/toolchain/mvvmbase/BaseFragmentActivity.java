package com.test.commentsapp.toolchain.mvvmbase;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unused")
public abstract class BaseFragmentActivity<VM extends BaseViewModel, DB extends ViewDataBinding>
        extends AppCompatActivity {
    private static final String TAG = BaseFragmentActivity.class.getSimpleName();

    protected VM mSharedViewModel;

    protected DB mBinding;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes());
        mBinding.setLifecycleOwner(this);
        mSharedViewModel = ViewModelProviders.of(this).get(getViewModelClass());
        mSharedViewModel.getOnBackClicked().observe(this, o -> onBackPressed());
        addStartFragment();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(getContainerViewId());
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    @IdRes
    protected abstract int getContainerViewId();

    @Nullable
    protected abstract Fragment getStartFragmentInstance();

    @SuppressWarnings("SameParameterValue")
    protected void replaceOrShowFragment(@NonNull Fragment newFragment,
                                         boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(getContainerViewId());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (currentFragment != null && newFragment.getClass().equals(currentFragment.getClass())) {
            ft.show(currentFragment);
        } else {
            ft.replace(getContainerViewId(), newFragment);

            if (addToBackStack) {
                ft.addToBackStack(newFragment.getClass().getSimpleName());
            }
        }
        ft.commit();
    }

    @SuppressWarnings("SameParameterValue")
    protected void replaceOrShowFragment(@NonNull Fragment newFragment,
                                         boolean addToBackStack,
                                         @AnimatorRes @AnimRes int enter,
                                         @AnimatorRes @AnimRes int exit,
                                         @AnimatorRes @AnimRes int popEnter,
                                         @AnimatorRes @AnimRes int popExit) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(getContainerViewId());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (currentFragment != null && newFragment.getClass().equals(currentFragment.getClass())) {
            ft.show(currentFragment);
        } else {
            ft.setCustomAnimations(enter, exit, popEnter, popExit);
            ft.replace(getContainerViewId(), newFragment);

            if (addToBackStack) {
                ft.addToBackStack(newFragment.getClass().getSimpleName());
            }
        }
        ft.commit();
    }

    @SuppressWarnings("SameParameterValue")
    protected void replaceOrShowFragment(@NonNull Fragment newFragment,
                                         boolean addToBackStack,
                                         int transition) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(getContainerViewId());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (currentFragment != null && newFragment.getClass().equals(currentFragment.getClass())) {
            ft.show(currentFragment);
        } else {
            ft.setTransition(transition);
            ft.replace(getContainerViewId(), newFragment);

            if (addToBackStack) {
                ft.addToBackStack(newFragment.getClass().getSimpleName());
            }
        }
        ft.commit();
    }

    @SuppressWarnings("SameParameterValue")
    protected void replaceFragment(@NonNull Fragment newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(getContainerViewId(), newFragment);
        if (addToBackStack) {
            ft.addToBackStack(newFragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    @SuppressWarnings("SameParameterValue")
    protected void addFragment(@NonNull Fragment newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(getContainerViewId(), newFragment);
        if (addToBackStack) {
            ft.addToBackStack(newFragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    protected void clearFragmentBackStack() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void printBackStack() {
        Log.i(TAG, "Print back stack: {");
        for (int entry = 0; entry < getSupportFragmentManager().getBackStackEntryCount(); entry++) {
            Log.i(TAG, "Found fragment: " + getSupportFragmentManager().getBackStackEntryAt(entry).getName());
        }
        Log.i(TAG, "}");
    }

    private void addStartFragment() {
        Fragment startFragment = getStartFragmentInstance();
        if (startFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int containerViewId = getContainerViewId();
            if (fragmentManager.findFragmentById(containerViewId) == null) {
                addFragment(startFragment, false);
                // replaceOrShowFragment(startFragment, false);
            }
        }
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @NonNull
    private Class<VM> getViewModelClass() {
        return (Class<VM>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
