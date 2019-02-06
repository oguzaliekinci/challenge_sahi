package com.sahibinden.challenge.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sahibinden.challenge.util.Utilities;

import javax.annotation.Nonnull;

public class UserPreferences {

    @Nonnull public static final String NAME = "userPreferences";

    @Nonnull private final SharedPreferences sharedPreferences;
    @Nonnull private final Gson gson = Utilities.createGson();

    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public  String getSharedPrefString(Context context, String key) {
        return sharedPreferences.getString(key, "");
    }

    public  void saveSharedPrefString(Context context, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void clearData(Context context) {
        sharedPreferences.edit().clear().apply();
    }

}
