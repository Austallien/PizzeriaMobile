package com.example.pizzeriamobile.logic.handler;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.general.Url;
import com.example.pizzeriamobile.exception.InvalidRefreshToken;
import com.example.pizzeriamobile.preference.PreferencesHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConnectionHandler {

    private static String url;
    final static public ServerConnectionHandler handler = new ServerConnectionHandler();

    protected ServerConnectionHandler(){
        url = Url.handler.SERVER;
    }

    /**<p>
     * Request consists of two parts (URL + SUB_URL):<br>
     *     <p style="margin-left: 20px">URL - sets up in initialize() method, server address;<br>
     *     SUB_URL - action address on server.</p>
     * </p>
     * @param SUB_URL formatted and prepared to request link
     * @return raw json data*/
    public String act(@NonNull String SUB_URL, boolean useJWT) throws IOException {
        HttpResponse httpResponse = execute(SUB_URL, useJWT);
        HttpEntity httpEntity = httpResponse.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

        String inputLine;
        StringBuilder data = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            data.append(inputLine);
        }
        return data.toString();
    }

    /**Send http request "URL + connectionString" where URL is WebApi url which sets up by initialise() method.
     * Example: URL: http://192.168.0.2:5000/; subUrl: user/auth?login={value}*/
    private HttpResponse execute(String subUrl, boolean JWT) {
        HttpResponse httpResponse = null;
        try {
            int iteration = 0;
            do {
                if (iteration > 0)
                    switch (httpResponse.getStatusLine().getStatusCode()) {
                        case HttpStatus.SC_UNAUTHORIZED:
                            boolean refreshResult = false;
                            if (iteration == 1 && JWT)
                                refreshResult = refreshAccessToken();
                            if (!refreshResult)
                                throw new InvalidRefreshToken();
                            break;
                    }

                httpResponse = request(subUrl, JWT, true);
            } while (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK && ++iteration < 2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRefreshToken e) {
            //ControllerHandler.handler.getAuthenticationController().deauthenticate();
        }
        return httpResponse;
    }

    /**Send http request
     * @return {@link HttpResponse}
     * */
    private HttpResponse request(String subUrl, boolean addJWT, boolean isAccessToken) throws IOException {
        HttpPost httpPost = new HttpPost(url + subUrl);
        if (addJWT)
            addJWTHeader(httpPost, isAccessToken);

        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        return httpResponse;
    }

    private boolean addJWTHeader(HttpPost httpPost, boolean isAccessToken){
        try{
            String JWT = isAccessToken ?
                    PreferencesHandler.getHandler().getAccessPreference().getAccessJWT() :
                    PreferencesHandler.getHandler().getAccessPreference().getRefreshJWT();
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
            request(Url.Account.REFRESH, true,false);
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
