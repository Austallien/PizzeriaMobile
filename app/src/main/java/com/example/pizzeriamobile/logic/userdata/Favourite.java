package com.example.pizzeriamobile.logic.userdata;

import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.model.http.Product;
import com.example.pizzeriamobile.logic.preference.PreferencesHandler;
import com.example.pizzeriamobile.logic.preference.ProductPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Favourite {
    private static Favourite favourite;
    final private ArrayList<Integer> content = new ArrayList<>();

    public static boolean load(){
        favourite = new Favourite();
        return true;
    }

    private Favourite(){
        ArrayList<Integer> list = PreferencesHandler.getHandler().getProductPreferences().getFavourites();
        content.addAll(list);
    }

    public static Favourite getInstance(){
        return favourite;
    }

    public boolean put(int id){
        content.add(id);
        boolean result = PreferencesHandler.getHandler().getProductPreferences().setFavourites(content);
        return result;
    }

    public boolean contain(int id){
        boolean result = content.contains(id);
        return result;
    }

    public boolean remove(int id){
        content.remove((Object)id);
        boolean result = PreferencesHandler.getHandler().getProductPreferences().setFavourites(content);
        return result;
    }
}
