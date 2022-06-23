package com.example.pizzeriamobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.core.handler.ServerConnectionHandler;

import java.io.IOException;

public class ActivityRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
    }

    private void initialize(){
        setTitle();
        bindListeners();
    }

    private void setTitle(){
        String title = getResources().getString(R.string.activityNavigationTitle);
        String value = getResources().getString(R.string.activityRegisterTitle);

        this.getSupportActionBar().setTitle(Html.fromHtml(String.format(title, value),Html.FROM_HTML_MODE_LEGACY));
    }

    private void bindListeners(){
        ((Button)findViewById(R.id.buttonActivityRegisterCommit)).setOnClickListener(onCommitClickListener);
    }

    private void register(){
        String firstName = ((EditText)findViewById(R.id.editTextActivityRegisterFirstNameValue)).getText().toString().trim();
        String middleName = ((EditText)findViewById(R.id.editTextActivityRegisterMiddleNameValue)).getText().toString().trim();
        String lastName = ((EditText)findViewById(R.id.editTextActivityRegisterLastNameValue)).getText().toString().trim();
        String phone = ((EditText)findViewById(R.id.editTextActivityRegisterPhoneValue)).getText().toString().trim();
        String login = ((EditText)findViewById(R.id.editTextActivityRegisterLoginValue)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.editTextActivityRegisterPasswordValue)).getText().toString().trim();
        String passwordCommit = ((EditText)findViewById(R.id.editTextActivityRegisterPasswordCommitValue)).getText().toString().trim();

        String exception = "";
        if(firstName.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionFirstName).replace(':','\0'));
        if(middleName.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionMiddleName).replace(':','\0'));
        if(lastName.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionLastName).replace(':','\0'));
        if(phone.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionPhone).replace(':','\0'));
        if(login.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionLogin).replace(':','\0'));
        if(password.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionPassword).replace(':','\0'));
        if(passwordCommit.length() == 0)
            exception += "\n - " + String.format(getResources().getString(R.string.toastFillField), getResources().getString(R.string.inscriptionPasswordCommit).replace(':','\0'));
        if(!password.contentEquals(passwordCommit))
            exception += "\n - " + String.format(getResources().getString(R.string.toastPasswordsNotEqual));
        exception = exception.replaceFirst("\n","");

        if(exception.length() > 0) {
            Toast.makeText(this, exception, Toast.LENGTH_LONG).show();
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String link = getResources().getString(R.string.SUB_URL_ACCOUNT_REGISTER);
                    link = String.format(link, firstName, middleName, lastName, phone, login, password);
                    String result = ServerConnectionHandler.handler.act(link, false);
                    ActivityRegister.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(result.contentEquals("true")) {
                                Toast.makeText(ActivityRegister.this, getResources().getString(R.string.toastSuccess), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                                Toast.makeText(ActivityRegister.this, getResources().getString(R.string.toastFail), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private View.OnClickListener onCommitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            register();
        }
    };
}