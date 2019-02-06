package com.sahibinden.challenge.ui;

import android.app.Application;
import android.support.annotation.NonNull;

import com.sahibinden.challenge.Configuration;
import com.sahibinden.challenge.api.TwitterAuthService;
import com.sahibinden.challenge.api.entities.OAuthAccessTokenResponse;
import com.sahibinden.challenge.base.ApiApplication;
import com.sahibinden.challenge.base.BaseViewModel;
import com.sahibinden.challenge.ui.LoginNavigator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(@NonNull Application application, TwitterAuthService twitterAuthService) {
        super(application, twitterAuthService);
    }

    private void saveRequestToken(OAuthAccessTokenResponse oAuthAccessTokenResponse){
        ((ApiApplication)getApplication()).getUserPreferences().saveSharedPrefString(getApplication().getApplicationContext(), Configuration.OAUTH_ACCESS_TOKEN_KEY, oAuthAccessTokenResponse.getOauth_token());
        ((ApiApplication)getApplication()).getUserPreferences().saveSharedPrefString(getApplication().getApplicationContext(), Configuration.OAUTH_ACCESS_TOKEN_SECRET_KEY, oAuthAccessTokenResponse.getOauth_token_secret());
    }

    public void retrieveAccessTokenRequest(String oAuthVerifier){
        setLoading(true);
        getTwitterAuthService().sendAccessTokenRequest(oAuthVerifier).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oAuthAccessTokenResponse -> {
                    setLoading(false);
                    saveRequestToken(oAuthAccessTokenResponse);
                    getNavigator().getItemListActivity();

                }, throwable -> {
                    setLoading(false);
                    setError(throwable.getLocalizedMessage());
                });

    }

    public void retrieveRequestToken(){

        setLoading(true);
        getTwitterAuthService().sendRequestTokenRequest().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oAuthRequestTokenResponse -> {
                    setLoading(false);

                    getNavigator().showAuthorizationWebView(oAuthRequestTokenResponse);

                }, throwable -> {
                    setLoading(false);
                    setError(throwable.getLocalizedMessage());

                });

    }
}
