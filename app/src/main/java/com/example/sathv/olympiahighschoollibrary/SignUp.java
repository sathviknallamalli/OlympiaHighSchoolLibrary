package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
        Login l = new Login();
        //String user, String password, String database, String server
        Connection con = l.createConnection("sa", "Saibaba", "OlyHighLibrary", "127.0.0.1");

        try {
            String query = "insert into UserInfo (Username,Password,FirstName,LastName,Email,Pincode) values (?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username.getText().toString());
            pst.setString(2, password.getText().toString());
            pst.setString(3, firstName.getText().toString());
            pst.setString(4, lastName.getText().toString());
            pst.setString(5, email.getText().toString());
            pst.setString(6, code.getText().toString());

            pst.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
