package com.sahibinden.challenge.base;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;

public class ApiApplication extends Application {

    /***
     * ApplicationLifeCycleTracker class is used for tracking application background/foreground state
     */
    private ApplicationLifeCycleTracker applicationLifeCycleTracker;
    private  UserPreferences userPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new LifecycleHandler());
        applicationLifeCycleTracker = ApplicationLifeCycleTracker.get(this);
        applicationLifeCycleTracker.addListener(myListener);
        userPreferences = new UserPreferences(this);

    }

    ApplicationLifeCycleTracker.Listener myListener = new ApplicationLifeCycleTracker.Listener(){

        @Override
        public void onBecameForeground() {
            Log.d("APP","onBecameForeground");

        }

        @Override
        public void onBecameBackground() {
            Log.d("APP", "Application in background");

        }
    };

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }
}
