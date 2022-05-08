package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.model.http.Order;
import com.example.pizzeriamobile.logic.model.http.Product;
import com.example.pizzeriamobile.logic.model.http.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataController implements Runnable {

    final private Thread thread;

    final private String SUB_URL_FOOD_GENERAL;
    final private String SUB_URL_FOOD_SETS;

    private boolean isDataLoadProcessComplete;
    private boolean dataLoadResult;
    private boolean isTaskExecuting;

    private ArrayList<Product> products;
    private ArrayList<Set> sets;


    protected DataController(@NonNull String resourceSubUrlProductData, @NonNull String resourceSubUrlSetsData) {
        SUB_URL_FOOD_GENERAL = resourceSubUrlProductData;
        SUB_URL_FOOD_SETS = resourceSubUrlSetsData;
        thread = new Thread(this, "DataControllerThread");
        thread.start();
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


        return true;
    }

    @Nullable
    private ArrayList<Product> loadProductData(){
        try{
            String data = ServerConnectionHandler.getHandler().act(SUB_URL_FOOD_GENERAL, false);
            JSONArray array = new JSONArray(data);
            return Product.getFromJsonArray(array);
        }
        catch (Exception e){
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
