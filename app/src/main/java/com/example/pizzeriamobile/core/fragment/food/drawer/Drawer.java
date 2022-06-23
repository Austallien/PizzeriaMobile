package com.example.pizzeriamobile.core.fragment.food.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.view.activity.ActivityDialog;
import com.example.pizzeriamobile.core.activity.dialog.Appearance;
import com.example.pizzeriamobile.core.userdata.cart.Cart;

public class Drawer {
    final private Context context;

    final private DrawerLayout layout;
    final private View container;
    final private View drawer;

    private RecyclerView recyclerViewCart;
    private CartListAdapter adapterCart;

    private TextView textViewDiscount;
    private TextView textViewTotal;
    private TextView textViewTotalDiscount;

    private Button buttonPlace;
    private Button buttonClear;

    /**
     * @param layout    Layout which contains drawer
     * @param container Layout main content
     * @param drawer    Layout side drawer
     */
    public Drawer(@NonNull DrawerLayout layout, @NonNull View container, @NonNull View drawer) {
        this.layout = layout;
        this.container = container;
        this.drawer = drawer;

        context = layout.getContext();

        initialize();
    }

    private void initialize() {
        inflateDrawer();
        findView();
        bindListeners();
    }

    private void findView() {
        recyclerViewCart = drawer.findViewById(R.id.recyclerViewFragmentFoodDrawer);
        textViewDiscount = drawer.findViewById(R.id.textViewFragmentFoodDrawerDiscountValue);
        textViewTotal = drawer.findViewById(R.id.textViewFragmentFoodDrawerTotalValue);
        textViewTotalDiscount = drawer.findViewById(R.id.textViewFragmentFoodDrawerTotalDiscountValue);
        buttonClear = drawer.findViewById(R.id.buttonFragmentFoodDrawerClear);
        buttonPlace = drawer.findViewById(R.id.buttonFragmentFoodDrawerPlace);
    }

    private void inflateDrawer() {
        LayoutInflater.from(context).inflate(R.layout.fragment_food_drawer, (ViewGroup) drawer, true);
        inflateRecyclerView();
    }

    private void inflateRecyclerView() {
        RecyclerView recyclerView = drawer.findViewById(R.id.recyclerViewFragmentFoodDrawer);
        adapterCart = new CartListAdapter(drawer.getContext(), Cart.handler.getAll(), View.VISIBLE);
        recyclerView.setAdapter(adapterCart);
    }

    private void bindListeners() {
        layout.addDrawerListener(drawerListener);
        layout.findViewById(R.id.buttonFragmentFoodDrawerClear).setOnClickListener(onClearClickListener);
        layout.findViewById(R.id.buttonFragmentFoodDrawerPlace).setOnClickListener(onPlaceClickListener);
    }

    protected void updateData() {
        Cart cart = Cart.handler;
        double discount = 0;
        double total = cart.getTotal();
        double totalDiscount = total * (1 - discount);

        textViewDiscount.setText((int) (discount * 100) + " %");
        textViewTotal.setText(total + " р.");
        textViewTotalDiscount.setText(totalDiscount + " р.");

        adapterCart.setCart(cart.getAll());
        adapterCart.notifyDataSetChanged();
    }

    final private View.OnClickListener onClearClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cart.handler.clear();
            updateData();
        }
    };

    final private View.OnClickListener onPlaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ActivityDialog.class);
            intent.putExtra(Appearance.KEY, Appearance.ORDER_PLACE);
            view.getContext().startActivity(intent);
        }
    };

    final DrawerLayout.SimpleDrawerListener drawerListener = new DrawerLayout.SimpleDrawerListener() {
        boolean updated = false;

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            if (!updated) {
                updateData();
                updated = true;
                //Toast.makeText(layout.getContext(), "Updated", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            updated = false;
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }
    };
}