package com.example.pizzeriamobile.core.userdata;

import com.example.pizzeriamobile.core.userdata.person.User;
import com.example.pizzeriamobile.core.userdata.person.UserSingleton;
import com.example.pizzeriamobile.core.preference.AccessPreferences;
import com.example.pizzeriamobile.core.preference.PreferencesHandler;

import org.json.JSONObject;

public class UserHandler {
    final static public UserHandler handler = new UserHandler();

    //private boolean isAuthorized = false;

    private UserHandler(){

    }

    public boolean signIn(JSONObject object) { ;
        boolean result = UserSingleton.getSingleton().setUser(object);
        return result;
    }

    public User getUser(){
        return UserSingleton.getSingleton().getUser();
    }

    //TODO
    public void signOut(){

    }

    /**
     * JSON Web Token
     * <br>
     * Contains access and refresh token logic
     * */
    public static class JWT{
        final static public JWT handler = new JWT();

        private AccessPreferences preference;

        private String accessJWT;
        private String refreshJWT;

        protected JWT(){
            preference = PreferencesHandler.getHandler().getAccessPreferences();
            restore();
        }

        //TODO: Use this instead of preference.setJWTPair at AuthenticationController
        public boolean setAccessJWT(JSONObject object){
            boolean result = PreferencesHandler.getHandler().getAccessPreferences().setAccessJWT(object);
            if(result)
                accessJWT = preference.getAccessJWT();
            return result;
        }

        public boolean setJWTPair(JSONObject object){
            boolean result = PreferencesHandler.getHandler().getAccessPreferences().setJWTPair(object);
            if(result){
                accessJWT = preference.getAccessJWT();
                refreshJWT = preference.getRefreshJWT();
            }
            return result;
        }

        public String getAccessJWT() {
            return accessJWT;
        }

        public String getRefreshJWT() {
            return refreshJWT;
        }

        private void restore(){
            accessJWT = preference.getAccessJWT();
            refreshJWT = preference.getRefreshJWT();
        }
    }
}
