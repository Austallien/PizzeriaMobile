package com.example.pizzeriamobile.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.service.controls.Control;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.controller.DataController;
import com.example.pizzeriamobile.logic.fragment.food.CategoryListAdapter;
import com.example.pizzeriamobile.logic.fragment.food.CategoryListContentAdapter;
import com.example.pizzeriamobile.logic.model.http.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentFood extends Fragment implements CategoryListContentAdapter.OnLongTouchListener {

    public FragmentFood() {
        // Required empty public constructor
    }

    public static FragmentFood newInstance() {
        FragmentFood fragment = new FragmentFood();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryListContentAdapter.registerCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = view.findViewById(R.id.categoriesRecycleView);
                        CategoryListAdapter adapter = new CategoryListAdapter(view.getContext(), loadStateData());
                        recyclerView.setAdapter(adapter);
                    }
                });

    }

    @Override
    public void productListItemTouched() {
        Toast.makeText(getContext(), "LongTouchWoIsWorked", Toast.LENGTH_LONG).show();
    }

    private List<CategoryListAdapter.State> loadStateData() {


        
        /*for(int i = 0; i < items.size();i++){
            Product item = items.get(i);

        }
*/
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

        ArrayList<CategoryListAdapter.State> list = new ArrayList<>();
        ArrayList<Product> products = data.getProductData();

        for (Product product : products) {
            String category = product.Category;
            //Если список категерий ещё пуст
            if (list.size() == 0)
                list.add(new CategoryListAdapter.State(category, new ArrayList<>()));
            //Перебор категорий для поиска куда вставить нынешний продукт
            for (CategoryListAdapter.State state : list) {
                //Если такая категория найдена
                if (state.getCategoryName().equals(category)) {
                    state.getContentStates().add(new CategoryListContentAdapter.State(product.Image, product.Name));
                    break;
                }
                //Если просматривается посленяя категория
                if (list.get(list.size()-1).getCategoryName().equals(state.getCategoryName())) {
                    ArrayList<CategoryListContentAdapter.State> categoryState = state.getContentStates();
                    String lastPName = categoryState.get(categoryState.size()-1).getProductName();
                    //Если даже d последней категории нет просматриваемого на данный момент продукта
                    if (!lastPName.equals(product.Name)) {
                        list.add(new CategoryListAdapter.State(category,
                                new ArrayList<CategoryListContentAdapter.State>(
                                        Arrays.asList(
                                                new CategoryListContentAdapter.State(product.Image, product.Name)
                                        )
                                )
                        )
                        );

                    }
                }
            }
        }

        return list;
    }
}