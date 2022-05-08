package com.example.pizzeriamobile.logic.model.user;

import org.json.JSONObject;

public class UserSingleton {

    private static User user;

    public static User getUser(){
        if(user == null)
            user = new User();
        return user;
    }

    public static User fromJson(JSONObject Data){
        try {
            user = new User(
                    Data.getInt("id"),
                    Data.getString("firstName"),
                    Data.getString("middleName"),
                    Data.getString("lastName"),
                    Data.getString("login"),
                    Data.getString("role"));
            return user;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return getUser();
        }
    }

    public static void deauthenticate(){
        user = null;
    }
}
