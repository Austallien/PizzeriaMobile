package com.example.pizzeriamobile.core.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.core.userdata.person.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserPreferences {
    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;

    final private static String PREFERENCE_NAME = "User";

    final private static String KEY_REMEMBER = "remember";

    final private static String KEY_ID = "id";
    final private static String KEY_FIRST_NAME = "firstName";
    final private static String KEY_MIDDLE_NAME = "middleName";
    final private static String KEY_LAST_NAME = "lastName";
    final private static String KEY_LOGIN = "login";
    final private static String KEY_ROLE = "role";

    protected UserPreferences(@NonNull Context context){
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public boolean setRemember(boolean value) {
        try {
            editor.putBoolean(KEY_REMEMBER, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean getRemember(){
        boolean result = false;
        try{
            result = preference.getBoolean(KEY_REMEMBER, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public boolean setUser(User user){
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

    public JSONObject getUser(){
        JSONObject object = new JSONObject();

        int id =  preference.getInt(KEY_ID, -1);
        String firstName = preference.getString(KEY_FIRST_NAME, "");
        String middleName = preference.getString(KEY_MIDDLE_NAME, "");
        String lastName = preference.getString(KEY_LAST_NAME, "");
        String login = preference.getString(KEY_LOGIN, "");
        String role = preference.getString(KEY_ROLE, "");

        try {
            object.put(KEY_ID, id);
            object.put(KEY_FIRST_NAME, firstName);
            object.put(KEY_MIDDLE_NAME, middleName);
            object.put(KEY_LAST_NAME, lastName);
            object.put(KEY_LOGIN, login);
            object.put(KEY_ROLE, role);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
