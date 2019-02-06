package com.sahibinden.challenge.api.entities;

import android.os.Parcel;

public class OAuthAccessTokenResponse extends Entity {

    private String oauth_token;
    private String oauth_token_secret;

    public static final Creator<OAuthAccessTokenResponse> CREATOR = new Creator<OAuthAccessTokenResponse>() {
        @Override
        public OAuthAccessTokenResponse createFromParcel(Parcel source) {
            final OAuthAccessTokenResponse oAuthRequestTokenResponse = new OAuthAccessTokenResponse();
            oAuthRequestTokenResponse.readFromParcel(source);
            return oAuthRequestTokenResponse;
        }


        @Override
        public OAuthAccessTokenResponse[] newArray(int size) {
            return new OAuthAccessTokenResponse[size];
        }
    };

    OAuthAccessTokenResponse(){

    }

    public OAuthAccessTokenResponse(String oauth_token, String oauth_token_secret) {
        this.oauth_token = oauth_token;
        this.oauth_token_secret = oauth_token_secret;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(oauth_token);
        dest.writeString(oauth_token_secret);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    protected void readFromParcel(Parcel parcel) {

        oauth_token= parcel.readString();
        oauth_token_secret = parcel.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OAuthAccessTokenResponse that = (OAuthAccessTokenResponse) o;

        if (oauth_token != null ? !oauth_token.equals(that.oauth_token) : that.oauth_token != null) return false;
        return oauth_token_secret != null ? oauth_token_secret.equals(that.oauth_token_secret) : that.oauth_token_secret == null;
    }

    @Override
    public int hashCode() {
        int result = oauth_token != null ? oauth_token.hashCode() : 0;
        result = 31 * result + (oauth_token_secret != null ? oauth_token_secret.hashCode() : 0);
        return result;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public String getOauth_token_secret() {
        return oauth_token_secret;
    }
}
