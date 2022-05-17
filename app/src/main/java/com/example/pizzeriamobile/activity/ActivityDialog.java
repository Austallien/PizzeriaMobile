package com.example.pizzeriamobile.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.activity.dialog.Appearance;
import com.example.pizzeriamobile.logic.activity.dialog.FoodBinder;
import com.example.pizzeriamobile.logic.activity.dialog.OrderBinder;

public class ActivityDialog extends AppCompatActivity {

    final static public String PRODUCT_ID = "PRODUCT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppearance(getIntent().getExtras());
    }

    private void setAppearance(Bundle bundle){
        int key = bundle.getInt(Appearance.KEY);
        switch (key){
            case Appearance.FOOD:
                setContentView(R.layout.activity_dialog_product);
                new FoodBinder(this);
                break;
            case Appearance.ORDER:
                setContentView(R.layout.activity_dialog_order);
                new OrderBinder(this);
                break;
        }
    }
}