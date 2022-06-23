package com.example.pizzeriamobile.model.http.receive;

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