package com.example.pizzeriamobile.logic.model.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

public class Product {
    public final int Id;
    public final String Name;
    public final String Category;
    public final Bitmap Image;
    public final ArrayList<Variety> Varieties;
    public final ArrayList<String> Composition;
    public final boolean IsDeleted;

    private static class Variety {
        public final int Id;
        public final double Quantity;
        public final String MeasurementUnit;
        public final double Price;
        public final boolean IsDeleted;

        public static ArrayList<Variety> getFromJsonArray(@NonNull JSONArray JsonArray) throws JSONException {
            ArrayList<Variety> list = new ArrayList<>();
            for(int i = 0; i < JsonArray.length(); i++){
                list.add(new Variety(JsonArray.getJSONObject(i)));
            }
            return list;
        }

        private Variety(JSONObject JsonObject) throws JSONException {
            Id = JsonObject.getInt("id");
            Quantity = JsonObject.getDouble("quantity");
            MeasurementUnit = JsonObject.getString("measurementUnit");
            Price = JsonObject.getDouble("price");
            IsDeleted = JsonObject.getBoolean("isDeleted");
        }
    }

    /**
     * @return Product object or null
     * */
    public static ArrayList<Product> getFromJsonArray(@NonNull JSONArray JsonArray) throws JSONException {
        try {
            ArrayList<Product> list = new ArrayList<>();
            for(int i = 0; i < JsonArray.length(); i++)
                list.add(new Product(JsonArray.getJSONObject(i)));
            return list;
        }
        catch(Exception ex){
            return null;
        }
    }

    private Product(JSONObject JsonObject) throws JSONException {
        Id = JsonObject.getInt("id");
        Name = JsonObject.getString("name");
        Category = JsonObject.getString("category");
        Varieties = new ArrayList<>();
        try {
            Collection<Variety> collection = Variety.getFromJsonArray(JsonObject.getJSONArray("varieties"));
            Varieties.addAll(collection);
        }
        catch (Exception ex){ }
        Composition = new ArrayList<>();
        try {
            JSONArray array = JsonObject.getJSONArray("composition");
            for(int i = 0; i < array.length(); i++)
                Composition.add(array.getString(i));
        }
        catch (Exception ex){ }
        IsDeleted = JsonObject.getBoolean("isDeleted");

        Image = getImage(JsonObject.getString("image"));
    }

    private Bitmap getImage(String name){
        Bitmap bmp = Bitmap.createBitmap(100,100,Bitmap.Config.RGB_565);
        /*try {
            HttpURLConnection con = (HttpURLConnection) new java.net.URL("http://192.168.0.15:5000/data/image&name="+name).openConnection();
            con.setDoInput(true);
            con.connect();
            InputStream input = con.getInputStream();
            //InputStream stream = new java.net.URL("http://192.168.0.15:5000/data/image&name="+name).openStream();
            bmp = BitmapFactory.decodeStream(input);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return bmp;
    }
}
