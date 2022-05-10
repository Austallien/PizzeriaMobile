package com.example.pizzeriamobile.logic.fragment.food;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

public class Category implements Parent<CategoryContent> {
    final public String Category;
    private List<CategoryContent> Content;

    public Category(String category, List<CategoryContent> content){
        Category = category;
        Content = content;
    }

    @Override
    public List<CategoryContent> getChildList() {
        return Content;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
