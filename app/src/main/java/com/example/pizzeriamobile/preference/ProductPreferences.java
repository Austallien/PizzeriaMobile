package com.example.pizzeriamobile.preference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.model.http.receive.Product;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItemType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ProductPreferences {
    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;

    final private static String PREFERENCE_NAME = "Product";

    final private static String KEY_FAVOURITE = "FAVOURITE";
    final private static String KEY_CART = "CART";

    protected ProductPreferences(@NonNull Context context) {
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public boolean setFavourites(ArrayList<Integer> content) {
        Set<String> set = new HashSet<>();
        try {
            for (int i : content)
                set.add(String.valueOf(i));
            editor.putStringSet(KEY_FAVOURITE, set).apply();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<Integer> getFavourites() {
        Set<String> set = preference.getStringSet(KEY_FAVOURITE, new HashSet<>());
        ArrayList<Integer> content = new ArrayList<>();
        Iterator<String> iterator = set.iterator();
        try {
            while(iterator.hasNext())
                content.add(Integer.parseInt(iterator.next()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Integer>();
        }

        return content;
    }

    public boolean setCart(ArrayList<CartItem> content) {
        Set<String> set = new HashSet<>();
        try {
            for (CartItem item : content)
                set.add(item.Product.Id + "_" + item.Variety.Id + "_" + item.CartItemType + "_" + item.Amount);
            editor.putStringSet(KEY_CART, set).apply();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<CartItem> getCart() {
        Set<String> set = preference.getStringSet(KEY_CART, new HashSet<>());
        ArrayList<CartItem> content = new ArrayList<>();
        Iterator<String> iterator = set.iterator();
        try {
            while (iterator.hasNext()) {
                String item = iterator.next();
                int productId = Integer.parseInt(item.split("_")[0]);
                int varietyId = Integer.parseInt(item.split("_")[1]);
                CartItemType type = CartItemType.valueOf(item.split("_")[2]);
                int amount = Integer.parseInt(item.split("_")[3]);

                Product product = ControllerHandler.handler.getDataController().getById(productId);
                Product.Variety variety = null;
                for(Product.Variety var : product.Varieties) {
                    if (var.Id == varietyId) {
                        variety = var;
                        break;
                    }
                }

                content.add(new CartItem(
                        product,
                        variety,
                        type,
                        amount
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return content;
    }
}
