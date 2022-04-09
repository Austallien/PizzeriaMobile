package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.fragment.FragmentFood;
import com.example.pizzeriamobile.fragment.FragmentProfile;
import com.example.pizzeriamobile.logic.activity.navigation.Drawer;
import com.example.pizzeriamobile.logic.fragment.FragmentHandler;

public class ActivityNavigation extends AppCompatActivity {

    ListView navigationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation);
        initialize();
    }

    private void initialize(){
        setupMenu();
    }

    private void setupMenu(){
        RelativeLayout drawerContainer = (RelativeLayout) findViewById(R.id.left_drawer);
        Drawer drawer = new Drawer(getApplicationContext(), drawerContainer);
        drawerContainer.addView(drawer);
        navigationList = drawer.getListView();
        navigationList.setOnItemClickListener(onItemClickListener);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String key = ((TextView)view.findViewById(R.id.textViewKey)).getText().toString();

            switch(key){
                case FragmentHandler.FOOD:
                    loadFragment(FragmentFood.newInstance());
                    break;
                case FragmentHandler.PROFILE:
                    loadFragment(FragmentProfile.newInstance());
                    break;
                case FragmentHandler.ORDERS:
                    break;
                case FragmentHandler.SETTINGS:
                    break;
                case FragmentHandler.SIGN_OUT:
                    finish();
                    break;
            }
        }
    };

    /*private void onLoad(){
        //TableLayout table = (TableLayout)findViewById(R.id.TableLayout);
        TextView[] headerTableRow = new TextView[]{
                (TextView)findViewById(R.id.textViewHeaderOrderId),
                (TextView)findViewById(R.id.textViewHeaderOrderDate),
                (TextView)findViewById(R.id.textViewHeaderOrderTotalPrice),
                (TextView)findViewById(R.id.textViewHeaderOrderReceivingMethod),
                (TextView)findViewById(R.id.textViewHeaderOrderStatus)
        };
        //new Thread(OrderDataController.InitializeAsync(this, headerTableRow, table)).start();
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