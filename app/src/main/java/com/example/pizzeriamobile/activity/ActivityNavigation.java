package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.RelativeLayout;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.fragment.FragmentFood;
import com.example.pizzeriamobile.fragment.FragmentProfile;
import com.example.pizzeriamobile.logic.activity.navigation.Drawer;
import com.example.pizzeriamobile.logic.activity.navigation.NavigationListAdapter;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.fragment.FragmentHandler;

public class ActivityNavigation extends AppCompatActivity implements NavigationListAdapter.OnTouchClickListener {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        actionBar = getSupportActionBar();
        initialize();
    }

    private void initialize(){
        setupMenu();
        actionBar.setTitle(Html.fromHtml( "<font color=\"#ffffff\">" + "Food" + "<font>"));
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
                actionBar.setTitle(Html.fromHtml( "<font color=\"#ffffff\">" + "Food" + "<font>"));
                loadFragment(FragmentFood.newInstance());
                break;
            case FragmentHandler.PROFILE:
                actionBar.setTitle(Html.fromHtml( "<font color=\"#ffffff\">" + "Profile" + "<font>"));
                loadFragment(FragmentProfile.newInstance());
                break;
            case FragmentHandler.ORDERS:
                break;
            case FragmentHandler.SETTINGS:
                break;
            case FragmentHandler.SIGN_OUT:
                ControllerHandler.getHandler().getAuthenticationController().deauthenticate();
                finish();
                break;
        }
    }

    /*private void onLoad(){
        //TableLayout table = (TableLayout)findViewById(R.id.TableLayout);
        TextView[] headerTableRow = new TextView[]{
                (TextView)findViewById(R.id.textViewHeaderOrderId),
                (TextView)findViewById(R.id.textViewHeaderOrderDate),
                (TextView)findViewById(R.id.textViewHeaderOrderTotalPrice),
                (TextView)findViewById(R.id.textViewHeaderOrderReceivingMethod),
                (TextView)findViewById(R.id.textViewHeaderOrderStatus)
        };
        //new Thread(DataController.InitializeAsync(this, headerTableRow, table)).start();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Profile");
        menu.add("About program");
        menu.add("Help");
        menu.add("Sign out");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getTitle().toString()){
            case "Profile":
                Toast.makeText(this,"Profile", Toast.LENGTH_LONG).show();
                break;
            case "About program":
                Toast.makeText(this,"About program", Toast.LENGTH_LONG).show();
                break;
            case "Help":
                Toast.makeText(this,"Help", Toast.LENGTH_LONG).show();
                break;
            case "Sign Out":
                Toast.makeText(this,"Sign out", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        IsHasFocus = hasFocus;
    }*/
}