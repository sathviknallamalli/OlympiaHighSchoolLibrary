package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.content.Intent;
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

public class SignUp extends Activity {

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText email;
    Spinner gradeOptions;
    Button register;
    EditText confirm;

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
        if (username.getText().toString().trim().isEmpty() || firstName.getText().toString().trim().isEmpty() || lastName.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty() || (String) gradeOptions.getSelectedItem() == "Please select your grade") {
            Toast.makeText(getApplicationContext(), "Missing field(s)", Toast.LENGTH_SHORT).show();

        } else {
            //check if passwords are identical
            if(!password.getText().toString().equals(confirm.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Passwords are not identical", Toast.LENGTH_SHORT).show();
            }
            //passwords are identical
            else{
                registerToDatabase();
            }

        }

    }

    public void registerToDatabase() {
        String url = "https://sathviknallamalli.000webhostapp.com/registerwebhost.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Your account has been created!", Toast.LENGTH_SHORT).show();

                    Intent activities = new Intent(getApplicationContext(), Activities.class);
                    startActivity(activities);
                    finish();
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
                params.put("firstname", firstName.getText().toString().trim().replace(" ",""));
                params.put("lastname", lastName.getText().toString().trim().replace(" ", ""));
                params.put("email", email.getText().toString().trim());
                params.put("grade", grade);

                return params;

            }
        };

        requestQueue.add(stringRequest);

    }
}
