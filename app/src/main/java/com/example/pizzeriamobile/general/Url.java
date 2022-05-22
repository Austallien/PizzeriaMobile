package com.example.pizzeriamobile.general;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.App;

public class Url {
    final static public Url handler = new Url(App.context);
    final public String SERVER;

    final static public Account Account = new Account(App.context);
    final static public Food Food = new Food(App.context);

    private Url(@NonNull Context context){
        SERVER = context.getResources().getString(R.string.SERVER_URL);
    }

    public static class Account{
        final public String AUTHENTICATION;
        final public String AUTHORIZATION;
        final public String REFRESH;
        final public String PROFILE;

        private Account(@NonNull Context context){
            AUTHENTICATION = context.getResources().getString(R.string.SUB_URL_ACCOUNT_AUTHENTICATION);
            AUTHORIZATION = context.getResources().getString(R.string.SUB_URL_ACCOUNT_AUTHORIZATION);
            REFRESH = context.getResources().getString(R.string.SUB_URL_ACCOUNT_REFRESH_ACCESS_TOKEN);
            PROFILE = context.getResources().getString(R.string.SUB_URL_ACCOUNT_DATA);
        }
    }

    public static class Food{
        final public String GENERAL;
        final public String ADDRESS;
        final public String BUILDING;
        final public String ORDER;

        private Food(@NonNull Context context){
            GENERAL = context.getResources().getString(R.string.SUB_URL_DATA_FOOD_GENERAL);
            ADDRESS = context.getResources().getString(R.string.SUB_URL_DATA_ADDRESS);
            BUILDING = context.getResources().getString(R.string.SUB_URL_DATA_BUILDINGS);
            ORDER = context.getResources().getString(R.string.SUB_URL_DATA_ORDER);
        }
    }
}
