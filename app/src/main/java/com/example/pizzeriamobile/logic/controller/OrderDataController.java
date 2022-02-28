package com.example.pizzeriamobile.logic.controller;

import android.content.Context;
import android.os.Handler;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.activity.OrderTableController;
import com.example.pizzeriamobile.logic.model.Order;
import com.example.pizzeriamobile.logic.user.UserSingleton;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderDataController implements Runnable {
    final private String SERVER_URL_NO_SSL_ORDER_DATA_BY_USER_ID;

    final private Thread thread;
    private ArrayList<Order> orderData;

    private boolean isDataLoadProcessComplete;

    protected OrderDataController(@NonNull String serverUrl){
        SERVER_URL_NO_SSL_ORDER_DATA_BY_USER_ID = serverUrl+"order/get:idUser="+UserSingleton.GetUser().GetId();
        thread = new Thread(this, "OrderDataControllerThread");
        thread.start();
    }

    protected void reloadData(){
        if(isDataLoadProcessComplete)
            thread.start();
    }

    @Override
    public void run() {
        orderData = getFromWebServer();
    }

    private ArrayList<Order> getFromWebServer(){
        try{
            isDataLoadProcessComplete = false;
            ArrayList<Order> orderDataList = new ArrayList<Order>();

            HttpGet httpGet = new HttpGet(SERVER_URL_NO_SSL_ORDER_DATA_BY_USER_ID);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

            String inputLine;
            StringBuilder data = new StringBuilder();
            data.append("{ result:");
            while((inputLine = reader.readLine()) != null){
                data.append(inputLine);
            }
            data.append("}");

            JSONObject jsonData = new JSONObject(data.toString());
            JSONArray jsonOrderArray =  jsonData.getJSONArray("result");

            JSONObject jsonObject;
            for(int i = 0; i < jsonOrderArray.length();i++){
                jsonObject = jsonOrderArray.getJSONObject(i);
                orderDataList.add(Order.convertFromJson(jsonObject));
            }

            isDataLoadProcessComplete = true;
            return orderDataList;
        }
        catch(Exception ex) {
            return new ArrayList<Order>();
        }
    }

    public ArrayList<Order> getData(){
        return orderData;
    }

    public Order getData(int id){
        for(int i = 0; i < orderData.size();i++)
            if(orderData.get(i).getId() == id)
                return orderData.get(i);
            return null;
    }

   /* public static Runnable InitializeAsync(@NonNull Context context, @NonNull TextView[] headerTableRow, @NonNull TableLayout table){
        Context = context;
        TableHeader = headerTableRow;
        Table = table;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                URL_NO_SSL = Context.getResources().getString(R.string.SERVER_URL_NO_SSL)+"order/";

                OrderList =new ArrayList<Order>();
                JSONObject json = LoadUserOrderList();

                Fill(json);

            }
        };
        return runnable;
    }

    private static JSONObject LoadUserOrderList(){
        try{
            HttpGet httpGet = new HttpGet(URL_NO_SSL+"get:idUser="+UserSingleton.GetUser().GetId());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

            String inputLine;
            StringBuilder data = new StringBuilder();
            data.append("{ result:");
            while((inputLine = reader.readLine()) != null){
                data.append(inputLine);
            }
            data.append("}");

            JSONObject json = new JSONObject(data.toString());
            return json;
        }
        catch(Exception ex) {
            return null;
        }
    }

    private static void Fill(JSONObject Json){
        try{
            JSONArray jsonArray =  Json.getJSONArray("result");

            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                OrderList.add(Order.GetOrder(jsonObject));
            }

            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        OrderTableController.InsertData(Context, TableHeader, Table, OrderList);
                    }
                    catch (Exception ex){
                        Toast.makeText(Context ,"Ошибка отображения данных\n"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch (Exception ex){
            mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Context ,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static ArrayList<Order> GetOrderList(){
        return OrderList;
    }*/

}
