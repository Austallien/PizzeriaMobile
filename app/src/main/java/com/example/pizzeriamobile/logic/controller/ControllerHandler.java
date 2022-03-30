package com.example.pizzeriamobile.logic.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.preference.AppPreferences;
import com.example.pizzeriamobile.logic.user.User;
import com.example.pizzeriamobile.logic.user.UserSingleton;

import java.util.HashMap;
import java.util.Map;

public class ControllerHandler{
    private static ControllerHandler mainController;

    private AuthenticationController authenticationController;
    private AuthorizationController authorizationController;
    private OrderDataController orderDataController;

    public static void initialize(@NonNull Context context){
        mainController = new ControllerHandler(context);
    }

    public ControllerHandler(@NonNull Context context){
        String authenticateSubUrl = context.getResources().getString(R.string.SUB_URL_AUTHENTICATION);
        String authorizeSubUrl = context.getResources().getString(R.string.SUB_URL_AUTHORIZATION);

        authenticationController = new AuthenticationController(authenticateSubUrl);
        authorizationController = new AuthorizationController(authorizeSubUrl);
    }

    public static ControllerHandler getHandler(){
        return mainController;
    }

    public AuthenticationController getAuthenticationController(){
        return authenticationController;
    }

    public AuthorizationController getAuthorizationController(){
        return authorizationController;
    }
}
