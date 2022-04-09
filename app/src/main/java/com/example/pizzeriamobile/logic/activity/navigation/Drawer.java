package com.example.pizzeriamobile.logic.activity.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.fragment.FragmentHandler;

public class Drawer extends RelativeLayout {

    private View view;

    public Drawer(@NonNull Context context, RelativeLayout parent) {
        super(context);
        initialize(context, parent);
    }

    private void initialize(Context context, RelativeLayout parent){
        view = LayoutInflater.from(context).inflate(R.layout.layout_navigation_drawer_header,parent,true);

        initializeHeader(context, parent);
        initializeNavigationList(context);
    }

    private void initializeHeader(Context context, RelativeLayout parent){

    }

    private void initializeNavigationList(Context context){
        String[] inscriptions = getResources().getStringArray(R.array.navigationMenu);
        NavigationListItem[] data = new NavigationListItem[]{
                new NavigationListItem(R.drawable.ic_drawer_food_24, inscriptions[0], FragmentHandler.FOOD),
                new NavigationListItem(R.drawable.ic_drawer_profile_24, inscriptions[1], FragmentHandler.PROFILE),
                new NavigationListItem(R.drawable.ic_drawer_orders_24, inscriptions[2], FragmentHandler.ORDERS),
                new NavigationListItem(R.drawable.ic_drawer_settings_24, inscriptions[3], FragmentHandler.SETTINGS),
                new NavigationListItem(R.drawable.ic_drawer_sign_out_24, inscriptions[4], FragmentHandler.SIGN_OUT)
        };

        ListView list = (ListView)view.findViewById(R.id.drawer_list);
        NavigationList adapter = new NavigationList(context, R.layout.layout_navigation_drawer_list_item, data);
        list.setAdapter(adapter);
    }

    public ListView getListView(){
        ListView list = (ListView)view.findViewById(R.id.drawer_list);
        return list;
    }

}