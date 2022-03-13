package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.preference.AppPreferences;
import com.example.pizzeriamobile.logic.user.UserSingleton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthorizationController implements Runnable {

    final private Thread thread;

    final private String SUB_URL;

    private int userId;

    private boolean isAuthorizeProcessComplete;
    private boolean authorisationResult;

    protected AuthorizationController(@NonNull String resourceSubUrl){
        SUB_URL = resourceSubUrl;
        thread = new Thread(this, "AuthorizationControllerThread");
    }

    public Thread authorize(int userId){
        this.userId = userId;
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        authorisationResult = start(userId);
        isAuthorizeProcessComplete = true;
    }

    private boolean start(int userId){
        isAuthorizeProcessComplete = false;

        JSONObject userData = getUserData(userId);
        if(userData == null)
            return false;

        boolean result = writeUserData(userData);
            if(!result)
                return false;

        return true;
    }

    private JSONObject getUserData(int userId){
        try {
            HttpResponse httpResponse = ServerConnectionHandler.getHandler().execute(String.format(SUB_URL, userId), true);
            HttpEntity httpEntity = httpResponse.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

            String inputLine;
            StringBuilder data = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                data.append(inputLine);
            }
            return new JSONObject(data.toString());
        }
        catch (Exception ex){
            return null;
        }
    }

    private boolean writeUserData(JSONObject data){
        try{
            int id = data.getInt("id");
            String firstName = data.getString("firstName");
            String middleName = data.getString("lastname");
            String lastName = data.getString("middleName");
            String login = data.getString("login");
            String role = data.getString("role");

            AppPreferences.SetUserData(id, firstName, middleName, lastName, login, role);
            UserSingleton.fromJson(data);

            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

}
