package com.example.sathv.olympiahighschoollibrary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;


public class EmailReccommend extends AppCompatActivity {
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_reccommend);

        final AlertDialog.Builder builder = new AlertDialog.Builder(EmailReccommend.this);
        builder.setTitle("Please enter the email you would like to share this book to");

        input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        //set ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String entered = input.getText().toString();

                //enter the current password first
                if (entered.contains("@gmail.com") || entered.contains("@yahoo.com") || entered.contains("@outlook.com")
                        || entered.contains("@live.com") || entered.contains("@") || entered.contains("@hotmail.com")) {

                    String emailRaw = entered;

                    Login l = new Login();
                    String[] firstandlast = l.getFullName().split(" ");

                    String first = firstandlast[0];
                    String last = firstandlast[1];

                    //set subject and message for the email beign sent
                    String subject = "You have recieved a recommendation from " + first + " " + last + "!";
                    String message = first + " from Olympia High School has sent a book to you. Check out what the book is! \n" + "\nBook Title: " +
                            BookAdapter.t + "\nAuthor: " + BookAdapter.au + "\nPagecount: " + BookAdapter.pg + "\nGenre: " + BookAdapter.c + "\nSummary: " + BookAdapter.s + "\n" + "\nPlease do not reply to this email because it is an unchecked inbox";

                    SendMailShare sm = new SendMailShare(EmailReccommend.this, emailRaw, subject, message, "Your reccommendation has been sent");
                    sm.execute();

                }
                // if the entered current password is not same then set all fields to gone
                else {
                    input.setText("");

                    Toast.makeText(getApplicationContext(), "please enter a valid email", Toast.LENGTH_SHORT).show();
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

