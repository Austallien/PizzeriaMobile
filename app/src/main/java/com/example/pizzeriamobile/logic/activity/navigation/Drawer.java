package com.example.pizzeriamobile.logic.activity.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.fragment.FragmentHandler;

import java.util.ArrayList;

public class Drawer extends RelativeLayout {

    private View view;

    public Drawer(@NonNull Context context, RelativeLayout parent) {
        super(context);
        initialize(context, parent);
    }

    private void initialize(Context context, RelativeLayout parent){
        view = LayoutInflater.from(context).inflate(R.layout.activity_navigation_drawer,parent,true);

        initializeHeader(context, parent);
        initializeNavigationList(context);
    }

    private void initializeHeader(Context context, RelativeLayout parent){

    }

    private void initializeNavigationList(Context context){
        String[] inscriptions = getResources().getStringArray(R.array.navigationMenu);
        ArrayList<NavigationListAdapter.State> states = new ArrayList<NavigationListAdapter.State>();
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_food_24, inscriptions[0], FragmentHandler.FOOD));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_profile_24, inscriptions[1], FragmentHandler.PROFILE));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_orders_24, inscriptions[2], FragmentHandler.ORDERS));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_settings_24, inscriptions[3], FragmentHandler.SETTINGS));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_sign_out_24, inscriptions[4], FragmentHandler.SIGN_OUT));

        RecyclerView list = (RecyclerView) view.findViewById(R.id.drawerNavigationList);
        NavigationListAdapter adapter = new NavigationListAdapter(context, states);
        list.setAdapter(adapter);
    }
}