package com.example.pizzeriamobile.core.fragment.food.category;

import android.graphics.Bitmap;

public class ContentItem {
    final public int Id;
    final public String Name;
    final public Bitmap Image;

    public ContentItem(int id, String name, Bitmap image){
        Id = id;
        Name = name;
        Image = image;
    }
}
