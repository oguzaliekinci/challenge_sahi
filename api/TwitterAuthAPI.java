package com.sahibinden.challenge.api;


import com.sahibinden.challenge.api.entities.Tweet;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/***
 * Twitter 3-legged authorization
 */
public interface TwitterAuthAPI {
    /***
     * Allows a Consumer application to obtain an OAuth Request Token to request user authorization.
     * @param oauthCallback The value you specify here will be used as the URL a user is redirected to should they approve your application's access to their account
     */
    @FormUrlEncoded
    @POST("oauth/request_token")
    Flowable<ResponseBody> requestToken(@Field("oauth_callback") String oauthCallback);

    /***
     * To render the request token into a usable access token, your application must make a request to the POST oauth / access_token endpoint, containing the oauth_verifier value obtained in step 2
     * @param oauthVerifier received from authorization
     * @return A successful response contains the oauth_token, oauth_token_secret parameters. The token and token secret should be stored and used for future authenticated requests to the Twitter API
     */
    @FormUrlEncoded
    @POST("/oauth/access_token")
    Flowable<ResponseBody> getAccessToken(@Field("oauth_verifier") String oauthVerifier);


    @GET("1.1/statuses/home_timeline.json")
    Flowable<ArrayList<Tweet>> getHomeTimeLine(@Query("count") int count,@Query("max_id") String maxId);

}