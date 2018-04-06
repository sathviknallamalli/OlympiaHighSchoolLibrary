package com.example.sathv.olympiahighschoollibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends Activity {

    EditText username;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText email;
    Spinner gradeOptions;
    Button register;
    EditText confirm;
    String userid;

    FirebaseAuth mAuth;

    ProgressDialog mDialog;
    private Firebase mRootRef;


    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();


        mRootRef = new Firebase("https://libeary-8d044.firebaseio.com/Users");


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
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
            } else {
                //begin the process of adding to database
                String fname = firstName.getText().toString();
                String lname = lastName.getText().toString();
                String un = username.getText().toString();
                String pd = password.getText().toString();
                String grade = gradeOptions.getSelectedItem().toString();
                register(email.getText().toString(), pd, fname, lname, un, grade);
                Login l = new Login();
                l.getallbooks();


            }
        }

    }

    private void register(final String email, final String password, final String fname, final String lname, final String un, final String grade) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Log.d("TAG", "signed in");

                    FirebaseUser user = mAuth.getCurrentUser();
                    userid = user.getUid();

                    UserInformation userInformation = new UserInformation(fname, lname, un, password, email, grade);
                    mRootRef.child(userid).setValue(userInformation);

                    SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(getString(R.string.fullname), fname + " " + lname);
                    editor.putString(getString(R.string.fname), fname);
                    editor.putString(getString(R.string.lname), lname);
                    editor.putString(getString(R.string.email), email);
                    editor.putString(getString(R.string.grade), grade);
                    editor.putString(getString(R.string.username), un);
                    editor.putString(getString(R.string.password), password);
                    editor.apply();


                    Login.setPassword(password);
                    Login.setFullName(fname + " " + lname);
                    Login.setUsername(un);
                    Login.setEmail(email);
                    Login.setGrade(grade);

                    String subject =  "Welcome to the Olympia High School Library";
                    String message = "This email is confirmation that you have successfully signed up and created an account for Olympia High School Library." +
                            " This email will be used to contact you and reserve or check out books under. Below is your user information" +
                            "\nFirst name: " + fname + "\nLast name: " + lname + "\nUsername: " + un + "\nPassword " + password + "\nGrade " + grade;

                    SendMailShare sm = new SendMailShare(SignUp.this, email, subject, message, "A confirmation email has been sent");
                    sm.execute();

                    Intent activities = new Intent(getApplicationContext(), Activities.class);
                    startActivity(activities);
                    finish();

                    Toast.makeText(getApplicationContext(), "Welcome to LiBEARy", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
    }

}
