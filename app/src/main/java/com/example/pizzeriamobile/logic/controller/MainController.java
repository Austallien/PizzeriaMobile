package com.example.pizzeriamobile.logic.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;

import java.util.HashMap;
import java.util.Map;

public class MainController implements Runnable{

    /**MAP_VALUE_KEY - is NOT operation, DO NOT USE*/
    final private static String OPERATION_KEY = "OPERATION_KEY";
    final private static String OPERATION_NONE = "NONE";
    final private static String OPERATION_AUTHENTICATE = "AUTHENTICATE";
    final private static String OPERATION_LOAD_DATA = "LOAD_DATA";

    final private static String AUTHENTICATE_KEY_LOGIN = "LOGIN";
    final private static String AUTHENTICATE_KEY_PASSWORD = "PASSWORD";


    private static Thread thread;
    private static MainController mainController;

    private AuthenticationController authenticationController;
    private OrderDataController orderDataController;
    private String serverUrl;

    private boolean isThreadActive;

    /**Contains code of current operation and values*/
    private Map<String, String> map;

    public static void Initialize(@NonNull Context context){
        mainController = new MainController(context);
    }

    public MainController(@NonNull Context context){
        serverUrl = context.getResources().getString(R.string.SERVER_URL_NO_SSL);
        authenticationController = new AuthenticationController(serverUrl);
        map = new HashMap<>();
        map.put(OPERATION_KEY, OPERATION_NONE);
        thread = new Thread(this, "MainControllerThread");
        thread.start();
    }

    @Override
    public void run() {
        isThreadActive = true;
        while(isThreadActive){
            try {
                switch (map.get(OPERATION_KEY)) {
                    case OPERATION_AUTHENTICATE:
                        String login = map.get(AUTHENTICATE_KEY_LOGIN);
                        String password = map.get(AUTHENTICATE_KEY_PASSWORD);
                        authenticationController.authenticate(login, password).join();
                        map.remove(OPERATION_KEY);
                        map.put(OPERATION_KEY, OPERATION_LOAD_DATA);
                        break;
                    case OPERATION_LOAD_DATA:
                        orderDataController = new OrderDataController(serverUrl);
                        map.remove(OPERATION_KEY);
                        map.put(OPERATION_KEY, OPERATION_NONE);
                        break;
                    default:
                        Thread.yield();
                        break;
                }
            }
            catch (Exception ex){
                map.remove(OPERATION_KEY);
                map.put(OPERATION_KEY, OPERATION_NONE);
            }
        }
    }

    public void Authenticate(String login, String password){
        if(map.get(OPERATION_KEY) == OPERATION_AUTHENTICATE)
            return;

        map.put(AUTHENTICATE_KEY_LOGIN, login);
        map.put(AUTHENTICATE_KEY_PASSWORD, password);
        map.remove(OPERATION_KEY);
        map.put(OPERATION_KEY, OPERATION_AUTHENTICATE);
    }

    private void postAuthorizationInitialization(){
        orderDataController = new OrderDataController(serverUrl);
    }

    public void start(){
        thread.start();
    }

    public void stop(){
        isThreadActive = false;
    }

    public static MainController getMainController(){
        return mainController;
    }

    public AuthenticationController getAuthenticationController(){
        return authenticationController;
    }
}
