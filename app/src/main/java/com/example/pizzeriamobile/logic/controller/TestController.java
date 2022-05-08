package com.example.pizzeriamobile.logic.controller;

import org.json.JSONObject;

public class TestController{
    private final Controller controller;

    /**Initialize new controller
     *@param ThreadName : set name of thread;
     */
    public TestController(String ThreadName) {
        controller = new Controller("TestThread");
    }

    public void authorize(String login, String password){
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                JSONObject tokens = getTokens(Login, Password);
                if (tokens == null)
                    return false;

                boolean result = writeTokens(tokens);
                if (!result)
                    return false;

                return true;
            }
        };
        controller.execute(runnable);*/
    }
}
