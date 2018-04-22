package com.example.sathv.olympiahighschoollibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    EditText input;
    Button submit;
    EditText pd;
    EditText confirm;
    TextView pdtitle, cpdtitle;
    ProgressDialog mDialog;
    String entered;
    FirebaseAuth mAuth;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Context context = getApplicationContext();

        sharedPref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        submit = (Button) findViewById(R.id.submit);
        pd = (EditText) findViewById(R.id.pd);
        confirm = (EditText) findViewById(R.id.confirm);
        pdtitle = (TextView) findViewById(R.id.pdtitle);
        cpdtitle = (TextView) findViewById(R.id.cpdtitle);

        //set the visibility of all fields to gone so the alert dialog can build
        cpdtitle.setVisibility(View.GONE);
        pdtitle.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        pd.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);

        mDialog = new ProgressDialog(this);

        //build alert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setTitle("Please enter your current password to continue");

        input = new EditText(context);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        //set ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                entered = input.getText().toString();

                //enter the current password first
                if (entered.equals(Login.getPassword())) {
                    //if equal then set all fields to visible and let the user enter new password
                    cpdtitle.setVisibility(View.VISIBLE);
                    pdtitle.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    pd.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.VISIBLE);

                    //submit onclick listener
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //make sure that the passwords are identical
                            if (!confirm.getText().toString().equals(pd.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "passwords are not indentical", Toast.LENGTH_SHORT).show();
                            }
                            //then update the password change into database
                            else {


                                updatepd();

                                mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    user.updatePassword(confirm.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        }
                    });
                }
                // if the entered current password is not same then set all fields to gone
                else {
                    input.setText("");

                    cpdtitle.setVisibility(View.GONE);
                    pdtitle.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    pd.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "passwords incorrect", Toast.LENGTH_SHORT).show();
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

    private void updatepd() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userid = user.getUid();


        //save and update all the changes to Firebase
        Firebase ref = new Firebase("https://libeary-8d044.firebaseio.com/Users/" + userid);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.password), confirm.getText().toString());


        final UserInformation bookdets = new UserInformation(sharedPref.getString(getString(R.string.fname), "full name"),
                sharedPref.getString(getString(R.string.lname), "full name"),
                sharedPref.getString(getString(R.string.provider), "full name"),
                sharedPref.getString(getString(R.string.username), "full name"), sharedPref.getString(getString(R.string.password), "full name"),
                sharedPref.getString(getString(R.string.email), "full name"), sharedPref.getString(getString(R.string.grade), "full name"));
        ref.setValue(bookdets);

        Login.setPassword(confirm.getText().toString());


        //send an email regarding the changes to the account

    }

}
