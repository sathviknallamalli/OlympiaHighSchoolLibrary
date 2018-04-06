package com.example.sathv.olympiahighschoollibrary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;


public class EmailReccommend extends AppCompatActivity {
    EditText input;
    String entered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_reccommend);

        final AlertDialog.Builder builder = new AlertDialog.Builder(EmailReccommend.this);
        builder.setTitle("Please enter the email you would like to recommend this book to");

        input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        //set ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                entered = input.getText().toString();

                if( Patterns.EMAIL_ADDRESS.matcher(entered).matches()){
                    String subject = entered + " has shared a book with you!";
                    String message = entered + " from Olympia High School has sent a book to you. Check out what the book is! \n" + "\nBook Title: " +
                            BookAdapter.t + "\nAuthor: " + BookAdapter.au + "\nPagecount: " + BookAdapter.pg + "\nGenre: " + BookAdapter.c + "\nSummary: " + BookAdapter.s + "\n" + "\nPlease do not reply to this email because it is an unchecked inbox";

                    SendMailShare sm = new SendMailShare(EmailReccommend.this, entered, subject, message, "Your reccommendation has been sent");
                    sm.execute();

                }else{
                    Toast.makeText(EmailReccommend.this, "Email is invalid", Toast.LENGTH_SHORT).show();
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

