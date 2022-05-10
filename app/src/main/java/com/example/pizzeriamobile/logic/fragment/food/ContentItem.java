package com.example.pizzeriamobile.logic.fragment.food;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

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
