package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText input;
    Button submit;
    EditText pd;
    EditText confirm;
    TextView pdtitle, cpdtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Context context = getApplicationContext();

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

        //build alert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setTitle("Please enter your current password to continue");

        input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        //set ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String entered = input.getText().toString();

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

                                Login.setPassword(confirm.getText().toString());
                                //UPDATE TO DATABASE
                                Toast.makeText(getApplicationContext(), "Saved. Restart the app for changes", Toast.LENGTH_SHORT).show();

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
}
