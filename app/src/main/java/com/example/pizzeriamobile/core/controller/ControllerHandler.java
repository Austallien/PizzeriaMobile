package com.example.pizzeriamobile.core.controller;

import com.example.pizzeriamobile.core.controller.data.OrderController;

public class ControllerHandler {
    final static public ControllerHandler handler = new ControllerHandler();

    final private DataController dataController;

    final private AddressController addressController;

    final private AuthenticationController authenticationController;
    final private AuthorizationController authorizationController;
    final private OrderController orderController;

    private ControllerHandler() {
        dataController = new DataController();

        authenticationController = new AuthenticationController("AuthenticationControllerThread");
        authorizationController = new AuthorizationController("AuthorizationControllerThread");
        addressController = new AddressController("AddressControllerThread");
        orderController = new OrderController("OrderControllerThread");
    }

    public DataController getDataController() {
        return dataController;
    }

    public AuthenticationController getNewAuthenticationController() {
        return authenticationController;
    }

    public AuthorizationController getNewAuthorizationController() {
        return authorizationController;
    }

    public OrderController getOrderController() {
        return orderController;
    }
}
