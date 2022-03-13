package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.preference.AppPreferences;
import com.example.pizzeriamobile.logic.user.User;
import com.example.pizzeriamobile.logic.user.UserSingleton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AuthenticationController implements Runnable {

    final private Thread thread;

    final private String SUB_URL;

    private String login;
    private String password;

    private boolean isAuthenticateProcessComplete;
    private boolean authenticationResult;

    protected AuthenticationController(@NonNull String resourceSubUrl){
        SUB_URL = resourceSubUrl;
        thread = new Thread(this, "AuthenticationControllerThread");
    }

    protected Thread authenticate(String login, String password) {
        this.login = login;
        this.password = password;
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        authenticationResult = start(login, password);
        isAuthenticateProcessComplete = true;
    }

    private boolean start(String Login, String Password) {
        isAuthenticateProcessComplete = false;

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
            HttpResponse httpResponse = ServerConnectionHandler.getHandler().execute(String.format(SUB_URL, Login, Password), false);
            HttpEntity httpEntity = httpResponse.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

            String inputLine;
            StringBuilder data = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                data.append(inputLine);
            }
            return new JSONObject(data.toString());
        }
        catch(Exception ex){
            return null;
        }
    }

    private boolean writeTokens(JSONObject tokens){
        try {
            String accessJwtToken = tokens.getString("accessJwtToken");
            String refreshJwtToken = tokens.getString("refreshJwtToken");
            int userId = tokens.getInt("userId");

            AppPreferences.SetAccessJWT(accessJwtToken);
            AppPreferences.SetRefreshJWT(refreshJwtToken);
            AppPreferences.SetUserId(userId);
        }
        catch(Exception ex){
            return false;
        }
        return true;
    }

    public boolean isAuthenticateProcessComplete(){
        return isAuthenticateProcessComplete;
    }

    public boolean getAuthenticationResult(){
        return authenticationResult;
    }
}
