package com.example.pizzeriamobile.logic.controller;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.userdata.UserHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthenticationController extends Controller<JSONObject>{

    private String login;
    private String password;

    /**
     * Initialize new controller
     *
     * @param ThreadName set name of thread;
     */
    protected AuthenticationController(String ThreadName) {
        super(ThreadName);
        url = Url.Account.AUTHENTICATION;
    }

    /**
     * reload((String) login, (String) password)
     * <br>
     * reload(new String { login, password})
     * */
    @Override
    public void reload(Object... data){
        login = data[0].toString();
        password = data[1].toString();
        super.execute(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            processResult = true;

            JSONObject object = load();
            if(object.length() == 0)
                processResult = false;

            boolean result = UserHandler.JWT.handler.setJWTPair(object);
            if(!result)
                processResult = false;
        }
    };

    /**
     * Get access and refresh JSON Web Tokens
     *
     * @return JWT pair in JSON format*/
    @Override
    protected JSONObject load() {
        JSONObject object = new JSONObject();
        try {
            String url = String.format(this.url, login, password);
            String result = ServerConnectionHandler.getHandler().act(url, false);
            object = new JSONObject(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
