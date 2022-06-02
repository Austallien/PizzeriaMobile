package com.example.pizzeriamobile.logic.userdata.person;

import com.example.pizzeriamobile.preference.PreferencesHandler;

import org.json.JSONObject;

public class UserSingleton {

    static private UserSingleton singleton = new UserSingleton();
    private static User user;

    private UserSingleton(){
        restore();
    }

    public static UserSingleton getSingleton(){
        return singleton;
    }

    public User getUser(){
        if(user == null)
            user = new User();
        return user;
    }

    public boolean setUser(JSONObject Data){
        boolean result = false;
        try {
            user = new User(
                    Data.getInt("id"),
                    Data.getString("firstName"),
                    Data.getString("middleName"),
                    Data.getString("lastName"),
                    Data.getString("login"),
                    Data.getString("role"));
            result = PreferencesHandler.getHandler().getUserPreference().setUser(user);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    private boolean restore(){
        JSONObject object = new JSONObject();
        if(PreferencesHandler.getHandler().getUserPreference().getRemember())
            PreferencesHandler.getHandler().getUserPreference().getUser();
        boolean result = setUser(object);
        return result;
    }

    public void deauthenticate(){
        user = null;
    }
}
