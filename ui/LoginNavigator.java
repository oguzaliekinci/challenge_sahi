package com.sahibinden.challenge.ui;

import com.sahibinden.challenge.api.entities.OAuthRequestTokenResponse;
import com.sahibinden.challenge.base.BaseNavigator;

public interface LoginNavigator extends BaseNavigator {

    void showAuthorizationWebView(OAuthRequestTokenResponse oAuthRequestTokenResponse);
    void getItemListActivity();
}
