package com.sahibinden.challenge.base;

import android.content.Intent;

public interface BaseNavigator extends BaseFragmentManager {

    /**
     * This is a general method used for showing some kind of progress during a background task. For example, this
     * method should show a progress bar and/or disable buttons before some background work starts.
     */
    void showProgress();

    /**
     * This is a general method used for hiding progress information after a background task finishes.
     */
    void hideProgress();

    /**
     * This method is used for showing error messages on the UI.
     *
     * @param error The error object.
     */

    void showError(Throwable error);

    BaseActivity<?> getBaseActivity();


    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int requestCode);

}
