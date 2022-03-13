package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.controller.AuthenticationController;
import com.example.pizzeriamobile.logic.controller.MainController;
import com.example.pizzeriamobile.logic.preference.AppPreferences;

public class AuthenticationActivity extends AppCompatActivity {

    final private Handler mUiHandler = new Handler();
    private boolean isCommandGiven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        AppPreferences.Initialize(this);
        MainController.initialize(AuthenticationActivity.this);
        listenerBinding();
    }

    private void listenerBinding() {
        Button signInButton = (Button) findViewById(R.id.buttonSignIn);
        Button signUpButton = (Button) findViewById(R.id.buttonSignUp);
        CheckBox hidePasswordCheckBox = (CheckBox) findViewById(R.id.checkBoxHidePassword);
        CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        signInButton.setOnClickListener(signInOnClickListener);
        signUpButton.setOnClickListener(signUpOnClickListener);
        hidePasswordCheckBox.setOnCheckedChangeListener(hidePasswordOnCheckedChangeListener);
        rememberMeCheckBox.setOnCheckedChangeListener(rememberMeOnCheckedChangeListener);
    }

    private View.OnClickListener signInOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isCommandGiven)
                return;

            isCommandGiven = true;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        EditText editTextLogin = (EditText) (findViewById(R.id.editTextTextLogin));
                        EditText editTextPassword = (EditText) (findViewById(R.id.editTextTextPassword));

                        String login = editTextLogin.getText().toString();
                        String password = editTextPassword.getText().toString();

                        MainController.getMainController().authenticate(login, password);
                        AuthenticationController controller = MainController.getMainController().getAuthenticationController();

                        while (!controller.isAuthenticateProcessComplete()) {
                            Thread.yield();
                        }

                        if (controller.getAuthenticationResult()) {
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(AuthenticationActivity.this, ProfileActivity.class));
                                }
                            });

                        }
                    } catch (Exception ex) {
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AuthenticationActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    isCommandGiven = false;
                }
            }).start();
        }
    };

    private View.OnClickListener signUpOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private CompoundButton.OnCheckedChangeListener hidePasswordOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            EditText passwordEditText = (EditText) findViewById(R.id.editTextTextPassword);
            if(b)
                passwordEditText.setTransformationMethod(new HideReturnsTransformationMethod());
            else
                passwordEditText.setTransformationMethod(null);
        }
    };

    private CompoundButton.OnCheckedChangeListener rememberMeOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    };
}