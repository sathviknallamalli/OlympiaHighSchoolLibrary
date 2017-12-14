package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        Login.name = name;
    }

    public  String getEmail() {
        return email;
    }

    public  void setEmail(String email) {
        Login.email = email;
    }

    public  String getGrade() {
        return grade;
    }

    public  void setGrade(String grade) {
        Login.grade = grade;
    }

    public  String getFullName() {
        return fullName;
    }

    public  void setFullName(String fullName) {
        Login.fullName = fullName;
    }

    static String name, email, grade, fullName;

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        Login.result = result;
    }

    static String result;

    private Button buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);

        buttons = (Button) findViewById(R.id.signUp);

        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (usernameField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                    } else {
                        loginCheck();
                        getNameFromHost();
                    }
                }
                return false;
            }
        });

        //COPY GET NAME CODE INTO SIGN UP AFTER REGISTER, FIX PROFILE PAGE GETTING NAME FROM LOGIN PAGE
        //TRY TO CONDENSE SQL METHODS AND MAKE REUSABLE

        Button login = (Button) findViewById(R.id.logIn);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (usernameField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                } else {
                    loginCheck();
                    getNameFromHost();
                }
            }
        });

    }

    public void action(View view) {
        Intent dashboard = new Intent(this, SignUp.class);
        startActivity(dashboard);
        //finish();
    }

    public void loginCheck() {
        String url = "https://sathviknallamalli.000webhostapp.com/loginwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "login success!", Toast.LENGTH_SHORT).show();


                    Log.d("BAD", "first inent of activities called when done is pressed");
                    Intent activities = new Intent(getApplicationContext(), Activities.class);
                    startActivity(activities);
                    finish();


                } else {
                    Toast.makeText(getApplicationContext(), "login incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
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

    public void getNameFromHost() {
        String url = "https://sathviknallamalli.000webhostapp.com/namefromwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("incorrect") && response.length() >= 4) {
                    name = response.trim();

                    String[] splitStr = name.split("\\s+");
                    String firstname = splitStr[0];
                    Log.d("BAD", firstname);
                    String lastname = splitStr[1];
                    Log.d("BAD", lastname);
                    String emailRaw = splitStr[2];
                    Log.d("BAD", emailRaw);
                    String gradeRaw = splitStr[3];
                    Log.d("BAD", gradeRaw);

                    setFullName(firstname + " " + lastname);
                    Log.d("BAD", getFullName());
                    setEmail(emailRaw + "");
                    Log.d("BAD", getEmail());
                    setGrade(gradeRaw + "");
                    Log.d("BAD", getGrade());

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






