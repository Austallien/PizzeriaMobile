package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.user.User;
import com.example.pizzeriamobile.logic.user.UserSingleton;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadData();
        listenerBinding();
    }

    private void loadData(){
        TextView textViewUserFullName = (TextView) findViewById(R.id.textViewUserFullName);
        TextView textViewUserId = (TextView) findViewById(R.id.textViewUserIdValue);

        User user = UserSingleton.getUser();

        textViewUserFullName.setText(user.GetFirstName() + " " + user.GetMiddleName() + " " + user.GetLastName());
        textViewUserId.setText(String.valueOf(user.GetId()));
    }

    private void listenerBinding(){
        Button buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        Button buttonSettings = (Button) findViewById(R.id.buttonSettings);

        buttonSignOut.setOnClickListener(signOutListener);
        buttonSettings.setOnClickListener(settingsListener);
    }

    private View.OnClickListener settingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener signOutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}