package com.example.pizzeriamobile.logic.fragment.food.drawer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;
import com.example.pizzeriamobile.logic.activity.dialog.Appearance;
import com.example.pizzeriamobile.logic.userdata.cart.Cart;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Drawer {
    private static Drawer drawer;
    private View view;
    private RecyclerView list;
    private TextView textViewDiscount;
    private TextView textViewTotal;
    private TextView textViewTotalDiscount;

    public static void create(Context context, RelativeLayout parent){
        drawer = new Drawer(context, parent);
    }

    private Drawer(){

    }

    private Drawer(Context context, RelativeLayout parent){
        view = inflateDrawer(context, parent);
        bindListeners();
        linkView();
        inflateRecyclerView();
        updateData();
    }

    private void bindListeners(){
        ((Button)view.findViewById(R.id.buttonPlace)).setOnClickListener(onPlaceClickListener);
    }

    private View inflateDrawer(Context context, RelativeLayout parent){
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_food_drawer, parent, true);
        return view;
    }

    private void linkView(){
        list = (RecyclerView) view.findViewById(R.id.drawer_cart);
        textViewDiscount = (TextView) view.findViewById(R.id.textViewDiscountValue);
        textViewTotal = (TextView) view.findViewById(R.id.textViewTotalvalue);
        textViewTotalDiscount = (TextView) view.findViewById(R.id.textViewTotalDiscountValue);
    }

    private void inflateRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.drawer_cart);
        CartListAdapter adapter = new CartListAdapter(view.getContext(), Cart.getCart().getAll(), View.VISIBLE);
        recyclerView.setAdapter(adapter);
    }

    private void updateData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {


                Cart cart = Cart.getCart();
                double discount = 0;
                double total = cart.getTotal();
                double totalDiscount = total * (1 - discount);

                textViewDiscount.setText((int) (discount * 100) + " %");
                textViewTotal.setText(total + " р.");
                textViewTotalDiscount.setText(totalDiscount + " р.");

                CartListAdapter adapter = (CartListAdapter) list.getAdapter();
                adapter.setCart(cart.getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void notifyDataChanged(){
        updateData();
    }

    public static Drawer getDrawer(){
        return drawer;
    }

    private View.OnClickListener onPlaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ActivityDialog.class);
            intent.putExtra(Appearance.KEY, Appearance.ORDER);
            view.getContext().startActivity(intent);
        }
    };
}
