package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.Nullable;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.model.http.receive.Geolocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AddressController extends Controller<ArrayList<Geolocation.Country>>{
    /**
     * Initialize new controller
     *
     * @param ThreadName name of the controller thread;
     */
    public AddressController(String ThreadName) {
        super(ThreadName);
        url = Url.Food.ADDRESS;
        reload();
    }

    @Override
    public void reload(Object... data){
        this.execute(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            processResult = true;

            JSONObject object = load();
            if(object.length() == 0)
                processResult = false;

            data = convert(object);
            if(data.size() == 0)
                processResult = false;
        }
    };

    @Nullable
    @Override
    protected JSONObject load(){
        JSONObject object = new JSONObject();
        try {
            String result = ServerConnectionHandler.getHandler().act(url, false);
            object = new JSONObject(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    protected ArrayList<Geolocation.Country> convert(JSONObject object){
        ArrayList<Geolocation.Country> list = new ArrayList<>();
        try {
             Collection collection = new Geolocation(object).countries;
             list.addAll(collection);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Geolocation.Country get(int index){
        return data.get(index);
    }

    @Nullable
    public Geolocation.Country getById(int id) {
        for (Geolocation.Country country : data)
            if (country.Id == id)
                return country;
        return null;
    }
}
