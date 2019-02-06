package com.sahibinden.challenge.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sahibinden.challenge.api.TwitterAuthService;
import com.sahibinden.challenge.ui.ItemListViewModel;
import com.sahibinden.challenge.ui.LoginViewModel;


public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {

    private V viewModel;
    private ApiApplication applicationContext;
    private TwitterAuthService twitterAuthService;

    public ViewModelProviderFactory(V viewModel, ApiApplication apiApplication, TwitterAuthService twitterAuthService) {
        this.viewModel = viewModel;
        applicationContext = apiApplication;
        this.twitterAuthService = twitterAuthService;
    }

    //used for passing parameter to viewModel constructor
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ItemListViewModel.class)) {
            return (T) new ItemListViewModel(applicationContext,twitterAuthService);
        }else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(applicationContext,twitterAuthService);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
