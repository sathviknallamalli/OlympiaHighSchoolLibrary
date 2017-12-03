package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends Activity {

    // UI references.
    private EditText usernameField;
    private EditText passwordField;
    private View mLoginFormView;

    Connection connection;

    ProgressBar pb;

    static String name = "temporary for now";

    private Button buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);

        buttons = (Button) findViewById(R.id.signUp);


        passwordField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if (usernameField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                    } else {
                        Login();
                    }


                    return true;
                }
                return false;
            }

        });

        Button login = (Button) findViewById(R.id.logIn);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (usernameField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                } else {
                    Login();
                }


            }
        });

    }

    public void action(View view) {
        Intent dashboard = new Intent(this, SignUp.class);
        startActivity(dashboard);
        //finish();
    }

    public void Login() {
        String url = "https://sathviknallamalli.000webhostapp.com/loginwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Login success!", Toast.LENGTH_SHORT).show();

                    Intent activities = new Intent(getApplicationContext(), Activities.class);
                    startActivity(activities);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("username", usernameField.getText().toString().trim());
                params.put("password", passwordField.getText().toString().trim());


                return params;

            }
        };

        requestQueue.add(stringRequest);

    }

}






