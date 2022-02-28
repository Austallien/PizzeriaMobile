package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.logic.user.User;
import com.example.pizzeriamobile.logic.user.UserSingleton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AuthenticationController implements Runnable {

    final private Thread thread;

    final private String URL_NO_SSL;
    private String URL_SSL;

    private String login;
    private String password;

    private boolean isAuthenticateProcessComplete;
    private boolean authenticationResult;

    protected AuthenticationController(@NonNull String serverUrl){
        URL_NO_SSL = serverUrl+"user/auth";
        thread = new Thread(this, "AuthenticationControllerThread");
    }

    protected Thread authenticate(String login, String password) {
        this.login = login;
        this.password = password;
        thread.start();
        return thread;
    }

    @Override
    public void run() {
        authenticationResult = tryAuthenticate(login, password);
        isAuthenticateProcessComplete = true;
    }

    private boolean tryAuthenticate(String Login, String Password) {
        isAuthenticateProcessComplete = false;

        JSONObject jsonData = httpGetData(Login, Password);
        if(jsonData == null)
            return false;

        User user = UserSingleton.FromJson(jsonData);
        if(user == null)
            return false;

        return true;
    }

    /**
     * Result: json file which contains user data;
     * Keys:
     *      | Integer id
     *      | String  firstName
     *      | String  middleName
     *      | String  lastName
     *      | String  role
     *      | String  login
     * */
    private JSONObject httpGetData(String Login, String Password){
        try {
            HttpGet httpGet = new HttpGet(String.format(URL_NO_SSL + ":login=%s&password=%s", Login, Password));
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

            String inputLine;
            StringBuilder data = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                data.append(inputLine);
            }

            JSONObject json = new JSONObject(data.toString());

            return json;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public boolean isAuthenticateProcessComplete(){
        return isAuthenticateProcessComplete;
    }

    public boolean getAuthenticationResult(){
        return authenticationResult;
    }
}
