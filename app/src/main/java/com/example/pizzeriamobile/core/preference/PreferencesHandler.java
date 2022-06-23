package com.example.pizzeriamobile.core.preference;

import android.content.Context;

import com.example.pizzeriamobile.core.App;

public class PreferencesHandler {
    final static private PreferencesHandler handler = new PreferencesHandler(App.context);

    private AccessPreferences accessPreferences;
    private UserPreferences userPreferences;
    private ProductPreferences productPreferences;

    private PreferencesHandler(Context context){
        accessPreferences = new AccessPreferences(context);
        userPreferences = new UserPreferences(context);
        productPreferences = new ProductPreferences(context);
    }

    public static PreferencesHandler getHandler(){
        return handler;
    }

    public AccessPreferences getAccessPreferences(){
        return accessPreferences;
    }

    public UserPreferences getUserPreferences(){
        return userPreferences;
    }

    public ProductPreferences getProductPreferences(){
        return productPreferences;
    }
}
