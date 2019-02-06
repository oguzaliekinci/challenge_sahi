package com.sahibinden.challenge.api.entities;

import android.os.Parcel;

public class OAuthRequestTokenResponse extends Entity {

    private String oauth_token;
    public static final Creator<OAuthRequestTokenResponse> CREATOR = new Creator<OAuthRequestTokenResponse>() {
        @Override
        public OAuthRequestTokenResponse createFromParcel(Parcel source) {
            final OAuthRequestTokenResponse oAuthRequestTokenResponse = new OAuthRequestTokenResponse();
            oAuthRequestTokenResponse.readFromParcel(source);
            return oAuthRequestTokenResponse;
        }


        @Override
        public OAuthRequestTokenResponse[] newArray(int size) {
            return new OAuthRequestTokenResponse[size];
        }
    };

    OAuthRequestTokenResponse(){

    }

    public OAuthRequestTokenResponse(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(oauth_token);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    protected void readFromParcel(Parcel parcel) {

        oauth_token= parcel.readString();
    }

    public String getOauth_token() {
        return oauth_token;
    }
}
