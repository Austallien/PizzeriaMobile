package com.example.pizzeriamobile.core.userdata.person;

import com.example.pizzeriamobile.core.preference.PreferencesHandler;

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

    public boolean setUser(JSONObject data){
        boolean result = false;
        try {
            user = new User(
                    data.getInt("id"),
                    data.getString("firstName"),
                    data.getString("middleName"),
                    data.getString("lastName"),
                    data.getString("login"),
                    data.getString("role"),
                    null);
            result = PreferencesHandler.getHandler().getUserPreferences().setUser(user);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    private boolean restore(){
        boolean result = false;
        JSONObject object = new JSONObject();
        if(PreferencesHandler.getHandler().getUserPreferences().getRemember()) {
            PreferencesHandler.getHandler().getUserPreferences().getUser();
            result = setUser(object);
        }
        return result;
    }
}
