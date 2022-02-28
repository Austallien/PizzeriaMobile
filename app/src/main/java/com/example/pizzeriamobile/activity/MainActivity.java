package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzeriamobile.R;

public class MainActivity extends AppCompatActivity {

    Handler mUiHandler = new Handler();
    public static boolean IsHasFocus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLoad();
    }

    private void onLoad(){
        TableLayout table = (TableLayout)findViewById(R.id.TableLayout);
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
    }
}