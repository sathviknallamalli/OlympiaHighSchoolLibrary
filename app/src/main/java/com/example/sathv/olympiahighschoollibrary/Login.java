package com.example.sathv.olympiahighschoollibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends Activity {

    // UI references.
    private EditText mUsername;
    private EditText mPasswordView;
    private View mLoginFormView;

   private Button buttons;

    String ip, database, un, pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        buttons = (Button) findViewById(R.id.signUp);

        /*buttons.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this,
                        SignUp.class);
                startActivity(myIntent);
            }
        });*/


        mPasswordView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {

                   /* Connection con = createConnection(un, pd, database, ip);

                    if (con == null) {
                        Toast.makeText(Login.this, "FAIL login", Toast.LENGTH_LONG).show();
                    }

                    try {
                        String query = "select * from UserInfo where Username=? and Password=?";
                        PreparedStatement pst = con.prepareStatement(query);
                        pst.setString(1, mUsername.getText().toString());
                        pst.setString(2, mPasswordView.getText().toString());
                        ResultSet rs = pst.executeQuery();
                        int count = 0;

                        while (rs.next()) {
                            if (count == 1) {
                                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                                con.close();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Login.this, "Login Invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    Intent intent = new Intent(getApplicationContext(), Activities.class);
                    startActivity(intent);

                    return true;

                }
                return false;
            }
        });

        ip = "127.0.0.1";
        database = "OlyHighLibrary";
        un = "sa";
        pd = "Saibaba";

        Button mEmailSignInButton = (Button) findViewById(R.id.logIn);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Connection con = createConnection(un, pd, database, ip);

                if (con == null) {
                    Toast.makeText(Login.this, "FAIL login", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Login.this, "GOOD login", Toast.LENGTH_LONG).show();
                }

                try {

                    String query = "select * from UserInfo where Username=? and Password=?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, mUsername.getText().toString());
                    pst.setString(2, mPasswordView.getText().toString());
                    ResultSet rs = pst.executeQuery();
                    int count = 0;

                    while (rs.next()) {
                        if (count == 1) {
                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                            con.close();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Login.this, "Login Invalid", Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                Intent intent = new Intent(getApplicationContext(), Activities.class);
                startActivity(intent);
            }
        });

    }

    public class DoLogin extends AsyncTask<String, String, String>{

       // String z = ""

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }


    @SuppressLint("NewApi")
    public Connection createConnection(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = "";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + "localhost" + ";"
                    + "databaseName=" + database + ";user=" + user + ";password="
                    + password + ";instance=SQLEXPRESS";
            connection = DriverManager.getConnection(ConnectionURL);
            Toast.makeText(Login.this, "Connection Success", Toast.LENGTH_LONG).show();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void action(View view ) {
        Intent dashboard = new Intent(this, SignUp.class);
        startActivity(dashboard);


    }


}






