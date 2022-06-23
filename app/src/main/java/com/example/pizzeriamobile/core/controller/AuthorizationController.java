package com.example.pizzeriamobile.core.controller;

import com.example.pizzeriamobile.core.handler.Url;
import com.example.pizzeriamobile.core.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.core.userdata.UserHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthorizationController extends Controller<JSONObject>{
    /**
     * Initialize new controller
     *
     * @param ThreadName set name of thread;
     */
    protected AuthorizationController(String ThreadName) {
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
}
