package com.example.pizzeriamobile.logic.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
    final private static String PREFERENCE_FILE_NAME = "PIZZERIA_APP_PREFERENCES";

    final private static String ACCESS_JWT = "ACCESS_JWT";
    final private static String REFRESH_JWT = "REFRESH_JWT";

    final private static String USER_ID = "USER_ID";
    final private static String USER_FIRST_NAME = "USER_FIRST_NAME";
    final private static String USER_MIDDLE_NAME = "USER_MIDDLE_NAME";
    final private static String USER_LAST_NAME = "USER_LAST_NAME";
    final private static String USER_LOGIN = "USER_LOGIN";
    final private static String USER_ROLE = "USER_ROLE";

    private static SharedPreferences Preferences;

    public static boolean Initialize(Context context){
        Preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return true;
    }

    public static boolean SetAccessJWT(String value){
        try {
            Preferences.edit().putString(ACCESS_JWT, value).apply();
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public static String GetAccessJWT(){
        return Preferences.getString(ACCESS_JWT, null);
    }

    public static boolean SetRefreshJWT(String value){
        try {
            Preferences.edit().putString(REFRESH_JWT, value).apply();
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public static String GetRefreshJWT(){
        return Preferences.getString(REFRESH_JWT, null);
    }

    public static boolean SetUserData(int id, String firstName, String middleName, String lastName, String login, String role){
        try{
            Preferences.edit().putInt(USER_ID, id).apply();
            Preferences.edit().putString(USER_FIRST_NAME, firstName).apply();
            Preferences.edit().putString(USER_MIDDLE_NAME, middleName).apply();
            Preferences.edit().putString(USER_LAST_NAME, lastName).apply();
            Preferences.edit().putString(USER_LOGIN, login).apply();
            Preferences.edit().putString(USER_ROLE, role).apply();
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public static boolean SetUserId(int id){
        try{
            Preferences.edit().putInt(USER_ID, id).apply();
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public static int GetUserId(){
        return Preferences.getInt(USER_ID, -1);
    }
}
