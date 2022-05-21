package com.example.pizzeriamobile.model.http.receive;

import org.json.JSONException;
import org.json.JSONObject;

public class Status {
    final public int Id;
    final public String Value;

    public Status(JSONObject jsonObject) throws JSONException {
        Id = jsonObject.getInt("id");
        Value = jsonObject.getString("name");
    }
}
