package com.example.pizzeriamobile.logic.model.cart;

public class CartItem {
    final public int Id;
    final public CartItemType CartItemType;
    final public int Amount;

    public CartItem(int id, CartItemType cartItemType, int amount){
        Id = id;
        CartItemType = cartItemType;
        Amount = amount;
    }
}
