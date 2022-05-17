package com.example.pizzeriamobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.controller.AuthenticationController;
import com.example.pizzeriamobile.logic.controller.AuthorizationController;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.preference.PreferencesHandler;

public class ActivityAuthentication extends AppCompatActivity {

    final private Handler mUiHandler = new Handler();
    private boolean isCommandGiven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        initialize();
    }

    private void initialize(){

        bindListeners();
    }

    private void bindListeners() {
        Button signInButton = (Button) findViewById(R.id.buttonSignIn);
        Button signUpButton = (Button) findViewById(R.id.buttonSignUp);
        CheckBox hidePasswordCheckBox = (CheckBox) findViewById(R.id.checkBoxHidePassword);
        CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        signInButton.setOnClickListener(signInOnClickListener);
        signUpButton.setOnClickListener(signUpOnClickListener);
        hidePasswordCheckBox.setOnCheckedChangeListener(hidePasswordOnCheckedChangeListener);
        rememberMeCheckBox.setOnCheckedChangeListener(rememberMeOnCheckedChangeListener);
    }

    private boolean signIn(String login, String password){
        //authenticate
        AuthenticationController authenticationController = ControllerHandler.getHandler().getAuthenticationController();
        authenticationController.authenticate(login, password);
        while(!authenticationController.isTaskExecuting()){Thread.yield();} //awaiting authenticate starting
        while(!authenticationController.isAuthenticateProcessComplete() || authenticationController.isTaskExecuting()) {
            Thread.yield();
        }
        if(!authenticationController.getAuthenticationResult())
            return false;

        //authorize
        AuthorizationController authorizationController = ControllerHandler.getHandler().getAuthorizationController();
        authorizationController.authorize();
        while(!authorizationController.isTaskExecuting()){Thread.yield();} //awaiting authorize starting
        while (!authorizationController.isAuthorizeProcessComplete() || authorizationController.isTaskExecuting()) {
            Thread.yield();
        }
        if(!authorizationController.getAuthorizationResult())
            return false;

        return true;
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

                        boolean authenticationResult = signIn(login, password);

                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (authenticationResult) {
                                    startActivity(new Intent(ActivityAuthentication.this, ActivityNavigation.class));
                                }
                                else
                                    Toast.makeText(ActivityAuthentication.this, "Unexpected error occurred.", Toast.LENGTH_LONG).show();
                                isCommandGiven = false;
                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ActivityAuthentication.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                isCommandGiven = false;
                            }
                        });
                    }
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
                passwordEditText.setTransformationMethod(null);
            else
                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
        }
    };

    private CompoundButton.OnCheckedChangeListener rememberMeOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        }
    };
}