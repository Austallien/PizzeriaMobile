package com.example.pizzeriamobile.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;
import com.example.pizzeriamobile.logic.activity.dialog.Appearance;
import com.example.pizzeriamobile.logic.fragment.food.drawer.CartListAdapter;
import com.example.pizzeriamobile.logic.fragment.food.category.CategoryListAdapter;
import com.example.pizzeriamobile.logic.fragment.food.category.CategoryListContentAdapter;
import com.example.pizzeriamobile.logic.fragment.food.category.ContentLoader;
import com.example.pizzeriamobile.logic.fragment.food.drawer.Drawer;
import com.example.pizzeriamobile.logic.userdata.cart.Cart;

public class FragmentFood extends Fragment implements CategoryListContentAdapter.OnTouchListener {

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
                CategoryListAdapter adapter = new CategoryListAdapter(view.getContext(), ContentLoader.loadFoodData());
                recyclerView.setAdapter(adapter);
                adapter.expandAllParents();

                Drawer.create(getActivity(), getActivity().findViewById(R.id.right_drawer));
            }
        });
    }

    @Override
    public void productListItemTouched(int productId) {
        Intent intent = new Intent(this.getActivity(), ActivityDialog.class);
        intent.putExtra(Appearance.KEY, Appearance.FOOD);
        intent.putExtra(ActivityDialog.PRODUCT_ID, productId);
        activityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK)
                        displayToast("Added");
                    else
                        displayToast("Failed to add");
                }
            }
    );

    private void displayToast(String content){
        Toast.makeText(this.getActivity(), content, Toast.LENGTH_LONG).show();
    }

}