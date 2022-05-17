package com.example.pizzeriamobile.logic.userdata.cart.model;

import com.example.pizzeriamobile.logic.model.http.Product;

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
}
