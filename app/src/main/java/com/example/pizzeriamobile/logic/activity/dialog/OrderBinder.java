package com.example.pizzeriamobile.logic.activity.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.fragment.food.drawer.CartListAdapter;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.model.http.Building;
import com.example.pizzeriamobile.logic.model.http.Geolocation;
import com.example.pizzeriamobile.logic.model.http.Order;
import com.example.pizzeriamobile.logic.userdata.cart.Cart;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderBinder {

    final private ActivityDialog activity;
    Geolocation.Country country;
    ArrayList<Building> buildings;

    ArrayAdapter<String> adapterCity;
    ArrayAdapter<String> adapterBuilding;

    double discount;
    double total;

    public OrderBinder(Context context){
        activity = (ActivityDialog) context;
        initialize();
    }

    private void initialize(){
        bindListeners();
        loadData();
    }

    private void bindListeners(){
        ((Button)activity.findViewById(R.id.buttonDialogOrderCancel)).setOnClickListener(onCancelClick);
        ((Button)activity.findViewById(R.id.buttonDialogOrderConfirm)).setOnClickListener(onConfirmListener);
        ((Spinner)activity.findViewById(R.id.spinnerDialogOrderCity)).setOnItemSelectedListener(onCitySelectedListener);
    }

    private void loadData(){
        country = ControllerHandler.getHandler().getDataController().getAddresses().getById(1);
        buildings = ControllerHandler.getHandler().getDataController().getBuildings();
        discount = 0;
        total = Cart.getCart().getTotal();

        displayData();
    }

    private void displayData(){
        setTitle();
        displayRecyclerView();
        displaySpinner();
        displayCartCalculation();
    }

    private void setTitle(){
        String title = activity.getResources().getString(R.string.dialogActivityPlaceTitle);
        activity.setTitle(title);
    }

    private void displayRecyclerView(){
        CartListAdapter adapter = new CartListAdapter(activity, Cart.getCart().getAll(), View.GONE);
        RecyclerView recyclerViewCart = (RecyclerView)activity.findViewById(R.id.recyclerViewDialogOrderCart);
        recyclerViewCart.setAdapter(adapter);
    }

    private void displaySpinner(){
        adapterCity = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item);
        adapterBuilding = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item);

        for(Geolocation.City city : country.Cities)
            adapterCity.add(city.Name);

        Spinner spinnerCity = ((Spinner)activity.findViewById(R.id.spinnerDialogOrderCity));
        spinnerCity.setAdapter(adapterCity);

        Spinner spinnerBuilding = ((Spinner)activity.findViewById(R.id.spinnerDialogOrderBuilding));
        spinnerBuilding.setAdapter(adapterBuilding);
    }

    private void displayCartCalculation() {
        TextView textViewDiscount = (TextView) activity.findViewById(R.id.textViewDialogOrderDiscountValue);
        TextView textViewTotal = (TextView) activity.findViewById(R.id.textViewDialogOrderTotalValue);
        TextView textViewTotalDiscount = (TextView) activity.findViewById(R.id.textViewDialogOrderTotalDiscountValue);

        String textDiscount = discount * 100 + "%";
        String textTotal = total + " р.";
        String textTotalDiscount = total * (1 - discount) + " р.";

        textViewDiscount.setText(textDiscount);
        textViewTotal.setText(textTotal);
        textViewTotalDiscount.setText(textTotalDiscount);
    }

    private View.OnClickListener onCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.finish();
        }
    };

    private View.OnClickListener onConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<CartItem> items = Cart.getCart().getAll();
                    String data = "";
                    for (CartItem item : items)
                        data += item.Variety.Id + "+" + item.Amount + "_";

                    data = data.substring(0, data.length()-1);

                    String build = (String) ((Spinner) activity.findViewById(R.id.spinnerDialogOrderBuilding)).getSelectedItem();
                    int buildingId = -1;
                    for (Building b : buildings)
                        if (b.Street.Name.equals(build.split(", ")[0]) && b.Number.equals(build.split(", ")[1]))
                            buildingId = b.Id;

                    String url = activity.getResources().getString(R.string.SUB_URL_DATA_PLACE_ORDER);
                    url = String.format(url, buildingId + "", data);


                    try {
                        String rawData = ServerConnectionHandler.getHandler().act(url, true);
                        if(rawData.equals("true")) {
                            activity.finish();
                            Cart.getCart().newCart();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    };

    private AdapterView.OnItemSelectedListener onCitySelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String city = adapterView.getSelectedItem().toString();

            adapterBuilding.clear();
            for(Building building : buildings)
                if(building.City.Name.equals(city))
                adapterBuilding.add(building.Street.Name + ", " + building.Number);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}