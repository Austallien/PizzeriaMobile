package com.example.pizzeriamobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.core.activity.dialog.Appearance;
import com.example.pizzeriamobile.core.activity.dialog.FoodBinder;
import com.example.pizzeriamobile.core.activity.dialog.OrderPlaceBinder;
import com.example.pizzeriamobile.core.activity.dialog.OrderShowBinder;

public class ActivityDialog extends AppCompatActivity {

    final static public String PRODUCT_ID = "PRODUCT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppearance(getIntent().getExtras());
    }

    private void setAppearance(Bundle bundle){
        int key = bundle.getInt(Appearance.KEY);
        switch (key) {
            case Appearance.FOOD:
                setContentView(R.layout.activity_dialog_product);
                new FoodBinder(this);
                break;
            case Appearance.ORDER_PLACE:
                setContentView(R.layout.activity_dialog_order_place);
                new OrderPlaceBinder(this);
                break;
            case Appearance.ORDER_SHOW:
                setContentView(R.layout.activity_dialog_order_show);
                new OrderShowBinder(this);
                break;
        }
    }
}