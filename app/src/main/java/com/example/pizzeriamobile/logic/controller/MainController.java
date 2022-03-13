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

public class MainController implements Runnable{

    /**OPERATION_KEY - is KEY in map collection*/
    final private static String OPERATION_KEY = "OPERATION_KEY";

    final private static String OPERATION_NONE = "NONE";
    final private static String OPERATION_AUTHENTICATE = "AUTHENTICATE";
    final private static String OPERATION_AUTHORIZE = "AUTHORIZE";
    final private static String OPERATION_LOAD_DATA = "LOAD_DATA";

    final private static String AUTHENTICATE_KEY_LOGIN = "LOGIN";
    final private static String AUTHENTICATE_KEY_PASSWORD = "PASSWORD";



    private static Thread thread;
    private static MainController mainController;

    private AuthenticationController authenticationController;
    private AuthorizationController authorizationController;
    private OrderDataController orderDataController;
    private String baseUrl;

    private boolean isThreadActive;

    /**Contains code of current operation and values*/
    private Map<String, String> map;

    public static void initialize(@NonNull Context context){
        mainController = new MainController(context);

    }

    public MainController(@NonNull Context context){
        baseUrl = context.getResources().getString(R.string.SERVER_URL);
        String authenticateSubUrl = context.getResources().getString(R.string.SUB_URL_AUTHENTICATION);
        String authorizeSubUrl = context.getResources().getString(R.string.SUB_URL_AUTHORIZATION);

        ServerConnectionHandler.initialize(baseUrl);
        authenticationController = new AuthenticationController(authenticateSubUrl);
        authorizationController = new AuthorizationController(authorizeSubUrl);

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
                        if(authenticationController.isAuthenticateProcessComplete() && authenticationController.getAuthenticationResult())
                            map.put(OPERATION_KEY, OPERATION_AUTHORIZE);
                        else
                            map.put(OPERATION_KEY, OPERATION_NONE);
                        break;
                    case OPERATION_AUTHORIZE:
                        int userId = AppPreferences.GetUserId();
                        authorizationController.authorize(userId);
                        map.remove(OPERATION_KEY);
                        map.put(OPERATION_KEY,OPERATION_NONE);
                        break;
                    case OPERATION_LOAD_DATA:
                        map.remove(OPERATION_KEY);
                        map.put(OPERATION_KEY, OPERATION_NONE);
                        break;
                    default:

                        String accessJWT = AppPreferences.GetAccessJWT();
                        String refreshJWT = AppPreferences.GetRefreshJWT();

                        User user = UserSingleton.getUser();
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

    public void authenticate(String login, String password){
        //If authenticate process is already running
        if(map.get(OPERATION_KEY).equals(OPERATION_AUTHENTICATE))
            return;

        //else it starts
        map.put(AUTHENTICATE_KEY_LOGIN, login);
        map.put(AUTHENTICATE_KEY_PASSWORD, password);
        map.remove(OPERATION_KEY);
        map.put(OPERATION_KEY, OPERATION_AUTHENTICATE);
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
