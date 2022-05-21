package com.example.pizzeriamobile.preference;

import android.content.Context;

import com.example.pizzeriamobile.logic.App;

public class PreferencesHandler {
    final static private PreferencesHandler handler = new PreferencesHandler(App.context);

    private AccessPreference accessPreference;
    private UserPreference userPreference;
    private ProductPreferences productPreferences;

    private PreferencesHandler(Context context){
        accessPreference = new AccessPreference(context);
        userPreference = new UserPreference(context);
        productPreferences = new ProductPreferences(context);
    }

    public static PreferencesHandler getHandler(){
        return handler;
    }

    public AccessPreference getAccessPreference(){
        return accessPreference;
    }

    public UserPreference getUserPreference(){
        return userPreference;
    }

    public ProductPreferences getProductPreferences(){
        return productPreferences;
    }

    /*final private static String PREFERENCE_FILE_NAME = "PIZZERIA_APP_PREFERENCES";

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
    }*/
}