package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends Activity {

    // UI references.
    private EditText mUsername;
    private EditText mPasswordView;
    private View mLoginFormView;

    String ip, database, un, pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Connection con = connectionclass(un, pd, database, ip);
                    Toast.makeText(Login.this, "FAIL", Toast.LENGTH_LONG).show();

                    try {
                        String query = "select * from Login where Username= '" + mUsername.toString() + "' and Password = '" + mPasswordView.toString() + "'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                            con.close();
                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Login.this, "Login Invalid", Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                Connection con = connectionclass(un, pd, database, ip);

                if(con==null) {
                    Toast.makeText(Login.this, "FAIL login", Toast.LENGTH_LONG).show();
                }

                try {
                    String query = "select * from Login where Username= '" + mUsername.toString() + "' and Password = '" + mPasswordView.toString() + "'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                        con.close();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Login.this, "Login Invalid", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }





    public Connection connectionclass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = "";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + database + ";user=" + un + ";password="
                    + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);

        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}






