package com.example.sathv.olympiahighschoollibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * This class is the backend for the login to screen to allow login through facebook, google, and the app account
 * It will also retrieve all the books information from the Firebase database that has connected this android project
 */
public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    // UI references.
    private EditText emailField;
    private EditText passwordField;
    String fn, ln, un, pd, em, gr;
    Button buttons;
    ProgressBar pb;
    String userclass;


    //THIS SECTION GENERATES GETTERS AND SETTERS FOR VARIABLES
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Login.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Login.email = email;
    }

    public static String getGrade() {
        return grade;
    }

    public static void setGrade(String grade) {
        Login.grade = grade;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        Login.fullName = fullName;
    }

    static String name, email, grade, fullName;

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

    static String[] ctits, cds;

    public static String[] getCtits() {
        return ctits;
    }

    public static void setCtits(String[] ctits) {
        Login.ctits = ctits;
    }

    public static String[] getCds() {
        return cds;
    }

    public static void setCds(String[] cds) {
        Login.cds = cds;
    }

    //END SECTION

    //Google API variables
    private static final int REQ_CODE = 9001;
    private GoogleApiClient googleApiClient;

    //Facebook UIS
    LoginButton fblogin;
    CallbackManager callbackManager;

    //Firebase Objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mReffname;

    //This section initializes arraylists to store the retrieve information from the databases
    //titles, authors, categories, summaries, status, isbns, pagecount, etc.
    ArrayList<String> ts = new ArrayList<>();
    ArrayList<String> aus = new ArrayList<>();
    ArrayList<String> cas = new ArrayList<>();
    ArrayList<String> ps = new ArrayList<>();
    ArrayList<String> sus = new ArrayList<>();
    ArrayList<String> sss = new ArrayList<>();
    ArrayList<String> ibss = new ArrayList<>();
    ArrayList<String> ddal = new ArrayList<>();
    ArrayList<String> ccal = new ArrayList<>();

    static String esend, namesend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //retrieve login form edittexts
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        //signup to make a new account from LiBEARy
        buttons = (Button) findViewById(R.id.signUp);

        //initialize firebase authorization variable
        mAuth = FirebaseAuth.getInstance();
        //this auth will allow access to the firebase console and authenticate the user

        //facebook login button
        fblogin = (LoginButton) findViewById(R.id.fblogin);
        fblogin.setText("Log in with Facebook");

        //THIS SECTION USES THE FACEBOOK SDK TO AUTHENTICATE
        fblogin.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();
        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(), "Log success"
                        , Toast.LENGTH_SHORT).show();
                Intent activities = new Intent(getApplicationContext(), Activities.class);
                startActivity(activities);
                finish();
                pb.setVisibility(View.GONE);

                String name = loginResult.getAccessToken().getUserId();
                String email = loginResult.getAccessToken().getToken();

                setFullName(name);
                setEmail(email);


                fblogin.setText("Log in with Facebook");

                getallbooks();
                getchecked();


                esend = emailField.getText().toString();
                namesend = Login.getFullName();


            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Log in with Facebook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //END SECTION

        //THIS SECTION retrieves all the Google API variables and UI reference
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //googleApiClient = new GoogleApiClient.Builder(Login.this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


/*        findViewById(R.id.sign_in_button).setOnClickListener(this);

        SignInButton thing = (SignInButton) findViewById(R.id.sign_in_button);

        TextView textView = (TextView) thing.getChildAt(0);
        textView.setText("Log in using Gmail");
        textView.setTextSize(14);*/
        //END SECTION


        //onclick listener when the done button is pressed on keyboard
        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    //make sure all fields are not empty
                    if (emailField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                    } else {
                        //begin the app authentication
                        startSignin();

                        esend = emailField.getText().toString();


                        pb.setVisibility(View.VISIBLE);
                        getallbooks();
                        getchecked();
                        namesend = Login.getFullName();
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
                if (emailField.getText().toString().trim().isEmpty() || passwordField.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();
                } else {
                    //begin authentication
                    startSignin();

                    esend = emailField.getText().toString();


                    pb.setVisibility(View.VISIBLE);
                    getallbooks();
                    getchecked();
                    namesend = Login.getFullName();
                }
            }
        });

        //implement that abstract method necessary for Firebase Authentication
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    //when onstart method is implemented, setup the mAuth by adding the listener
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //firebase authentication method
    public void startSignin() {
        //remove all the spaces in the username and retrieve edittest values
        final String email = emailField.getText().toString().replace(" ", "");
        String password = passwordField.getText().toString();

        //make sure not empty
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "FIELDS ARE EMPTY FIREBASE", Toast.LENGTH_SHORT).show();
        } else {
            //begin authenticating
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if not successful
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "There was an issue, unable to Login", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                    //successful
                    if (task.isSuccessful()) {

                        //get the user that is logged in and userid
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userid = user.getUid();

                        //get the reference to the database through url
                        mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Users/" + userid);

                        //this event listener will retrieve the user information
                        //name, email, username, password, etc.
                        mReffname.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //store the information in Map
                                Map<String, String> map = dataSnapshot.getValue(Map.class);

                                //getvalue method to retrieve necessary fields
                                fn = map.get("fname");
                                ln = map.get("lname");
                                em = map.get("email");
                                un = map.get("username");
                                pd = map.get("password");
                                gr = map.get("grade");


                                //use set methods
                                Login.setFullName(fn + " " + ln);
                                namesend = fn + " " + ln;
                                Login.setUsername(un);
                                Login.setEmail(em);
                                Login.setGrade(gr);
                                Login.setPassword(pd);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        //begin the navigationview activity
                        Intent activities = new Intent(getApplicationContext(), Activities.class);
                        startActivity(activities);
                        finish();
                        pb.setVisibility(View.GONE);
                    }
                }
            });
        }

    }

    //method to get allbooks
    public void getallbooks() {
        //retrieve reference
        Firebase getbooksref = new Firebase("https://libeary-8d044.firebaseio.com/Books/");

        getbooksref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //collect all the books titles, authors, pagecounts, etc. and save in the arraylists
                ts = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "title");
                aus = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "author");
                cas = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "category");
                ps = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "pagecount");
                sus = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "summary");
                sss = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "status");
                ibss = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "isbn");

                //convert the arraylist to array and use setmethod
                setTils(ts.toArray(new String[ts.size()]));
                setAuths(aus.toArray(new String[aus.size()]));
                setCs(cas.toArray(new String[cas.size()]));
                setPgs(ps.toArray(new String[ps.size()]));
                setSs(sus.toArray(new String[sus.size()]));
                setStatuss(sss.toArray(new String[sss.size()]));
                setIss(ibss.toArray(new String[ibss.size()]));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getchecked() {
        //  databasereference.child(CatalogFragment.titleofthebook).setValue();
        Firebase getbooksref = new Firebase("https://libeary-8d044.firebaseio.com/Books/");

        getbooksref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //collect all the books titles, authors, pagecounts, etc. and save in the arraylists
                ddal = lolnew((Map<String, Object>) dataSnapshot.getValue(), "duedate", un);
                ccal = lolnew((Map<String, Object>) dataSnapshot.getValue(), "title", un);

                Log.d("SATHVIK", ddal.toString() + " " + ccal.toString());

                setCtits(ccal.toArray(new String[ccal.size()]));
                setCds(ddal.toArray(new String[ddal.size()]));

                for (int i = 0; i < ctits.length; i++) {
                    Log.d("SATHVIK", getCtits()[i] + " " + getCds()[i] + " ");
                }
                //convert the arraylist to array and use setmethod

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private ArrayList<String> lolnew(Map<String, Object> users, String fieldName, String usernamecheck) {
        ArrayList<String> information = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            Log.d("HELLO", usernamecheck + "");
            //Get phone field and append to list
            //  information.add((String) singleUser.get("checkedoutto"));
            String thing = (String) singleUser.get("checkedoutto");
            if (thing.equals(usernamecheck)) {
                information.add((String) singleUser.get(fieldName));
                Log.d("HELLOGOHOME", "SOMETHING " + singleUser.get(fieldName).toString());
            }
        }

        return information;
    }

    //get all the information for a necessary parameter
    private ArrayList<String> collectBookData(Map<String, Object> users, String fieldName) {
        ArrayList<String> information = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            information.add((String) singleUser.get(fieldName));
        }

        return information;
    }


    //signup button action onclick
    public void action(View view) {
        //start the signup intent
        Intent dashboard = new Intent(this, SignUp.class);
        startActivity(dashboard);
    }

    //updates the ui for googleLogin
   /* private void updateUI(boolean isLogin) {
        if (isLogin) {
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.GONE);
            //if logged in correctly, start the navigationview
            Intent activities = new Intent(getApplicationContext(), Activities.class);
            startActivity(activities);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Logged fail", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.sign_in_button:
                //when the googlesigninbutton is pressed, call this method
                signIn();
                break;*/
        }
    }
/*
    //use googleAPI to authenticate
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        pb.setVisibility(View.VISIBLE);
        startActivityForResult(signInIntent, REQ_CODE);
    }*/

    //implement abstract methods
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
         /*   GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);*/
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            pb.setVisibility(View.VISIBLE);
        }
    }

    //when logged in, retrieve the user information
    /*private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            setFullName(account.getDisplayName());
            setEmail(account.getEmail());
            String username = account.getId();
            setUsername(username);
            // String img_url = account.getPhotoUrl().toString();
            updateUI(true);
        } else {
            updateUI(false);
        }
    }*/
}