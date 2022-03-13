package com.example.pizzeriamobile.logic.handler;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.logic.preference.AppPreferences;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class ServerConnectionHandler {

    private static String URL;
    private static ServerConnectionHandler handler;

    public static void initialize(@NonNull String baseServerUrl){
        handler = new ServerConnectionHandler(baseServerUrl);
    }

    protected ServerConnectionHandler(@NonNull String baseServerUrl){
        URL = baseServerUrl;
    }

    /**Send http request "URL + connectionString" where URL is WebApi url which sets up by initialise() method.
     * Example: URL: http://192.168.0.2:5000/; subUrl: user/auth?login={value}*/
    public HttpResponse execute(String subUrl, @NonNull boolean addJWT) throws IOException {
        HttpPost httpPost = new HttpPost(URL + subUrl);
        if(addJWT)
            addJWTHeader(httpPost, true);

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        //add access and refresh JWT checker

        return httpResponse;
    }

    private boolean addJWTHeader(HttpPost httpPost, boolean isAccessToken){
        try{
            String JWT = isAccessToken ? AppPreferences.GetAccessJWT() : AppPreferences.GetRefreshJWT();
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + JWT);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static ServerConnectionHandler getHandler(){
        return handler;
    }
}
