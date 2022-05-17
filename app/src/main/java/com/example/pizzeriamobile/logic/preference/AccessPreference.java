package com.example.pizzeriamobile.logic.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class AccessPreference {
    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;

    final private static String PREFERENCE_NAME = "Access";

    final private static String KEY_ACCESS_JWT = "ACCESS_JWT";
    final private static String KEY_REFRESH_JWT = "REFRESH_JWT";

    protected AccessPreference(@NonNull Context context){
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public boolean setJWTPair(String access, String refresh){
        try {
            editor.putString(KEY_ACCESS_JWT, access);
            editor.putString(KEY_REFRESH_JWT, refresh);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setAccessJWT(String access){
        try {
            editor.putString(KEY_ACCESS_JWT, access);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setRefreshJWT(String refresh){
        try {
            editor.putString(KEY_REFRESH_JWT, refresh);
            editor.apply();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getAccessJWT(){
        String access = preference.getString(KEY_ACCESS_JWT, "");
        return access;
    }

    public String getRefreshJWT(){
        String refresh = preference.getString(KEY_REFRESH_JWT, "");
        return refresh;
    }
}
