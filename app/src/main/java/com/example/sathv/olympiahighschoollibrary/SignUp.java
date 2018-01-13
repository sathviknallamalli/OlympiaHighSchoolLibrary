package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
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

    ProgressDialog mDialog;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //retrieve each field
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

    //register button onClick
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
                //begin the process of adding to database
                registerToDatabase();
            }
        }

    }

    //method to update and insert into database
    public void registerToDatabase() {
        String url = "https://sathviknallamalli.000webhostapp.com/registerwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //start and initialize the dialog with message
                mDialog.setMessage("Signing up...");
                mDialog.show();
                if (response.trim().equals("success")) {
                    //if the php script returns "success" token then let user know and send email
                    Login l = new Login();

                    //set appropriate full namel email and username and password
                    l.setFullName(firstName.getText().toString() + " " + lastName.getText().toString());
                    l.setEmail(email.getText().toString());
                    mDialog.dismiss();

                    String emailRaw = l.getEmail();
                    String code = generateRandomString();

                    //set subject and message for the email beign sent
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

                //send the appropriate hashmap vairables as parameters into the php script
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

    //generate random code for user to enter
    public String generateRandomString() {
        //possible characters that are avilable
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomString = new StringBuilder();
        Random rnd = new Random();
        //use while loop to select random index
        while (randomString.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * characters.length());
            randomString.append(characters.charAt(index));
        }
        String saltStr = randomString.toString();
        //concatentate 6 times and return string
        return saltStr;
    }
}
