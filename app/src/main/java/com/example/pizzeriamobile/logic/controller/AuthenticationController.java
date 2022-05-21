package com.example.pizzeriamobile.logic.controller;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.logic.App;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.preference.PreferencesHandler;
import com.example.pizzeriamobile.logic.userdata.person.UserSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthenticationController implements Runnable {

    final private Thread thread;

    final private String SUB_URL;

    private String login;
    private String password;

    private boolean isAuthenticateProcessComplete;
    private boolean authenticationResult;
    private boolean isTaskExecuting;

    protected AuthenticationController(){
        SUB_URL = Url.Account.AUTHENTICATION;
        thread = new Thread(this, "AuthenticationControllerThread");
        thread.start();
    }

    /**If authenticate process is already running, do nothing exclude thread returning*/
    public Thread authenticate(String login, String password) {
        if(!isTaskExecuting) {
            this.login = login;
            this.password = password;
            isTaskExecuting = true;
        }
        return thread;
    }

    @Override
    public void run() {
        while(true) {
            Thread.yield();
            if(!isTaskExecuting)
                continue;

            isAuthenticateProcessComplete = false;
            authenticationResult = start(login, password);
            isAuthenticateProcessComplete = true;
            isTaskExecuting = false;
        }
    }

    private boolean start(String Login, String Password) {
        JSONObject tokens = getTokens(Login, Password);
        if (tokens == null)
            return false;

        boolean result = writeTokens(tokens);
        if (!result)
            return false;

        return true;
    }

    /**returns: access and refresh JWT & userId*/
    @Nullable
    private JSONObject getTokens(@NonNull String Login, @NonNull String Password){
        try{
            String link = String.format(SUB_URL, Login, Password);
            String data = ServerConnectionHandler.getHandler().act(link , false);
            return new JSONObject(data);
        }
        catch(IOException | JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private boolean writeTokens(JSONObject tokens){
        try {
            PreferencesHandler.getHandler().getAccessPreference().setJWTPair(tokens);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    public void deauthenticate(){
        PreferencesHandler.getHandler().getAccessPreference().setAccessJWT(null);
        PreferencesHandler.getHandler().getAccessPreference().setRefreshJWT(null);
        UserSingleton.getSingleton().deauthenticate();

        Toast.makeText(App.context, "Refresh token is outdated, please sign in again",Toast.LENGTH_LONG).show();
    }

    public boolean isAuthenticateProcessComplete(){
        return isAuthenticateProcessComplete;
    }

    public boolean getAuthenticationResult(){
        return authenticationResult;
    }

    public boolean isTaskExecuting(){
        return isTaskExecuting;
    }
}
