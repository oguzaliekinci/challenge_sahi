package com.sahibinden.challenge.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sahibinden.challenge.api.TwitterAuthService;

public abstract class BaseViewModel<N> extends AndroidViewModel {

    private N navigator;

    private TwitterAuthService twitterAuthService;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application, TwitterAuthService twitterAuthService) {
        super(application);
        this.twitterAuthService = twitterAuthService;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }
    public MutableLiveData<String> getError() {
        return error;
    }
    public void setError(String  error) {
        this.error.setValue(error);
    }
    public void setLoading(boolean loading) {
        this.loading.setValue(loading);
    }

    public N getNavigator() {
        return navigator;
    }

    public void setNavigator(N navigator) {
        this.navigator = navigator;
    }

    /*public BaseViewModel(TwitterAuthService twitterAuthService){
        this.twitterAuthService = twitterAuthService;
    }*/
    public TwitterAuthService getTwitterAuthService(){
        return twitterAuthService;
    }
}
