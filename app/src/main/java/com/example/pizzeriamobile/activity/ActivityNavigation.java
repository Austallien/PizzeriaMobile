package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.fragment.FragmentFood;
import com.example.pizzeriamobile.fragment.FragmentProfile;
import com.example.pizzeriamobile.logic.activity.navigation.Drawer;
import com.example.pizzeriamobile.logic.activity.navigation.NavigationListAdapter;
import com.example.pizzeriamobile.logic.fragment.FragmentHandler;

public class ActivityNavigation extends AppCompatActivity implements NavigationListAdapter.OnTouchClickListener {

    private ActionBar actionBar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        actionBar = getSupportActionBar();
        title = getResources().getString(R.string.activityNavigationTitle);
        initialize();
    }

    private void initialize(){
        setupMenu();
        actionBar.setTitle(Html.fromHtml(String.format(title, getResources().getString(R.string.fragmentFoodTitle)), Html.FROM_HTML_MODE_LEGACY));
        loadFragment(FragmentFood.newInstance());
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(135,138,138)));
    }

    private void setupMenu(){
        RelativeLayout drawerContainer = (RelativeLayout) findViewById(R.id.left_drawer);
        Drawer drawer = new Drawer(getApplicationContext(), drawerContainer);
        drawerContainer.addView(drawer);
        NavigationListAdapter.registerCallback(this);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void navigationListItemTouched(String key) {
        switch(key){
            case FragmentHandler.FOOD:
                actionBar.setTitle(Html.fromHtml(String.format(title, getResources().getString(R.string.fragmentFoodTitle)), Html.FROM_HTML_MODE_LEGACY));
                loadFragment(FragmentFood.newInstance());
                break;
            case FragmentHandler.PROFILE:
                actionBar.setTitle(Html.fromHtml(String.format(title, getResources().getString(R.string.fragmentProfileTitle)), Html.FROM_HTML_MODE_LEGACY));
                loadFragment(FragmentProfile.newInstance());
                break;
            case FragmentHandler.ORDERS:
                break;
            case FragmentHandler.SETTINGS:
                break;
            case FragmentHandler.SIGN_OUT:
                Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show();
                //ControllerHandler.handler.getAuthenticationController().deauthenticate();
                //finish();
                break;
        }
    }
}