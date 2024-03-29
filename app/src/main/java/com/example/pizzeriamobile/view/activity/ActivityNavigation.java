package com.example.pizzeriamobile.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.view.fragment.FragmentFood;
import com.example.pizzeriamobile.view.fragment.FragmentOrder;
import com.example.pizzeriamobile.view.fragment.FragmentProfile;
import com.example.pizzeriamobile.view.fragment.FragmentSettings;
import com.example.pizzeriamobile.core.activity.navigation.Drawer;
import com.example.pizzeriamobile.core.activity.navigation.NavigationListAdapter;
import com.example.pizzeriamobile.core.controller.ControllerHandler;
import com.example.pizzeriamobile.core.controller.data.OrderController;
import com.example.pizzeriamobile.core.fragment.FragmentHandler;

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
        OrderController controller = ControllerHandler.handler.getOrderController();
        controller.reload();


        setupMenu();
        actionBar.setTitle(Html.fromHtml(String.format(title, getResources().getString(R.string.fragmentFoodTitle)), Html.FROM_HTML_MODE_LEGACY));
        loadFragment(FragmentFood.newInstance());
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(135,138,138)));
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
                actionBar.setTitle(Html.fromHtml(String.format(title, getResources().getString(R.string.fragmentOrdersTitle)), Html.FROM_HTML_MODE_LEGACY));
                loadFragment(FragmentOrder.newInstance());
                break;
            case FragmentHandler.SETTINGS:
                actionBar.setTitle(Html.fromHtml(String.format(title, getResources().getString(R.string.fragmentSettingsTitle)), Html.FROM_HTML_MODE_LEGACY));
                loadFragment(FragmentSettings.newInstance());
                break;
            case FragmentHandler.SIGN_OUT:
                //Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show();
                //ControllerHandler.handler.getAuthenticationController().deauthenticate();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();
    }
}