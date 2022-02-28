package com.example.pizzeriamobile.logic.user;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UserSingleton {

    private static User User;

    public static User GetUser(){
        if(User == null)
            User = new User();
        return User;
    }

    public static User FromJson(JSONObject Data){
        try {
            User = new User(
                    Data.getInt("id"),
                    Data.getString("firstName"),
                    Data.getString("middleName"),
                    Data.getString("lastName"),
                    Data.getString("role"),
                    Data.getString("login"));
            return User;
        }
        catch (Exception ex){
            return GetUser();
        }
    }
}
