package com.example.pizzeriamobile.logic.controller;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.userdata.UserHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewAuthorizationController extends Controller<String>{
    /**
     * Initialize new controller
     *
     * @param ThreadName set name of thread;
     */
    protected NewAuthorizationController(String ThreadName) {
        super(ThreadName);
        url = Url.Account.AUTHORIZATION;
    }

    /**
     * reload();
     * */
    @Override
    public void reload(Object... args) {
        super.execute(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            processResult = true;

            JSONObject object = load();
            if(object.length() == 0)
                processResult = false;

            boolean result = UserHandler.handler.signIn(object);
            if(!result)
                processResult = false;
        }
    };

    @Override
    protected JSONObject load() {
        JSONObject object = new JSONObject();
        try {
            String result = ServerConnectionHandler.handler.act(url, true);
            object = new JSONObject(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    protected String convert(JSONObject object) {
        return null;
    }
}
