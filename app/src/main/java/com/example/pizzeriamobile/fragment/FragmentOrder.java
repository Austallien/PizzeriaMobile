package com.example.pizzeriamobile.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;
import com.example.pizzeriamobile.logic.activity.dialog.Appearance;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.controller.data.OrderController;
import com.example.pizzeriamobile.logic.fragment.order.OrderListAdapter;
import com.example.pizzeriamobile.model.http.receive.Order;

import java.util.ArrayList;

public class FragmentOrder extends Fragment {

    OrderListAdapter adapter;

    public FragmentOrder() {
        // Required empty public constructor
    }

    public static FragmentOrder newInstance() {
        FragmentOrder fragment = new FragmentOrder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Order> order = ControllerHandler.handler.getOrderController().getOrderList();

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerViewFragmentOrder);
        adapter = new OrderListAdapter(getActivity(), order);
        recyclerView.setAdapter(adapter);
    }
}