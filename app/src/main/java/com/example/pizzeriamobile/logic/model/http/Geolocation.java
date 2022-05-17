package com.example.pizzeriamobile.logic.model.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Geolocation {

    final public ArrayList<Country> countries;

    /**Not usable*/
    private Geolocation(){
        countries = new ArrayList<>();
    }

    public Geolocation(@NonNull JSONObject jsonObject) throws JSONException {
            countries = Country.getFromJsonArray(jsonObject.getJSONArray("countries"));
    }

    @Nullable
    public Country getById(int id){
        for (Country country : countries)
            if(country.Id == id)
                return country;
        return null;
    }

    public static class Country{
        final public int Id;
        final public String Name;
        final public ArrayList<City> Cities;

        private Country(){
            Id = 0;
            Name = "";
            Cities = new ArrayList<>();
        }

        @NonNull
        protected static ArrayList<Country> getFromJsonArray(@NonNull JSONArray jsonArray) throws JSONException {
            ArrayList<Country> collection = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Country country = new Country(jsonObject);
                    collection.add(country);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return collection;
        }

        private Country(@NonNull JSONObject jsonObject) throws JSONException {
            Id = jsonObject.getInt("id");
            Name = jsonObject.getString("name");
            Cities = City.getFromJsonArray(jsonObject.getJSONArray("cities"));
        }

        @Nullable
        public City getById(int id) {
            for (City city : Cities)
                if (city.Id == id)
                    return city;
            return null;
        }

    }

    public static class City{
        final public int Id;
        final public String Name;
        final public List<Street> Streets;

        private City(){
            Id = 0;
            Name = "";
            Streets = new ArrayList<>();
        }

        @NonNull
        protected static ArrayList<City> getFromJsonArray(@NonNull JSONArray jsonArray) throws JSONException {
            ArrayList<City> collection = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    City city = new City(jsonObject);
                    collection.add(city);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return collection;
        }

        private City(@NonNull JSONObject jsonObject) throws JSONException {
            Id = jsonObject.getInt("id");
            Name = jsonObject.getString("name");
            Streets = Street.getFromJsonArray(jsonObject.getJSONArray("streets"));
        }

        public Street getById(int id){
            for(Street street : Streets)
                if(street.Id == id)
                    return street;
            return null;
        }

    }

    public static class Street{
        final public int Id;
        final public String Name;

        private Street(){
            Id = 0;
            Name = "";
        }

        @NonNull
        protected static ArrayList<Street> getFromJsonArray(@NonNull JSONArray jsonArray) throws JSONException {
            ArrayList<Street> collection = new ArrayList<>();
            for(int i = 0; i < jsonArray.length();i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Street street = new Street(jsonObject);
                    collection.add(street);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            return collection;
        }

        public Street(@NonNull JSONObject jsonObject) throws JSONException {
            Id = jsonObject.getInt("id");
            Name = jsonObject.getString("name");
        }
    }
}
