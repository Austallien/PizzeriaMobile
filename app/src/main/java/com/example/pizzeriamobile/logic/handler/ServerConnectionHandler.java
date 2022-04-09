package com.example.pizzeriamobile.logic.handler;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.exception.InvalidRefreshToken;
import com.example.pizzeriamobile.logic.preference.AppPreferences;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class ServerConnectionHandler {

    private static String URL;
    private static String SUB_URL_REFRESH_ACCESS_TOKEN;
    private static ServerConnectionHandler handler;

    public static void initialize(@NonNull Context context){
        String baseServerUrl = context.getResources().getString(R.string.SERVER_URL);
        String refreshAccessTokenUrl = context.getResources().getString(R.string.SUB_URL_REFRESH_ACCESS_TOKEN);
        handler = new ServerConnectionHandler(baseServerUrl, refreshAccessTokenUrl);

    }

    protected ServerConnectionHandler(@NonNull String baseServerUrl, @NonNull String refreshAccessTokenUrl){
        URL = baseServerUrl;
        SUB_URL_REFRESH_ACCESS_TOKEN = refreshAccessTokenUrl;
    }

    /**Send http request "URL + connectionString" where URL is WebApi url which sets up by initialise() method.
     * Example: URL: http://192.168.0.2:5000/; subUrl: user/auth?login={value}*/
    public HttpResponse execute(String subUrl, @NonNull boolean addJWT) {
        HttpResponse httpResponse = null;
        try {
            int iteration = 0;
            do {
                if(iteration > 0)
                switch(httpResponse.getStatusLine().getStatusCode()){
                    case HttpStatus.SC_UNAUTHORIZED:
                        boolean refreshResult = false;
                        if(iteration == 1 && addJWT)
                            refreshResult = refreshAccessToken();
                        if(!refreshResult)
                            throw new InvalidRefreshToken();
                        break;
                }

                httpResponse = request(subUrl, addJWT, true);
            }while(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK || ++iteration < 2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRefreshToken e){
            ControllerHandler.getHandler().getAuthenticationController().deauthenticate();
        }
        return httpResponse;
    }

    private HttpResponse request(String subUrl, @NonNull boolean addJWT, boolean isAccessToken) throws IOException {
        HttpPost httpPost = new HttpPost(URL + subUrl);
        if (addJWT)
            addJWTHeader(httpPost, isAccessToken);

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        return httpResponse;

    }

    private boolean addJWTHeader(HttpPost httpPost, boolean isAccessToken){
        try{
            String JWT = isAccessToken ? AppPreferences.getAccessJWT() : AppPreferences.getRefreshJWT();
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + JWT);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean refreshAccessToken(){
        try {
            request(SUB_URL_REFRESH_ACCESS_TOKEN, true,false);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ServerConnectionHandler getHandler(){
        return handler;
    }
}
