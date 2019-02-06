package com.sahibinden.challenge.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sahibinden.challenge.R;
import com.sahibinden.challenge.api.TwitterAuthService;
import com.sahibinden.challenge.api.entities.OAuthRequestTokenResponse;
import com.sahibinden.challenge.base.ApiApplication;
import com.sahibinden.challenge.base.BaseActivity;
import com.sahibinden.challenge.base.ViewModelProviderFactory;

public class LoginActivity extends BaseActivity<LoginViewModel> implements View.OnClickListener, LoginNavigator {
    public FrameLayout fragmentContainer;
    private Button loginButton;
    private LoginViewModel loginViewModel;
    @Override
    public LoginViewModel getViewModel() {
        ViewModelProviderFactory factory = new  ViewModelProviderFactory(loginViewModel,(ApiApplication)getApplicationContext(), TwitterAuthService.getInstance());
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        return loginViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel.setNavigator(this);

        fragmentContainer = findViewById(R.id.fragment_container);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == loginButton){
            loginViewModel.retrieveRequestToken();
        }
    }

    @Override
    public void showAuthorizationWebView(OAuthRequestTokenResponse oAuthRequestTokenResponse) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, AuthorizationFragment.newInstance(oAuthRequestTokenResponse))
                .commit();
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void getItemListActivity() {

        startActivity(new Intent(this,ItemListActivity.class));
        finish();
    }
}
