package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

/**
 * This class is the backend for the login to screen to allow login through facebook, google, and the app account
 * It will also retrieve all the books information from the Firebase database that has connected this android project
 */
public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;


    // UI references.
    private EditText emailField;
    private EditText passwordField;
    TextView fp;
    String fn, ln, un, pd, em, gr , res;
    Button buttons;
    ProgressBar pb;

    ImageView loginicon;

    EditText passwordcustom;
    EditText usernamecustom;
    EditText gradecustom;
    EditText confirmcustom;

    EditText input;
    String entered;

    Button facebookbtn;


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

    static String[] resses;

    public static String[] getResses() {
        return resses;
    }

    public static void setResses(String[] resses) {
        Login.resses = resses;
    }

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

    static String[] ctits;

    public static String[] getCtits() {
        return ctits;
    }

    //END SECTION


    //Facebook UIS
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
    ArrayList<String> ress = new ArrayList<>();

    static String esend, namesend;

    Firebase mRootRef;

    private TwitterLoginButton mLoginButton;

    final String CONSUMER_KEY = "WmCgyauCS5UuLCPosNQpc2Tag";
    final String CONSUMER_SECRET = "VEReEAhJSuBLzf8wW4OrICYLoYkN1hbgfASz4ICyX3munFmwP9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TwitterAuthConfig authConfig = new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET);
        Fabric.with(Login.this, new Twitter(authConfig));

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        facebookbtn = (Button) findViewById(R.id.facebookbutton);

        //retrieve login form edittexts
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        fp = findViewById(R.id.fp);
        loginicon = (ImageView) findViewById(R.id.loginicon);
        //signup to make a new account from LiBEARy
        buttons = (Button) findViewById(R.id.signUp);

        //initialize firebase authorization variable
        mAuth = FirebaseAuth.getInstance();
        mRootRef = new Firebase("https://libeary-8d044.firebaseio.com/Users");
        //this auth will allow access to the firebase console and authenticate the user
        callbackManager = CallbackManager.Factory.create();

        facebookbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        handleFacebookAccessToken(loginResult.getAccessToken());


                        Toast.makeText(getApplicationContext(), "Log success"
                                , Toast.LENGTH_SHORT).show();


                        pb.setVisibility(View.GONE);

                        String name = loginResult.getAccessToken().getUserId();
                        String email = loginResult.getAccessToken().getToken();

                        getallbooks();
                        // getchecked();

                        //String[] splited = name.split("\\s+");

                        FirebaseUser signedinuser = mAuth.getCurrentUser();

                        setFullName(name);
                        setEmail(email);


                       /* SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(getString(R.string.fullname), name);
                        editor.putString(getString(R.string.fname), splited[0]);
                        editor.putString(getString(R.string.lname), "no last name");
                        editor.putString(getString(R.string.email), email);
                        editor.putString(getString(R.string.grade), "Unknown");
                        editor.putString(getString(R.string.username), "Not Applicable for Facebook login");
                        editor.putString(getString(R.string.password), "Cannot retrieve");
                        editor.apply();*/


                        esend = emailField.getText().toString();
                        namesend = Login.getFullName();



                       /* UserInformation userInformation = new UserInformation("no first name", "no last name",
                                "Facebook", "No username specified"
                                , "No password specified", user.getEmail(), "Unknown");
                        mRootRef.child(user.getUid()).setValue(userInformation);*/


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
                //END SECTION);
            }
        });

        //GOOGLE SIGN IN
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Log in with Google");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        mAuth.signOut();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    for (UserInfo userdos : FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {

                        if (userdos.getProviderId().equals("facebook.com")) {
                            //For linked facebook account
                            Log.d("xx_xx_provider_info", "User is signed in with Facebook");
                            updateUI(user, "Facebook");



                        } else if (userdos.getProviderId().equals("google.com")) {
                            //For linked Google account
                            Log.d("xx_xx_provider_info", "User is signed in with Google");
                            updateUI(user, "Gmail");


                        }else if (userdos.getProviderId().equals("twitter.com")) {
                            //For linked Google account
                            Log.d("xx_xx_provider_info", "User is signed in with Twitter");
                            updateUI(user, "Twitter");


                        }
                    }

                } else {
                }


            }
        };

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
                        // getchecked();
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
                    //   getchecked();
                    namesend = Login.getFullName();
                }
            }
        });

        fp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Please enter your email");

                input = new EditText(getApplicationContext());
                input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                input.setTextColor(getResources().getColor(R.color.tw__composer_white));
                builder.setView(input);

                //set ok button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        entered = input.getText().toString();

                        if (Patterns.EMAIL_ADDRESS.matcher(entered).matches()) {
                            FirebaseAuth.getInstance().sendPasswordResetEmail(entered)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Login.this, "Check for a reset email", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(Login.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                //alert dialog negative cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();


            }
        });


        mLoginButton = findViewById(R.id.twitter);

        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d("TAG", "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.w("TAG", "twitterLogin:failure", exception);
                updateUI(null, "Twitter");
            }
        });


    }



    //when onstart method is implemented, setup the mAuth by adding the listener
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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


                                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(getString(R.string.fullname), fn + " " + ln);
                                editor.putString(getString(R.string.fname), fn);
                                editor.putString(getString(R.string.lname), ln);
                                editor.putString(getString(R.string.email), em);
                                editor.putString(getString(R.string.grade), gr);
                                editor.putString(getString(R.string.username), un);
                                editor.putString(getString(R.string.password), pd);
                                editor.apply();


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
                ress = collectBookData((Map<String, Object>) dataSnapshot.getValue(), "reservations");


                //convert the arraylist to array and use setmethod
                setTils(ts.toArray(new String[ts.size()]));
                setAuths(aus.toArray(new String[aus.size()]));
                setCs(cas.toArray(new String[cas.size()]));
                setPgs(ps.toArray(new String[ps.size()]));
                setSs(sus.toArray(new String[sus.size()]));
                setStatuss(sss.toArray(new String[sss.size()]));
                setIss(ibss.toArray(new String[ibss.size()]));
                setResses(ress.toArray(new String[ress.size()]));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
    private void updateUI(final FirebaseUser user, final String provider) {
        if (user != null) {
            //Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

            //if logged in correctly, start the navigationview
            getallbooks();

            final String thing = user.getDisplayName();
            final String split[] = thing.split("\\s+");

            final String uno = user.getEmail();

            String userid = user.getUid();

            mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Users/" + userid);

            //this event listener will retrieve the user information
            //name, email, username, password, etc.
            mReffname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //store the information in Map
                    Map<String, String> map = dataSnapshot.getValue(Map.class);

                    if (map == null) {
                        update(split[0], split[1], provider, uno, user);
                    } else {
                        un = map.get("username");
                        pd = map.get("password");
                        gr = map.get("grade");

                        if (pd.equals("No password specified") || un.equals("No username specified") || gr.equals("Unknown")) {
                            update(split[0], split[1], provider, uno, user);
                        } else {
                            SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(getString(R.string.fullname), thing);
                            editor.putString(getString(R.string.fname), split[0]);
                            editor.putString(getString(R.string.lname), split[1]);
                            editor.putString(getString(R.string.email), uno);
                            editor.putString(getString(R.string.grade), map.get("grade"));
                            editor.putString(getString(R.string.username), map.get("username"));
                            editor.putString(getString(R.string.password), map.get("password"));
                            editor.putString(getString(R.string.provider), provider);
                            editor.putString(getString(R.string.uuid), user.getUid());

                            editor.apply();

                            UserInformation userInformation = new UserInformation(split[0], split[1], provider + "",
                                    map.get("username")
                                    , map.get("password"), uno, map.get("grade"));
                            mRootRef.child(user.getUid()).setValue(userInformation);

                            pb.setVisibility(View.GONE);

                            Intent activities = new Intent(Login.this, Activities.class);
                            startActivity(activities);
                            finish();
                        }
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        } else {
            // Toast.makeText(getApplicationContext(), "Logged fail", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                updateUI(null, "Gmail");
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            pb.setVisibility(View.VISIBLE);

            mLoginButton.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(getString(R.string.fullname), user.getDisplayName());
                            editor.putString(getString(R.string.email), user.getEmail());

                            updateUI(user, "Facebook");


                            /*Intent activities = new Intent(getApplicationContext(), Activities.class);
                            startActivity(activities);
                            finish();*/


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d("TAG", "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, "Twitter");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    private void signin() {
        Intent signinintent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signinintent, RC_SIGN_IN);
    }

    public void signout(FirebaseAuth mAuth) {
        mAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null, "Gmail");
                    }
                }
        );
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Login.this, "Connection error",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signin();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
    }

    public void update(final String cfname, final String clname, final String cprovider, final String cemail,
                       final FirebaseUser user) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Please enter this information for completion of your account");

        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.customalert, null);
        builder.setView(v);

        passwordcustom = v.findViewById(R.id.passwordcustom);
        usernamecustom = v.findViewById(R.id.usernamecustom);
        gradecustom = v.findViewById(R.id.gradecustom);
        confirmcustom = v.findViewById(R.id.confirmcustom);

        Toast.makeText(getApplicationContext(), "Because you logged in with another provider, please enter this information " +
                "to complete your account creation", Toast.LENGTH_LONG).show();

        //set ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String passwordcust = passwordcustom.getText().toString();

                Pattern p = Pattern.compile("[^A-Za-z0-9]");
                Matcher m = p.matcher(passwordcust);

                Pattern numberp = Pattern.compile("([0-9])");
                Matcher numberm = numberp.matcher(passwordcust);

                if (!passwordcustom.getText().toString().equals(confirmcustom.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Passwords are not identical", Toast.LENGTH_SHORT).show();
                } else if (!m.find()) {
                    Toast.makeText(getApplicationContext(), "Password must contain a special character", Toast.LENGTH_SHORT).show();
                } else if (!numberm.find()) {
                    Toast.makeText(getApplicationContext(), "Password must contain a number", Toast.LENGTH_SHORT).show();
                } else {
                    UserInformation userInformation = new UserInformation(cfname, clname, cprovider,
                            usernamecustom.getText().toString()
                            , passwordcustom.getText().toString(), cemail, gradecustom.getText().toString());
                    mRootRef.child(user.getUid()).setValue(userInformation);

                    SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(getString(R.string.fullname), cfname + " " + clname);
                    editor.putString(getString(R.string.fname), cfname);
                    editor.putString(getString(R.string.lname), clname);
                    editor.putString(getString(R.string.email), cemail);
                    editor.putString(getString(R.string.grade), gradecustom.getText().toString());
                    editor.putString(getString(R.string.username), usernamecustom.getText().toString());
                    editor.putString(getString(R.string.password), passwordcustom.getText().toString());
                    editor.putString(getString(R.string.provider), cprovider);
                    editor.putString(getString(R.string.uuid), user.getUid());
                    editor.apply();

                    pb.setVisibility(View.GONE);

                    Intent activities = new Intent(Login.this, Activities.class);
                    startActivity(activities);
                    finish();
                    dialog.cancel();
                }

            }
        });

        //alert dialog negative cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


}