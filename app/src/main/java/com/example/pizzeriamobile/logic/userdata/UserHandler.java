package com.example.pizzeriamobile.logic.userdata;

import com.example.pizzeriamobile.logic.userdata.cart.Cart;

public class UserHandler {
    private static UserHandler handler;

    private boolean isAuthorized = false;

    private String accessJWT;
    private String refreshJWT;

    private UserHandler(){

    }

    public static void create(){
        handler = new UserHandler();
    }

    public static UserHandler getHandler(){
        return handler;
    }

    public void signIn(){

    }

    public void signOut(){

    }
}
