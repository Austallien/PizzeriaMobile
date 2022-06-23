package com.example.pizzeriamobile.core.controller.data;

import androidx.annotation.Nullable;

import com.example.pizzeriamobile.core.handler.Url;
import com.example.pizzeriamobile.core.controller.Controller;
import com.example.pizzeriamobile.core.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.model.http.receive.Order;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

public class OrderController extends Controller<JSONArray> {

    ArrayList<Order.Receiving> receiving;
    ArrayList<Order.Discount> discount;
    ArrayList<Order.Status> status;
    ArrayList<Order> order;

    /**
     * Initialize new controller
     *
     * @param ThreadName set name of thread;
     */
    public OrderController(String ThreadName) {
        super(ThreadName);
        url = Url.Food.ORDER;
    }

    @Override
    public void reload(Object... args) {
        this.execute(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            processResult = true;

            JSONArray array = load();
            if (array.length() == 0)
                processResult = false;

            try {
                receiving = (ArrayList<Order.Receiving>) convertReceiving(array.getJSONArray(0));
                discount = (ArrayList<Order.Discount>) convertDiscount(array.getJSONArray(1));
                status = (ArrayList<Order.Status>) convertStatus(array.getJSONArray(2));
                order = (ArrayList<Order>) convertOrder(array.getJSONArray(3));
            } catch (Exception e) {
                e.printStackTrace();
                receiving = new ArrayList<>();
                discount = new ArrayList<>();
                status = new ArrayList<>();
                order = new ArrayList<>();
            }

            if (receiving.size() == 0 || discount.size() == 0 || status.size() == 0 || order.size() == 0)
                processResult = false;
        }
    };

    @Override
    protected JSONArray load() {
        JSONArray array = new JSONArray();
        try {
            String data = ServerConnectionHandler.getHandler().act(Url.Food.ORDER, true);
            array = new JSONArray(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    protected Collection<Order.Receiving> convertReceiving(JSONArray array) {
        Collection<Order.Receiving> collection = new ArrayList<>();
        try {
            collection = Order.Receiving.fromJsonArray(array);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collection;
    }

    protected Collection<Order.Discount> convertDiscount(JSONArray array) {
        Collection<Order.Discount> collection = new ArrayList<>();
        try {
            collection = Order.Discount.fromJsonArray(array);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collection;
    }

    protected Collection<Order.Status> convertStatus(JSONArray array) {
        Collection<Order.Status> collection = new ArrayList<>();
        try {
            collection = Order.Status.fromJsonArray(array);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collection;
    }

    protected Collection<Order> convertOrder(JSONArray array) {
        Collection<Order> collection = new ArrayList<>();
        try {
            collection = Order.fromJsonArray(array);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collection;
    }

    @Nullable
    public Order.Receiving getReceivingById(int id) {
        for (Order.Receiving receiving : this.receiving)
            if (receiving.Id == id)
                return receiving;
        return null;
    }

    @Nullable
    public Order.Discount getDiscountById(int id) {
        for (Order.Discount discount : this.discount)
            if (discount.Id == id)
                return discount;
        return null;
    }

    @Nullable
    public Order.Status getStatusById(int id) {
        for (Order.Status status : this.status)
            if (status.Id == id)
                return status;
        return null;
    }

    public ArrayList<Order> getOrderList(){
        return order;
    }

    public Order getOrderById(int id) {
        for (Order order : this.order)
            if (order.id == id)
                return order;
        return null;
    }
}