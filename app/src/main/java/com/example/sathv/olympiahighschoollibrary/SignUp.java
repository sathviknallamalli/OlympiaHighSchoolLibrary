package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends Activity {

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText email;
    EditText code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.username);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        password= (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        code = (EditText) findViewById(R.id.pinCode);
    }

    public void registerToDatabase(View view) {

    }
}
