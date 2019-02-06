package com.sahibinden.challenge.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sahibinden.challenge.Configuration;
import com.sahibinden.challenge.R;
import com.sahibinden.challenge.api.entities.OAuthRequestTokenResponse;

public class AuthorizationFragment extends Fragment {

    private class AuthorizationWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(request.getUrl().toString().contains(Configuration.REQUEST_TOKEN_CALLBACK_URL)){
                String[] tokenAndVerifier = request.getUrl().toString().split("&");
                String oAuthVerifier = tokenAndVerifier[1].substring(tokenAndVerifier[1].indexOf('=') + 1);
                ((LoginActivity)getActivity()).getViewModel().retrieveAccessTokenRequest(oAuthVerifier);
                removeFragment();
                return true;
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            ((LoginActivity)getActivity()).getViewModel().setLoading(false);
            super.onPageFinished(view, url);
        }
    }
    private Toolbar toolbar;
    private WebView webView;
    private OAuthRequestTokenResponse oAuthRequestTokenResponse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authorization, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationOnClickListener(navIcon -> removeFragment());

        oAuthRequestTokenResponse = getArguments().getParcelable("oAuthRequestTokenResponse");
        webView = rootView.findViewById(R.id.webView);
        String authorizationUrl   = "https://api.twitter.com/oauth/authorize" + "?oauth_token=" + oAuthRequestTokenResponse.getOauth_token();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AuthorizationWebViewClient());
        ((LoginActivity)getActivity()).getViewModel().setLoading(true);

        webView.loadUrl(authorizationUrl);
        return rootView;
    }

    public void removeFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
        ((LoginActivity)getActivity()).fragmentContainer.setVisibility(View.GONE);
        toolbar.setNavigationIcon(null);

    }
    public static AuthorizationFragment newInstance(OAuthRequestTokenResponse oAuthRequestTokenResponse) {
        Bundle arg = new Bundle();
        arg.putParcelable("oAuthRequestTokenResponse", oAuthRequestTokenResponse);
        AuthorizationFragment fragment = new AuthorizationFragment();
        fragment.setArguments(arg);
        return fragment;
    }


}
