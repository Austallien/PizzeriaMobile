package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.model.user.UserSingleton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthorizationController implements Runnable {

    final private Thread thread;

    final private String SUB_URL;

    private boolean isAuthorizeProcessComplete;
    private boolean authorisationResult;
    private boolean isTaskExecuting;

    protected AuthorizationController(@NonNull String resourceSubUrl){
        SUB_URL = resourceSubUrl;
        thread = new Thread(this, "AuthorizationControllerThread");
        thread.start();
    }

    /**If authorize process is already running, do nothing exclude thread returning*/
    public Thread authorize(){
        if(!isTaskExecuting)
            isTaskExecuting = true;
        return thread;
    }

    @Override
    public void run() {
        while(true) {
            Thread.yield();
            if(!isTaskExecuting)
                continue;

            isAuthorizeProcessComplete = false;
            authorisationResult = start();
            isAuthorizeProcessComplete = true;
            isTaskExecuting = false;
        }
    }

    private boolean start(){
        JSONObject userData = getUserData();
        if(userData == null)
            return false;
        
        boolean result = writeUserData(userData);
            if(!result)
                return false;

        return true;
    }

    private JSONObject getUserData(){
        try {
            String data = ServerConnectionHandler.getHandler().act(SUB_URL, true);
            return new JSONObject(data);
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean writeUserData(JSONObject data){
        try{
            UserSingleton.fromJson(data);
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isAuthorizeProcessComplete() {
        return isAuthorizeProcessComplete;
    }

    public boolean getAuthorizationResult() {
        return authorisationResult;
    }

    public boolean isTaskExecuting() {
        return isTaskExecuting;
    }
}
