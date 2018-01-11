package com.example.sathv.olympiahighschoollibrary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    // UI references.
    private EditText usernameField;
    private EditText passwordField;

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

    public static Uri getUri() {
        return uri;
    }

    public static void setUri(Uri uri) {
        Login.uri = uri;
    }

    static Uri uri;

    Button buttons;
    ProgressBar pb;

    private static final int REQ_CODE = 9001;
    private GoogleApiClient googleApiClient;

    LoginButton fblogin;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        fblogin = (LoginButton) findViewById(R.id.fblogin);
        fblogin.setText("Log in with Facebook");
        // Set up the login form.
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        SignInButton thing = (SignInButton) findViewById(R.id.sign_in_button);

        TextView textView = (TextView) thing.getChildAt(0);
        textView.setText("Log in with Google");
        textView.setTextSize(14);

        buttons = (Button) findViewById(R.id.signUp);

        fblogin.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();
        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(), "Log success", Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);

                String name = loginResult.getAccessToken().getUserId();
                String email = loginResult.getAccessToken().getToken();

                setFullName(name);
                setEmail(email);

                fblogin.setText("Log in with Facebook");
                Intent activities = new Intent(getApplicationContext(), Activities.class);
                startActivity(activities);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Log cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });


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
                    Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT).show();
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

    private void updateUI(boolean isLogin) {
        if (isLogin) {

            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.GONE);
            Intent activities = new Intent(getApplicationContext(), Activities.class);
            startActivity(activities);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Logged fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        pb.setVisibility(View.VISIBLE);
        startActivityForResult(signInIntent, REQ_CODE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            pb.setVisibility(View.VISIBLE);
        }
    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            setFullName(account.getDisplayName());
            String name = account.getDisplayName();
            String email = account.getEmail();
            setEmail(account.getEmail());
            String username = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            setUsername(username);
            setUri(personPhoto);
            // String img_url = account.getPhotoUrl().toString();
            updateUI(true);
        } else {
            updateUI(false);
        }
    }

}

//RUN THE APP






