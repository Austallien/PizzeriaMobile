package com.example.pizzeriamobile.logic.controller;

public class ControllerHandler {
    final static public ControllerHandler handler = new ControllerHandler();

    final private AuthenticationController authenticationController;
    final private AuthorizationController authorizationController;
    final private DataController dataController;

    final private AddressController addressController;

    final private NewAuthenticationController newAuthenticationController;
    final private NewAuthorizationController newAuthorizationController;

    private ControllerHandler() {
        authenticationController = new AuthenticationController();
        authorizationController = new AuthorizationController();
        dataController = new DataController();

        newAuthenticationController = new NewAuthenticationController("AuthenticationControllerThread_0");
        newAuthorizationController = new NewAuthorizationController("AuthorizationControllerThread_0");
        addressController = new AddressController("AddressControllerThread");
    }

    public AuthenticationController getAuthenticationController() { return authenticationController; }

    public AuthorizationController getAuthorizationController() { return authorizationController; }

    public DataController getDataController() { return dataController; }

    public NewAuthenticationController getNewAuthenticationController(){return newAuthenticationController;}
    public NewAuthorizationController getNewAuthorizationController(){return newAuthorizationController;}
}
