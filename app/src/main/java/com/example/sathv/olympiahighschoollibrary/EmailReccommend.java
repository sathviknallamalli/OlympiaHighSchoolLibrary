package com.example.sathv.olympiahighschoollibrary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.EditText;
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


public class EmailReccommend extends AppCompatActivity {
    EditText input;
    String entered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_reccommend);

        final AlertDialog.Builder builder = new AlertDialog.Builder(EmailReccommend.this);
        builder.setTitle("Please enter the username you would like to recommend this book to");

        input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        //set ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                entered = input.getText().toString();

                checkrecieveusername(entered);

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

    private void checkrecieveusername(String recieve) {

        String url = "https://sathviknallamalli.000webhostapp.com/checkusername.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("incorrect")) {
                    String email = response;

                    String subject = entered + " has shared a book with you!";
                    String message = entered + " from Olympia High School has sent a book to you. Check out what the book is! \n" + "\nBook Title: " +
                            BookAdapter.t + "\nAuthor: " + BookAdapter.au + "\nPagecount: " + BookAdapter.pg + "\nGenre: " + BookAdapter.c + "\nSummary: " + BookAdapter.s + "\n" + "\nPlease do not reply to this email because it is an unchecked inbox";

                    SendMailShare sm = new SendMailShare(EmailReccommend.this, email, subject, message, "Your reccommendation has been sent");
                    sm.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "The username you entered does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if the php script returns an error, set visibility to gone and print
                Toast.makeText(getApplicationContext(), "ERROR " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //put necessary parameters for the php script using the hashmap
                params.put("checkusername", entered);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}

