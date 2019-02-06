package com.sahibinden.challenge.api.entities;

import android.os.Parcel;

public class Tweet extends Entity {

    private long id;
    private String text;
    private String id_str;

    public static Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel source) {
            final Tweet tweet = new Tweet();
            tweet.readFromParcel(source);
            return tweet;
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
    Tweet(){

    }

    public Tweet(long id, String text,String id_str) {
        this.id = id;
        this.text = text;
        this.id_str = id_str;
    }

    public long getId() {
        return id;
    }

    public String getId_str() {
        return id_str!= null ? id_str : "";
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (id != tweet.id) return false;
        if (text != null ? !text.equals(tweet.text) : tweet.text != null) return false;
        return id_str != null ? id_str.equals(tweet.id_str) : tweet.id_str == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (id_str != null ? id_str.hashCode() : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

         dest.writeLong(id);
         dest.writeString(text);
    }

    @Override
    protected void readFromParcel(Parcel parcel) {

        id = parcel.readLong();
        text = parcel.readString();
    }
}
