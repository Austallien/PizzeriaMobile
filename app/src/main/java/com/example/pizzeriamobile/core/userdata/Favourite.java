package com.example.pizzeriamobile.core.userdata;

import com.example.pizzeriamobile.core.preference.PreferencesHandler;

import java.util.ArrayList;

public class Favourite {
    final static public Favourite handler = new Favourite();
    final private ArrayList<Integer> content = new ArrayList<>();

    private Favourite(){
        restore();
    }

    private boolean restore(){
        ArrayList<Integer> list = PreferencesHandler.getHandler().getProductPreferences().getFavourites();
        content.addAll(list);
        return true;
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
