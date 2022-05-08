package com.example.pizzeriamobile.logic.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;

public class ControllerHandler{
    private static ControllerHandler mainController;

    private AuthenticationController authenticationController;
    private AuthorizationController authorizationController;
    private DataController dataController;

    public static void initialize(@NonNull Context context){
        mainController = new ControllerHandler(context);
    }

    public ControllerHandler(@NonNull Context context){
        String authenticateSubUrl = context.getResources().getString(R.string.SUB_URL_ACCOUNT_AUTHENTICATION);
        String authorizeSubUrl = context.getResources().getString(R.string.SUB_URL_ACCOUNT_AUTHORIZATION);
        String productDataSubUrl = context.getResources().getString(R.string.SUB_URL_DATA_FOOD_GENERAL);
        String setsDataSubUrl = context.getResources().getString(R.string.SUB_URL_DATA_FOOD_SETS);

        authenticationController = new AuthenticationController(authenticateSubUrl);
        authorizationController = new AuthorizationController(authorizeSubUrl);
        dataController = new DataController(productDataSubUrl, setsDataSubUrl);
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

    public DataController getDataController(){
        return dataController;
    }
}
