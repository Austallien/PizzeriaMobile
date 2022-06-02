package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.Nullable;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.model.http.receive.Building;
import com.example.pizzeriamobile.model.http.receive.Geolocation;
import com.example.pizzeriamobile.model.http.send.Order;
import com.example.pizzeriamobile.model.http.receive.Product;
import com.example.pizzeriamobile.model.http.receive.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataController implements Runnable {

    final private Thread thread;

    final private String SUB_URL_FOOD_GENERAL;
    final private String SUB_URL_ADDRESS;
    final private String SUB_URL_GEOLOCATION_BUILDING;

    private boolean isDataLoadProcessComplete;
    private boolean dataLoadResult;
    private boolean isTaskExecuting;

    private ArrayList<Product> products;
    private ArrayList<String> categories;
    private ArrayList<Set> sets;
    private Geolocation addresses;
    private ArrayList<Building> buildings;


    protected DataController() {
        SUB_URL_FOOD_GENERAL = Url.Food.GENERAL;
        SUB_URL_ADDRESS = Url.Food.ADDRESS;
        SUB_URL_GEOLOCATION_BUILDING = Url.Food.BUILDING;
        thread = new Thread(this, "DataControllerThread");
        thread.start();
        reloadData();
    }

    public Thread reloadData(){
        if(!isTaskExecuting)
            isTaskExecuting = true;
        return thread;
    }

    @Override
    public void run() {
        while(true){
            Thread.yield();
            if(!isTaskExecuting)
                continue;

            isDataLoadProcessComplete = false;
            dataLoadResult = start();
            isDataLoadProcessComplete = true;
            isTaskExecuting = false;
        }
    }

    private boolean start(){
        products = loadProductData();
        if(products == null)
            return false;

        addresses = loadAddresses();
        if(addresses == null)
            return false;

        buildings = loadBuildings();
        if(buildings == null)
            return false;

        return true;
    }

    @Nullable
    private ArrayList<Product> loadProductData(){
        try{
            String data = ServerConnectionHandler.getHandler().act(SUB_URL_FOOD_GENERAL, false);
            JSONArray array = new JSONArray(data);
            ArrayList<Product> list =  Product.getFromJsonArray(array);
            categories = new ArrayList<>();
            int index = 0;
            do{
                if(!categories.contains(list.get(index).Category))
                    categories.add(list.get(index).Category);
                index++;
            }while(index < list.size());

            return list;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private Geolocation loadAddresses() {
        try {
            String data = ServerConnectionHandler.getHandler().act(SUB_URL_ADDRESS, false);
            JSONObject object = new JSONObject(data);
            Geolocation item = new Geolocation(object);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private ArrayList<Building> loadBuildings() {
        try {
            String data = ServerConnectionHandler.getHandler().act(SUB_URL_GEOLOCATION_BUILDING, false);
            JSONArray jsonArray = new JSONArray(data);
            ArrayList<Building> list = Building.fromJsonArray(jsonArray);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private ArrayList<Order> loadOrderData(){
        /*try {
            String data = ServerConnectionHandler.getHandler().act(String.format(SUB_URL_PRODUCT), false);
            JSONArray arrayData = new JSONArray(data);
            return Product.getFromJsonArray(arrayData);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }*/
        return null;
    }

    public ArrayList<Product> getProductData(){
        return products;
    }

    public Product getById(int id){
        for (Product product:products) {
            if(product.Id == id)
                return  product;
        }
        return null;
    }

    public ArrayList<String> getCategories(){
        return categories;
    }

    public Geolocation getAddresses(){
        return addresses;
    }

    public ArrayList<Building> getBuildings(){
        return buildings;
    }

    public Building getBuildingById(int id) {
        for (Building b : buildings)
            if (b.Id == id)
                return b;
        return null;
    }

    public boolean isDataLoadProcessComplete(){
        return isDataLoadProcessComplete;
    }

    public boolean dataLoadResult(){
        return dataLoadResult;
    }

    public boolean isTaskExecuting(){
        return isTaskExecuting;
    }
}
