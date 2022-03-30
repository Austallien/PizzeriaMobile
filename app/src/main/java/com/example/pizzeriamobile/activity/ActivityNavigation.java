package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.fragment.FragmentProfile;

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
        RelativeLayout left_drawer = (RelativeLayout) findViewById(R.id.left_drawer);
        ActivityNavigationDrawer activityNavigationDrawer = new ActivityNavigationDrawer(getApplicationContext(), left_drawer);
        left_drawer.addView(activityNavigationDrawer);
        navigationList = activityNavigationDrawer.getListView();
        navigationList.setOnItemClickListener(onItemClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //(ListView.ConstraintLayout).RelativeLayout.DrawerLayout
            //FrameLayout fragmentContainer = ((DrawerLayout)adapterView.getParent().getParent().getParent()).findViewById(R.id.fragment_container) ;
            switch(position){
                case 0: //profile
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, FragmentProfile.class, null)
                            .commit();
                    break;
                case 1: //orders
                    break;
                case 2: //settings
                    break;
                case 3: //sign out
                    break;
            }


            //fragmentContainer.addView(fragment.getView());
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