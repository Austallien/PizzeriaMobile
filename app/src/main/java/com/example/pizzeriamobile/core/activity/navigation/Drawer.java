package com.example.pizzeriamobile.core.activity.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.core.fragment.FragmentHandler;
import com.example.pizzeriamobile.core.userdata.UserHandler;
import com.example.pizzeriamobile.core.userdata.person.User;

import java.util.ArrayList;

public class Drawer extends RelativeLayout {

    private View view;

    public Drawer(@NonNull Context context, RelativeLayout parent) {
        super(context);
        initialize(context, parent);
    }

    private void initialize(Context context, RelativeLayout parent){
        view = LayoutInflater.from(context).inflate(R.layout.activity_navigation_drawer,parent,true);

        setupUserData();
        initializeNavigationList(context);
    }

    private void setupUserData(){
        User user = UserHandler.handler.getUser();

        ((ImageView)view.findViewById(R.id.imageViewActivityNavigationDrawerImage)).setImageBitmap(user.getImage());
        ((TextView)view.findViewById(R.id.textViewActivityNavigationDrawerNameValue)).setText(user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName());
        ((TextView)view.findViewById(R.id.textViewActivityNavigationDrawerIdValue)).setText(user.getId() + "");
    }

    private void initializeNavigationList(Context context){
        ArrayList<NavigationListAdapter.State> states = new ArrayList<NavigationListAdapter.State>();
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_food_24, context.getResources().getString(R.string.navigationMenuFoodInscription), FragmentHandler.FOOD));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_profile_24, context.getResources().getString(R.string.navigationMenuProfileInscription), FragmentHandler.PROFILE));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_orders_24, context.getResources().getString(R.string.navigationMenuOrdersInscription), FragmentHandler.ORDERS));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_settings_24, context.getResources().getString(R.string.navigationMenuSettingsInscription), FragmentHandler.SETTINGS));
        states.add(new NavigationListAdapter.State(R.drawable.ic_drawer_sign_out_24, context.getResources().getString(R.string.navigationMenuSignOutInscription), FragmentHandler.SIGN_OUT));

        RecyclerView list = (RecyclerView) view.findViewById(R.id.drawerNavigationList);
        NavigationListAdapter adapter = new NavigationListAdapter(context, states);
        list.setAdapter(adapter);
    }
}