package com.test.commentsapp.toolchain.mvvmbase;

public interface IOnBackPressed {
    /**
     * If you return true the back press will not be taken into account, otherwise the activity
     * will act naturally
     *
     * @return true if your processing has priority, if not (default back button behavior) - false
     */
    default boolean onBackPressed() {
        return false;
    }
}