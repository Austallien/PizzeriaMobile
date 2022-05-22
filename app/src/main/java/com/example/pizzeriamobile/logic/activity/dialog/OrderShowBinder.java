package com.example.pizzeriamobile.logic.activity.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.fragment.food.drawer.CartListAdapter;
import com.example.pizzeriamobile.model.http.receive.Order;

import java.util.ArrayList;

public class OrderShowBinder {

    final private ActivityDialog activity;
    private Order order;

    private String building;
    private String delivery;
    private String receiving;
    private double total;
    private double discount;


    public OrderShowBinder(Context context) {
        this.activity = (ActivityDialog) context;
        initialize();
    }

    private void initialize() {
        load();
        display();
    }

    private void load() {
        int index = activity.getIntent().getExtras().getInt(Appearance.OrderShow.KEY);
        order = ControllerHandler.handler.getOrderController().getOrderList().get(index);
        loadValues();
    }

    private void loadValues() {
        building = order.building.City.Name + " " + order.building.Street.Name + " " + order.building.Number;
        delivery = order.delivery.length() == 0 ? order.delivery : "—";
        receiving = order.receiving.Name;
        total = order.total;
        discount = order.discount.Value;
    }

    private void display() {
        RecyclerView recyclerView = activity.findViewById(R.id.recyclerViewFragmentOrderActivityDialog);
        CartListAdapter adapter = new CartListAdapter(activity, order.getCart(), View.GONE);
        recyclerView.setAdapter(adapter);

        setTitle();
        setData();
    }

    private void setTitle() {
        String title = activity.getResources().getString(R.string.fragmentOrderListTitle) + " " + order.id;
        activity.setTitle(title);
    }

    private void setData() {
        ((TextView) activity.findViewById(R.id.textViewActivityDialogOrderShowBuildingValue)).setText(building);
        ((TextView) activity.findViewById(R.id.textViewActivityDialogOrderShowDeliveryValue)).setText(delivery);
        ((TextView) activity.findViewById(R.id.textViewActivityDialogOrderShowReceivingValue)).setText(receiving);
        ((TextView) activity.findViewById(R.id.textViewActivityDialogOrderShowTotalValue)).setText(total + " р.");
        ((TextView) activity.findViewById(R.id.textViewActivityDialogOrderShowDiscountValue)).setText(discount + "%");
        ((TextView) activity.findViewById(R.id.textViewActivityDialogOrderShowTotalDiscountValue)).setText(total * (1 - discount) + " р.");
    }
}
