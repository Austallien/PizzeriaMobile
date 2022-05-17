package com.example.pizzeriamobile.logic.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.logic.userdata.person.User;

import java.util.ArrayList;

public class UserPreference {
    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;

    final private static String PREFERENCE_NAME = "User";

    final private static String KEY_ID = "ID";
    final private static String KEY_FIRST_NAME = "FIRST_NAME";
    final private static String KEY_MIDDLE_NAME = "MIDDLE_NAME";
    final private static String KEY_LAST_NAME = "LAST_NAME";
    final private static String KEY_LOGIN = "LOGIN";
    final private static String KEY_ROLE = "ROLE";

    protected UserPreference(@NonNull Context context){
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public static boolean setUser(User user){
        try {
            editor.putInt(KEY_ID, user.getId());
            editor.putString(KEY_FIRST_NAME, user.getFirstName());
            editor.putString(KEY_MIDDLE_NAME, user.getMiddleName());
            editor.putString(KEY_LAST_NAME, user.getLastName());
            editor.putString(KEY_LOGIN, user.getLogin());
            editor.putString(KEY_ROLE, user.getRole());
            editor.apply();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static User getUser(){
        int id =  preference.getInt(KEY_ID, -1);
        String firstName = preference.getString(KEY_FIRST_NAME, "");
        String middleName = preference.getString(KEY_MIDDLE_NAME, "");
        String lastName = preference.getString(KEY_LAST_NAME, "");
        String login = preference.getString(KEY_LOGIN, "");
        String role = preference.getString(KEY_ROLE, "");

        User user = new User(
                id,
                firstName,
                middleName,
                lastName,
                login,
                role
        );

        return user;
    }
}
