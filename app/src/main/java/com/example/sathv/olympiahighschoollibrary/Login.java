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

    public static String[] getTils() {
        return tils;
    }

    public static void setTils(String[] tils) {
        Login.tils = tils;
    }

    static String[] tils;

    public static String[] getAuths() {
        return auths;
    }

    public static void setAuths(String[] auths) {
        Login.auths = auths;
    }

    static String[] auths;

    public static String[] getPgs() {
        return pgs;
    }

    public static void setPgs(String[] pgs) {
        Login.pgs = pgs;
    }

    static String[] pgs;

    public static String[] getCs() {
        return cs;
    }

    public static void setCs(String[] cs) {
        Login.cs = cs;
    }

    static String[] cs;

    public static String[] getIss() {
        return iss;
    }

    public static void setIss(String[] iss) {
        Login.iss = iss;
    }

    static String[] iss;

    public static String[] getSs() {
        return ss;
    }

    public static void setSs(String[] ss) {
        Login.ss = ss;
    }

    static String[] ss;

    public static String[] getStatuss() {
        return statuss;
    }

    public static void setStatuss(String[] statuss) {
        Login.statuss = statuss;
    }

    static String[] statuss;

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
        textView.setText("Log in using Gmail");
        textView.setTextSize(14);

        buttons = (Button) findViewById(R.id.signUp);

        fblogin.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();
        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(), "Log success"
                        , Toast.LENGTH_SHORT).show();
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
                        pb.setVisibility(View.VISIBLE);
                        loadbooks();
                        loadauthors();
                        loadpg();
                        loadcategories();
                        loadisbns();
                        loadstatus();
                        loadsummaries();

                        //  loadbooks();
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

                    pb.setVisibility(View.VISIBLE);
                    loadbooks();
                    loadauthors();
                    loadpg();
                    loadcategories();
                    loadisbns();
                    loadstatus();
                    loadsummaries();
                    //retrieve name and email

                    // loadbooks();
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
                if (!response.trim().equals("incorrect") && response.length() >= 4) {
                    //if the php scropt that is in the url return success
                    Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

                    //set the password variable and username variable appropriately
                    setPassword(passwordField.getText().toString());
                    setUsername(usernameField.getText().toString());

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

                    pb.setVisibility(View.GONE);
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

    public void loadbooks() {
        String url = "https://sathviknallamalli.000webhostapp.com/getbooks.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String booktitles = response;
                    setTils(booktitles.split(", "));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING TTILES" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }

    public void loadauthors() {
        String url = "https://sathviknallamalli.000webhostapp.com/getauthors.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String authtitles = response;
                    setAuths(authtitles.split(", "));

                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING ATUHS" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }

    public void loadpg() {
        String url = "https://sathviknallamalli.000webhostapp.com/getpagecount.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String pgs = response;
                    setPgs(pgs.split(", "));

                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING ATUHS" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }

    public void loadcategories() {
        String url = "https://sathviknallamalli.000webhostapp.com/getcategories.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String cs = response;
                    setCs(cs.split(", "));

                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING ATUHS" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }

    public void loadisbns() {
        String url = "https://sathviknallamalli.000webhostapp.com/getisbns.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String iss = response;
                    setIss(iss.split(", "));

                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING ATUHS" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }

    public void loadsummaries() {
        String url = "https://sathviknallamalli.000webhostapp.com/getsummaries.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String sss = response;
                    setSs(sss.split("<br />"));

                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING ATUHS" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }

    public void loadstatus() {
        String url = "https://sathviknallamalli.000webhostapp.com/getstatus.php";

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //pbdos.setVisibility(View.VISIBLE);
                if (!response.trim().equals("incorrect")) {

                    String status = response;
                    setStatuss(status.split(", "));

                    pb.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //display the error
                Toast.makeText(getApplicationContext(), "ERROR WHEN RETRIEVING ATUHS" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
        };

        requestQueue.add(stringRequest);
    }
}

//RUN THE APP






