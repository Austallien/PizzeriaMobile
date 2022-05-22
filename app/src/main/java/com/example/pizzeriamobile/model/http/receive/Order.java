package com.example.pizzeriamobile.model.http.receive;

import android.service.controls.Control;

import com.example.pizzeriamobile.logic.controller.Controller;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class Order {
    final public int id;
    final public long registrationDate;
    final public long registrationTime;
    final public long receivingDate;
    final public long receivingTime;
    final public String delivery;
    final public Building building;
    final public Receiving receiving;
    final public Discount discount;
    final public double total;
    final public Status status;
    final public ArrayList<CartItem> cart;

    public Order(JSONObject jsonObject) throws JSONException {
        int idBuilding = jsonObject.getInt("idBuilding");
        int idReceiving = jsonObject.getInt("idReceivingMethod");
        int idDiscount = jsonObject.getInt("idDiscount");
        int idStatus = jsonObject.getInt("idStatus");
        id = jsonObject.getInt("id");
        registrationDate = jsonObject.getLong("registrationDate");
        registrationTime = jsonObject.getLong("registrationTime");
        receivingTime = jsonObject.getLong("receivingTime");
        receivingDate = jsonObject.getLong("receivingDate");
        delivery = jsonObject.getString("delivery");
        building = ControllerHandler.handler.getDataController().getBuildingById(idBuilding);
        receiving = ControllerHandler.handler.getOrderController().getReceivingById(idReceiving);
        discount = ControllerHandler.handler.getOrderController().getDiscountById(idDiscount);
        total = jsonObject.getDouble("totalPrice");
        status = ControllerHandler.handler.getOrderController().getStatusById(idStatus);

        cart = new ArrayList<>(CartItem.fromArrayList(jsonObject.getJSONArray("content")));
    }

    public static Collection<Order> fromJsonArray(JSONArray array) throws JSONException {
        Collection<Order> collection = new ArrayList<>();
        for(int i = 0; i < array.length(); i++)
            collection.add(new Order(array.getJSONObject(i)));
        return collection;
    }

    public ArrayList<CartItem> getCart(){
        return cart;
    }

    public static class Receiving{
        final public int Id;
        final public String Name;

        public Receiving(JSONObject object) throws JSONException {
            Id = object.getInt("id");
            Name = object.getString("name");
        }

        public static Collection<Receiving> fromJsonArray(JSONArray array) throws JSONException {
            Collection<Receiving> receiving = new ArrayList<>();
            for(int i = 0; i < array.length();i++)
                receiving.add(new Receiving(array.getJSONObject(i)));
            return receiving;
        }
    }

    public static class Discount {
        final public int Id;
        final public double Value;

        public Discount(JSONObject object) throws JSONException {
            Id = object.getInt("id");
            Value = object.getDouble("value");
        }

        public static Collection<Order.Discount> fromJsonArray(JSONArray array) {
            Collection<Order.Discount> collection = new ArrayList<>();
            for (int i = 0; i < array.length(); i++)
                try {
                    collection.add(new Discount(array.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return collection;
        }
    }

    public static class Status{
        final public int Id;
        final public String Name;

        public Status(JSONObject object) throws JSONException {
            Id = object.getInt("id");
            Name = object.getString("name");
        }

        public static Collection<Status> fromJsonArray(JSONArray array) throws JSONException {
            Collection<Status> status = new ArrayList<>();
            for(int i = 0; i < array.length();i++)
                status.add(new Status(array.getJSONObject(i)));
            return status;
        }
    }
}