package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends Activity {

    // UI references.
    private EditText usernameField;
    private EditText passwordField;

    EditText input;

    //create getters and setters for each variable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Login.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Login.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        Login.grade = grade;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
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

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Login.password = password;
    }

    static String password;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Login.username = username;
    }

    static String username;

    Button buttons;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);

        buttons = (Button) findViewById(R.id.signUp);

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        //onclick listener when the done button is pressed on keyboard
        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (usernameField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                    } else {
                        //let the progressbar to load
                        pb.setVisibility(View.VISIBLE);
                        //veryify the login
                        loginCheck();
                        //retrieve the name and email
                        getNameFromHost();
                    }
                }
                return false;
            }
        });

        Button login = (Button) findViewById(R.id.logIn);
        //onclick for actual login button
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //make sure both the login fields are filled
                if (usernameField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                } else {
                    //set progressbar to load
                    pb.setVisibility(View.VISIBLE);
                    //verify login
                    loginCheck();
                    //retrieve name and email
                    getNameFromHost();
                }
            }
        });
    }

    //signup button action onclick
    public void action(View view) {
        //start the signup intent
        Intent dashboard = new Intent(this, SignUp.class);
        startActivity(dashboard);
    }

    //verify login method
    public void loginCheck() {
        String url = "https://sathviknallamalli.000webhostapp.com/loginwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    //if the php scropt that is in the url return success
                    Toast.makeText(getApplicationContext(), "login success!", Toast.LENGTH_SHORT).show();

                    //set the password variable and username variable appropriately
                    setPassword(passwordField.getText().toString());
                    setUsername(usernameField.getText().toString());

                    //because login is valid, setvisibility of the l=progress bar to gone
                    pb.setVisibility(View.GONE);

                    //start the actvities activity for navigation view
                    Intent activities = new Intent(getApplicationContext(), Activities.class);
                    startActivity(activities);
                    finish();

                } else {
                    //else the login is incorrect
                    Toast.makeText(getApplicationContext(), "login incorrect", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if the php script returns an error, set visibility to gone and print
                Toast.makeText(getApplicationContext(), "ERROR " + error.toString(), Toast.LENGTH_SHORT).show();

                pb.setVisibility(View.GONE);
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //put necessary parameters for the php script using the hashmap
                params.put("username", usernameField.getText().toString().trim());
                params.put("password", passwordField.getText().toString().trim());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    //get name method
    public void getNameFromHost() {
        String url = "https://sathviknallamalli.000webhostapp.com/namefromwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("incorrect") && response.length() >= 4) {
                    name = response.trim();

                    //the php script will return all the variables and will be spaced by a space
                    // this will break each string by the space and set it into the appropriate variables
                    String[] splitStr = name.split("\\s+");
                    String firstname = splitStr[0];
                    String lastname = splitStr[1];
                    String emailRaw = splitStr[2];
                    String gradeRaw = splitStr[3];

                    setFullName(firstname + " " + lastname);
                    //use the setters to set the variable
                    setEmail(emailRaw + "");
                    setGrade(gradeRaw + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING NAME" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //run the php scipt using the url and input the parameters using the hasmap
                params.put("username", usernameField.getText().toString().trim());
                params.put("password", passwordField.getText().toString().trim());

                return params;

            }
        };

        requestQueue.add(stringRequest);
    }
}






