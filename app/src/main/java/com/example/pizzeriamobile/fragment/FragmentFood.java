package com.example.pizzeriamobile.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.fragment.food.CategoryListAdapter;
import com.example.pizzeriamobile.logic.fragment.food.CategoryListContentAdapter;

import java.util.ArrayList;
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

        RecyclerView recyclerView = view.findViewById(R.id.categoriesRecycleView);
        CategoryListAdapter adapter = new CategoryListAdapter(view.getContext(), loadStateData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void productListItemTouched() {
        Toast.makeText(getContext(), "LongTouchWoIsWorked", Toast.LENGTH_LONG).show();
    }

    private List<CategoryListAdapter.State> loadStateData(){
        ArrayList<CategoryListAdapter.State> list = new ArrayList<>();
        ArrayList<CategoryListContentAdapter.State> contentList1 = new ArrayList<>();
        ArrayList<CategoryListContentAdapter.State> contentList2 = new ArrayList<>();
        ArrayList<CategoryListContentAdapter.State> contentList3 = new ArrayList<>();

        contentList1.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_00"));
        contentList1.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_01"));
        contentList1.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_03"));

        contentList2.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_10"));
        contentList2.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_11"));
        contentList2.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_13"));

        contentList3.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_20"));
        contentList3.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_21"));
        contentList3.add(new CategoryListContentAdapter.State(Bitmap.createBitmap(100,100, Bitmap.Config.RGB_565), "productName_23"));

        CategoryListAdapter.State state1 = new CategoryListAdapter.State("category_0", (List)contentList1);
        CategoryListAdapter.State state2 = new CategoryListAdapter.State("category_1", (List)contentList2);
        CategoryListAdapter.State state3 = new CategoryListAdapter.State("category_2", (List)contentList3);
        list.add(state1);
        list.add(state2);
        list.add(state3);

        return list;
    }
}