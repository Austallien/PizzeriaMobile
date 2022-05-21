package com.example.pizzeriamobile.logic.userdata.cart;

import com.example.pizzeriamobile.model.http.receive.Product;
import com.example.pizzeriamobile.preference.PreferencesHandler;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItemType;

import java.util.ArrayList;

public class Cart {
    final static public Cart handler = new Cart();
    private ArrayList<CartItem> content = new ArrayList<>();

    private Cart(){
        restore();
    }

    public boolean restore(){
        ArrayList<CartItem> list = PreferencesHandler.getHandler().getProductPreferences().getCart();
        content.addAll(list);
        return true;
    }

    public boolean clear(){
        content = new ArrayList<>();
        boolean result = setCart();
        return result;
    }

    public boolean add(Product product, Product.Variety variety, CartItemType type, int amount){
        CartItem item = new CartItem(product, variety, type, amount);
        content.add(item);
        boolean result = setCart();
        return result;
    }

    public CartItem get(int index){
        CartItem item = content.get(index);
        return item;
    }

    public boolean replace(Product product, Product.Variety variety, int amount){
        for(CartItem item : content){
            if(item.Variety.Id == variety.Id) {
                CartItem newItem = new CartItem(product, variety, item.CartItemType, amount);
                int index = content.indexOf(item);
                content.remove(index);
                content.add(index, newItem);
                setCart();
                return true;
            }
        }
        return false;
    }

    public ArrayList<CartItem> getVarietiesByProductId(int id){
        ArrayList<CartItem> list = new ArrayList<>();
        for(CartItem cartItem : content){
            if(cartItem.Product.Id == id)
                list.add(cartItem);
        }
        return list;
    }

    public CartItem getItemByVarietyId(int varietyId){
        for(CartItem item : content){
            if(item.Variety.Id == varietyId)
                return item;
        }
        return null;
    }

    public ArrayList<CartItem> getAll(){
        return content;
    }

    public boolean remove(int index){
        content.remove(index);
        boolean result = setCart();
        return result;
    }


    private boolean setCart(){
        boolean result = PreferencesHandler.getHandler().getProductPreferences().setCart(content);
        return result;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : content) {
            total += item.Variety.Price * item.Amount;
        }
        return total;
    }
}