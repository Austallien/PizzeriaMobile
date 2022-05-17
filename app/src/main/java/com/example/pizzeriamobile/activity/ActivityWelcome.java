package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.activity.welcome.AppPreparer;

public class ActivityWelcome extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        loading();
    }

    private void loading(){
        context = this;
        new Thread(new AppPreparer()).start();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, ActivityAuthentication.class);
                startActivity(intent);
            }
        });
    }
}