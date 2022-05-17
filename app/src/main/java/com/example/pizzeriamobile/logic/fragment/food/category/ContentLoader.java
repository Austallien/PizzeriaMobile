package com.example.pizzeriamobile.logic.fragment.food.category;

import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.controller.DataController;
import com.example.pizzeriamobile.logic.model.http.Product;

import java.util.ArrayList;

public class ContentLoader {

    public static ArrayList<Category> loadFoodData() {
        DataController data = ControllerHandler.getHandler().getDataController();
        boolean a = true;
        boolean b = false;
        while (a && !b) {
            a = data.isTaskExecuting();
            b = data.isDataLoadProcessComplete();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Category> list = new ArrayList<>();
        ArrayList<Product> products = data.getProductData();
        ArrayList<String> categories = data.getCategories();

        for (String category : categories) {
            ArrayList<ContentItem> contentItems = new ArrayList<>();
            for (Product product : products) {
                if(product.Category.equals(category))
                    contentItems.add(new ContentItem(product.Id, product.Name, product.Image));
            }
            CategoryContent categoryContent = new CategoryContent(contentItems);
            ArrayList<CategoryContent> categoryContentList =  new ArrayList<CategoryContent>();
            categoryContentList.add(categoryContent);
            Category newCategory = new Category(category, categoryContentList);
            list.add(newCategory);
        }

        return list;
    }
}
