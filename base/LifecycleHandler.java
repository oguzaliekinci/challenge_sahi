package com.sahibinden.challenge.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by ozgesaracoglu on 01/02/16.
 */
public class LifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private static int resumed = 0;
    private static int paused = 0;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    public static boolean isApplicationWakenUp(){
        return resumed == paused + 1;
    }
    public static int getResumed() {
        return resumed;
    }

    public static int getPaused() {
        return paused;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }
}
