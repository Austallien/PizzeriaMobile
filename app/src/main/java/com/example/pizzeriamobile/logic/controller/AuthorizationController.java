package com.example.pizzeriamobile.logic.controller;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.userdata.person.UserSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthorizationController implements Runnable {

    final private Thread thread;

    final private String SUB_URL;

    private boolean isAuthorizeProcessComplete;
    private boolean authorisationResult;
    private boolean isTaskExecuting;

    protected AuthorizationController(){
        SUB_URL = Url.Account.AUTHORIZATION;
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
            UserSingleton.getSingleton().setUser(data);
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
