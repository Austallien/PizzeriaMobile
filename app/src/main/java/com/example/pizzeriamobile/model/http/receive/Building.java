package com.example.pizzeriamobile.model.http.receive;

import com.example.pizzeriamobile.logic.controller.ControllerHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Building {
    final public int Id;
    final public Geolocation.Country Country;
    final public Geolocation.City City;
    final public Geolocation.Street Street;
    final public String Number;

    public Building(JSONObject jsonObject) throws JSONException {
        Id = jsonObject.getInt("id");
        Country = ControllerHandler.handler.getDataController().getAddresses().getById(jsonObject.getInt("idCountry"));
        City = Country.getById(jsonObject.getInt("idCity"));
        Street = City.getById(jsonObject.getInt("idStreet"));
        Number = jsonObject.getString("number");
    }

    public static ArrayList<Building> fromJsonArray(JSONArray jsonArray){
        ArrayList<Building> collection = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Building building = new Building(jsonObject);
                collection.add(building);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return collection;
    }

    public boolean isLocatedInCity(String city){
        boolean result = City.Name.equals(city);
        return result;
    }

    public boolean isLocatedInCity(int id){
        boolean result = City.Id == id;
        return result;
    }
}
