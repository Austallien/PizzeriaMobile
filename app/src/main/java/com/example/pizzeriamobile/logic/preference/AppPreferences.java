package com.example.pizzeriamobile.logic.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    final private static String PREFERENCE_FILE_NAME = "PIZZERIA_APP_PREFERENCES";

    final private static String ACCESS_JWT = "ACCESS_JWT";
    final private static String REFRESH_JWT = "REFRESH_JWT";

    private static SharedPreferences preferences;

    public static boolean initialize(Context context){
        preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return true;
    }

    public static boolean setAccessJWT(String value){
        try {
            preferences.edit().putString(ACCESS_JWT, value).apply();
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static String getAccessJWT(){
        return preferences.getString(ACCESS_JWT, null);
    }

    public static boolean setRefreshJWT(String value){
        try {
            preferences.edit().putString(REFRESH_JWT, value).apply();
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static String getRefreshJWT(){
        return preferences.getString(REFRESH_JWT, null);
    }
}
