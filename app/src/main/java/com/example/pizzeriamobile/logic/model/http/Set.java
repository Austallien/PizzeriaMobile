package com.example.pizzeriamobile.logic.model.http;

import org.json.JSONObject;

import java.util.List;

public class Set {
    public int Id;
    public String Size;
    public List<Product> Content;

    private Set(){}

    public Set(JSONObject jsonObject){

    }
}
