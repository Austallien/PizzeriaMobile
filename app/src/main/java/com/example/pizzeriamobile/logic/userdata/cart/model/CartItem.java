package com.example.pizzeriamobile.logic.userdata.cart.model;

import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.model.http.receive.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class CartItem {
    final public Product Product;
    final public Product.Variety Variety;
    final public CartItemType CartItemType;
    final public int Amount;

    public CartItem(Product product, Product.Variety variety, CartItemType cartItemType, int amount){
        Product = product;
        Variety = variety;
        CartItemType = cartItemType;
        Amount = amount;
    }

    public static Collection<CartItem> fromArrayList(JSONArray array){
        Collection<CartItem> collection = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject object = array.getJSONObject(i);
                int idProduct = object.getInt("idProduct");
                int idVariety = object.getInt("idVariety");

                Product product = ControllerHandler.handler.getDataController().getById(idProduct);
                Product.Variety variety = product.getVariety(idVariety);
                int amount = object.getInt("amount");

                CartItem item = new CartItem(product,variety, com.example.pizzeriamobile.logic.userdata.cart.model.CartItemType.PRODUCT, amount);
                collection.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return collection;
    }
}
