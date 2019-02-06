package com.sahibinden.challenge.api;

import com.google.gson.Gson;
import com.sahibinden.challenge.Configuration;
import com.sahibinden.challenge.api.entities.OAuthAccessTokenResponse;
import com.sahibinden.challenge.api.entities.OAuthRequestTokenResponse;
import com.sahibinden.challenge.api.entities.Tweet;
import com.sahibinden.challenge.util.Utilities;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterAuthService {
    private static TwitterAuthService service;

    private OAuthRequestTokenResponse oAuthTokenResponse;
    private OAuthAccessTokenResponse oAuthAccessTokenResponse;
    public synchronized static TwitterAuthService getInstance() {
        if (service == null) {
            service = new TwitterAuthService();
        }
        return service;
    }

    // basic builders created
    private static TwitterInterceptor.Builder sInterceptorBuilder =
            new TwitterInterceptor.Builder()
                    .consumerKey(Configuration.CONSUMER_KEY)
                    .consumerSecret(Configuration.CONSUMER_SECRET)
                    .random(new Random())
                    .clock(new TwitterInterceptor.Clock());

    private static Gson mGson = Utilities.getGsonForPrivateVariableClass();
    private static Retrofit.Builder sRetrofitBuilder = new Retrofit.Builder()
            .baseUrl(Configuration.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    // without access_token interceptor and client created
    private static TwitterInterceptor sWithoutAccessTokenInterceptor =
            sInterceptorBuilder.accessToken("").accessSecret("").build();

    // logger for debugging
    private static HttpLoggingInterceptor sLoggingInterceptor = new HttpLoggingInterceptor();

    private static OkHttpClient.Builder sWithoutAccessTokenClient = new OkHttpClient.Builder()
            .addInterceptor(sWithoutAccessTokenInterceptor);

    private static Retrofit sWithoutAccessTokenRetrofit;


    public static TwitterAuthAPI createTwitterAPIWithoutAccessToken() {
        if (sWithoutAccessTokenRetrofit == null) {
            sLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // uncomment to debug network requests
             sWithoutAccessTokenClient.addInterceptor(sLoggingInterceptor);
            sWithoutAccessTokenRetrofit = sRetrofitBuilder
                    .client(sWithoutAccessTokenClient.build()).build();
        }
        return sWithoutAccessTokenRetrofit.create(TwitterAuthAPI.class);
    }

    public static TwitterAuthAPI createTwitterAPIWithAccessToken(String token) {
        TwitterInterceptor withAccessTokenInterceptor =
                sInterceptorBuilder.accessToken(token).accessSecret("").build();
        OkHttpClient withAccessTokenClient = new OkHttpClient.Builder()
                .addInterceptor(withAccessTokenInterceptor)
                .addInterceptor(sLoggingInterceptor) // uncomment to debug network requests
                .build();
        Retrofit withAccessTokenRetrofit = sRetrofitBuilder.client(withAccessTokenClient).build();
        return withAccessTokenRetrofit.create(TwitterAuthAPI.class);
    }

    public static TwitterAuthAPI createTwitterAPIWithAccessTokenAndSecret(
            String token, String tokenSecret) {
        sLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        TwitterInterceptor withAccessTokenAndSecretInterceptor =
                sInterceptorBuilder.accessToken(token).accessSecret(tokenSecret).build();
        OkHttpClient withAccessTokenAndSecretClient = new OkHttpClient.Builder()
                .addInterceptor(withAccessTokenAndSecretInterceptor)
                .addInterceptor(sLoggingInterceptor) // uncomment to debug network requests
                .build();
        Retrofit withAccessTokenAndSecretRetrofit =
                sRetrofitBuilder.client(withAccessTokenAndSecretClient).build();
        return withAccessTokenAndSecretRetrofit.create(TwitterAuthAPI.class);
    }

    public Flowable<OAuthRequestTokenResponse> sendRequestTokenRequest(){
        return createTwitterAPIWithoutAccessToken().requestToken(Configuration.REQUEST_TOKEN_CALLBACK_URL).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<ResponseBody, Flowable<OAuthRequestTokenResponse>>) responseBody -> {
                    String text = responseBody.string();
                    String oauthTokenKeyValue = text.split("&")[0];
                    oAuthTokenResponse = new OAuthRequestTokenResponse(oauthTokenKeyValue.substring(oauthTokenKeyValue.indexOf("=") + 1));
                    return Flowable.just(oAuthTokenResponse);
                });

    }

    public Flowable<OAuthAccessTokenResponse> sendAccessTokenRequest(String oAuthVerifier ){
        return createTwitterAPIWithAccessToken(oAuthTokenResponse.getOauth_token()).getAccessToken(oAuthVerifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<ResponseBody, Flowable<OAuthAccessTokenResponse>>) responseBody -> {
                    String[] responseValues = responseBody.string().split("&");
                    String token = responseValues[0].substring(responseValues[0].indexOf("=") + 1);
                    String oauth_token_secret = responseValues[1].substring(responseValues[1].indexOf("=") + 1);
                    oAuthAccessTokenResponse = new OAuthAccessTokenResponse(token,oauth_token_secret);
                    return Flowable.just(oAuthAccessTokenResponse);
                });

    }

    public Flowable<ArrayList<Tweet>> sendHomeTimelineRequest(int count,String maxId){
        return createTwitterAPIWithAccessTokenAndSecret(oAuthAccessTokenResponse.getOauth_token(),oAuthAccessTokenResponse.getOauth_token_secret()).getHomeTimeLine(count,maxId)
               ;


    }
}
