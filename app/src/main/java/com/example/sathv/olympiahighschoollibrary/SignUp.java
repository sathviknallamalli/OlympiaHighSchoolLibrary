package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUp extends Activity {

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText email;
    Spinner gradeOptions;
    Button register;
    EditText confirm;

    EditText change;

    ProgressDialog mDialog;
    FirebaseAuth mAuth;

    String name;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.username);
        firstName = (EditText) findViewById(R.id.firstName);
        firstName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        lastName = (EditText) findViewById(R.id.lastName);
        lastName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        confirm = (EditText) findViewById(R.id.confirmPassword);

        gradeOptions = (Spinner) findViewById(R.id.gradeOptions);
        register = (Button) findViewById(R.id.signUp);

        mDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        //selecting the grade on spinner
        adapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeOptions.setAdapter(adapter);
        gradeOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                //parent.getItemAtPosition(position)
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        gradeOptions.setSelection(0);


    }

    public void registerAction(View view) {
        //check is all fields are entered correctly
        if (username.getText().toString().trim().isEmpty() || firstName.getText().toString().trim().isEmpty() || lastName.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty() ||
                gradeOptions.getSelectedItem().toString().equals("Please select your grade")) {
            Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();

        } else {
            //check if passwords are identical
            if (!password.getText().toString().equals(confirm.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Passwords are not identical", Toast.LENGTH_SHORT).show();
            }
            //passwords are identical
            else {
                //ACCOUNT CREATED!!
                registerToDatabase();
            }
        }

    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Check email for verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }

    }

    public void registerToDatabase() {
        String url = "https://sathviknallamalli.000webhostapp.com/registerwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mDialog.setMessage("Signing up...");
                mDialog.show();
                if (response.trim().equals("success")) {
                    //Toast.makeText(getApplicationContext(), "Your account has been created!", Toast.LENGTH_SHORT).show();

                    Login l = new Login();

                    l.setFullName(firstName.getText().toString() + " " + lastName.getText().toString());
                    Log.d("BAD", "after setting in SIGN UP " + l.getFullName());
                    l.setEmail(email.getText().toString());
                    Log.d("BAD", "email is after register" + l.getEmail());
                    mDialog.dismiss();

                    String emailRaw = l.getEmail();
                    Log.d("BAD", "email" + emailRaw);
                    String code = generateRandomString();

                    String subject = "Confirm your email address for Olympia High School Library";
                    String message = "Thank you for signing up for Olympia High School. Please enter this verification code in the app " + code;

                    SendMail sm = new SendMail(SignUp.this, emailRaw, subject, message, code);

                    sm.execute();

                } else {
                    Toast.makeText(getApplicationContext(), "Oops something went wrong" + response, Toast.LENGTH_SHORT).show();
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

                String grade = (String) gradeOptions.getSelectedItem();

                params.put("username", username.getText().toString().trim().replace(" ", ""));
                params.put("password", password.getText().toString().trim());
                params.put("firstname", firstName.getText().toString().trim().replace(" ", ""));
                params.put("lastname", lastName.getText().toString().trim().replace(" ", ""));
                params.put("email", email.getText().toString().trim());
                params.put("grade", grade);

                return params;

            }
        };

        requestQueue.add(stringRequest);

    }

    public void sendEmail() {
        Login l = new Login();

        String emailRaw = l.getEmail();
        Log.d("BAD", "email" + emailRaw);
        String code = generateRandomString();

        String subject = "Confirm your email address for Olympia High School Library";
        String message = "from android studio";

        SendMail sm = new SendMail(this, emailRaw, subject, message, code);

        sm.execute();
    }

    public String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomString = new StringBuilder();
        Random rnd = new Random();
        while (randomString.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * characters.length());
            randomString.append(characters.charAt(index));
        }
        String saltStr = randomString.toString();
        return saltStr;

    }
}
