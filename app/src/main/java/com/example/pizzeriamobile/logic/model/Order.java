package com.example.pizzeriamobile.logic.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Order {
    private int Id;
    private long RegistrationDate;
    private long RegistrationTime;
    private long ReceivingDate;
    private long ReceivingTime;
    private String ReceivingMethod;
    private String Address;
    private String DeliveryAddress;
    private double TotalPrice;
    private String Status;

    private ArrayList<Product> ProductList;

    public static Order convertFromJson(JSONObject JsonObject) throws JSONException {
        Order order = new Order();
        order.Id = JsonObject.getInt("id");
        order.RegistrationDate = JsonObject.getLong("registrationDate");
        order.RegistrationTime = JsonObject.getLong("registrationTime");
        order.ReceivingDate = JsonObject.getLong("receivingDate");
        order.ReceivingTime = JsonObject.getLong("receivingTime");
        order.ReceivingMethod = JsonObject.getString("receivingMethod");
        order.Address = JsonObject.getString("address");
        order.DeliveryAddress = JsonObject.getString("deliveryAddress");
        order.TotalPrice = JsonObject.getDouble("totalPrice");
        order.Status = JsonObject.getString("status");

        return order;
    }

    public int getId(){
        return Id;
    }

    public long getRegistrationDate(){
        return RegistrationDate;
    }

    public long getRegistrationTime(){
        return RegistrationTime;
    }

    public long getReceivingDate(){
        return ReceivingDate;
    }

    public long getReceivingTime(){
        return ReceivingTime;
    }

    public String getReceivingMethod(){
        return ReceivingMethod;
    }

    public String getAddress(){
        return Address;
    }

    public String getDeliveryAddress(){
        return DeliveryAddress;
    }

    public double getTotalPrice(){
        return TotalPrice;
    }

    public String getStatus(){
        return Status;
    }
}
