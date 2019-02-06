package com.sahibinden.challenge.api;

import com.sahibinden.challenge.BuildConfig;
import com.sahibinden.challenge.Configuration;
import com.sahibinden.challenge.api.entities.OAuthRequestTokenResponse;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterAuthService_old {
    private static TwitterAuthService_old service;

    private TwitterAuthAPI twitterAuthAPI;

    public synchronized static TwitterAuthService_old getInstance() {
        if (service == null) {
            service = new TwitterAuthService_old();
        }
        return service;
    }



    private TwitterAuthService_old(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                // .addInterceptor(new TwitterInterceptor())
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)

                .client(httpClient/*new OkHttpClient.Builder().addInterceptor(new TwitterInterceptor()).build()*/)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        twitterAuthAPI = retrofit.create(TwitterAuthAPI.class);
    }

    public Flowable<OAuthRequestTokenResponse> retrieveRequestToken(){
        return null;//return twitterAuthAPI.sendRequestTokenRequest(authorization);
    }
}
