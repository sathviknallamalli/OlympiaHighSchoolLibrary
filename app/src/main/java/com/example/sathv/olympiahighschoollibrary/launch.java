package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class launch extends AppCompatActivity {
    Button send;
    EditText value;
    private Firebase mRootRef;
    private final String TAG = "TAG";
    int count = 0;
    private FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //   Firebase.setAndroidContext(this);
//        FirebaseUser user = mAuth.getCurrentUser();
        //       userID = user.getUid();




        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);



        mRootRef = new Firebase("https://libeary-8d044.firebaseio.com/Users");

        mAuth = FirebaseAuth.getInstance();

        send = (Button) findViewById(R.id.send);
        value = (EditText) findViewById(R.id.value);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");
                String fname = "Sathvik";
                String lname = "Nallamalli";
                String username = "sathviknallamalli";
                String password = "eaflsajkd;f";
                String email = "sathviknallamalli@gmail.com";
                String phoneNum = "3605259897";


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Log.d(TAG, "signed in");
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

                count++;
                //handle the exception if the EditText fields are null
                UserInformation userInformation = new UserInformation(fname, lname, "Email/Password", username, password, email, phoneNum);
                mRootRef.child("adsklfasdl;fwep" + count).setValue(userInformation);

            }
        });

    }
}
