package com.example.pizzeriamobile.view.activity;

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
import android.widget.TextView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.core.controller.ControllerHandler;
import com.example.pizzeriamobile.core.controller.AuthenticationController;
import com.example.pizzeriamobile.core.controller.AuthorizationController;

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
        TextView textViewRegister = (TextView) findViewById(R.id.textViewActivityAuthenticationRegisterInscription);
        CheckBox hidePasswordCheckBox = (CheckBox) findViewById(R.id.checkBoxHidePassword);
        CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.checkBoxRememberMe);

        signInButton.setOnClickListener(signInOnClickListener);
        textViewRegister.setOnClickListener(signUpOnClickListener);
        hidePasswordCheckBox.setOnCheckedChangeListener(hidePasswordOnCheckedChangeListener);
        rememberMeCheckBox.setOnCheckedChangeListener(rememberMeOnCheckedChangeListener);
    }

    private boolean signIn(String login, String password){
        //authenticate
        AuthenticationController authenticationController = ControllerHandler.handler.getNewAuthenticationController();
        authenticationController.reload(login, password);
        while(!authenticationController.isTaskExecuting()){Thread.yield();} //awaiting authenticate starting
        while(!authenticationController.isProcessComplete() || authenticationController.isTaskExecuting()) {
            Thread.yield();
        }
        if(!authenticationController.processResult())
            return false;

        //authorize
        AuthorizationController authorizationController = ControllerHandler.handler.getNewAuthorizationController();
        authorizationController.reload();
        while(!authorizationController.isTaskExecuting()){Thread.yield();} //awaiting authorize starting
        while (!authorizationController.isProcessComplete() || authorizationController.isTaskExecuting()) {
            Thread.yield();
        }
        if(!authorizationController.processResult())
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
                        EditText editTextLogin = (EditText) (findViewById(R.id.editTextActivityAuthenticationLogin));
                        EditText editTextPassword = (EditText) (findViewById(R.id.editTextActivityAuthenticationPassword));

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
                                    //Toast.makeText(ActivityAuthentication.this, "Unexpected error occurred", Toast.LENGTH_LONG).show();
                                isCommandGiven = false;
                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(ActivityAuthentication.this, ex.getMessage(), Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(ActivityAuthentication.this, ActivityRegister.class);
            startActivity(intent);
        }
    };

    private CompoundButton.OnCheckedChangeListener hidePasswordOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            EditText passwordEditText = (EditText) findViewById(R.id.editTextActivityAuthenticationPassword);
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
};