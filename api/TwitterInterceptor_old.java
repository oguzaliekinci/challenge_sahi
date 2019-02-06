package com.sahibinden.challenge.api;

import android.util.Base64;

import com.sahibinden.challenge.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TwitterInterceptor_old implements Interceptor {

    private String authToken = null;
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (authToken == null) {
            ResponseBody body = chain.proceed(getAuthToken()).body();

            try {
                JSONObject jsonObject = new JSONObject(body.string());
                authToken = "Bearer " + jsonObject.optString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        request = request.newBuilder()
                .addHeader("Authorization", authToken)
                .build();

        return chain.proceed(request);
    }
    private Request getAuthToken() {
        String bearerToken = Configuration.CONSUMER_KEY + ":" + Configuration.CONSUMER_SECRET;

        String encodedToken = "Basic " + Base64.encodeToString(bearerToken.getBytes(), Base64.NO_WRAP);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), "grant_type=client_credentials");

        return new Request.Builder()
                .url(Configuration.AUTHORIZATION_URL)
                .post(requestBody)
                .header("Authorization", encodedToken)
                .header("Content-type", "application/x-www-form-urlencoded;charset=UTF-8")
                .build();
    }
   /*
   OAuth
   oauth_nonce="K7ny27JTpKVsTgdyLdDfmQQWVLERj2zAK5BslRsqyw",
   oauth_callback="http%3A%2F%2Fmyapp.com%3A3005%2Ftwitter%2Fprocess_callback",
   oauth_signature_method="HMAC-SHA1", oauth_timestamp="1300228849",
   oauth_consumer_key="OqEqJeafRSF11jBMStrZz",
   oauth_signature="Pc%2BMLdv028fxCErFyi8KXFM%2BddU%3D",
   oauth_version="1.0"
     */

}
