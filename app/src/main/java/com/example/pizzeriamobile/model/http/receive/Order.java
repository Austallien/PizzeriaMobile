package com.example.pizzeriamobile.model.http.receive;

import com.example.pizzeriamobile.logic.controller.ControllerHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;

public class Order {
    final public int Id;
    final public long RegistrationDate;
    final public long RegistrationTime;
    final public long ReceivingDate;
    final public long ReceivingTime;
    final public Building Building;
    final public Receiving Receiving;
    final public double Discount;
    final public double Total;
    final public Status Status;

    public Order(JSONObject jsonObject) throws JSONException {
        Id = jsonObject.getInt("id");
        RegistrationDate = jsonObject.getLong("registrationDate");
        RegistrationTime = jsonObject.getLong("registrationTime");
        ReceivingTime = jsonObject.getLong("receivingTime");
        ReceivingDate = jsonObject.getLong("receivingDate");
        Building = ControllerHandler.handler.getDataController().getBuildingById(jsonObject.getInt("idBuilding"));
        Receiving = new Receiving();
        Discount = jsonObject.getDouble("discount");
        Total = jsonObject.getDouble("total");
        Status = new Status(new JSONObject(""));
    }
}
