package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookInformation extends AppCompatActivity {

    TextView status;
    EditText input;
    ArrayList checkedoutbooks = new ArrayList();
    static int checkedoutcount = 0;
    static int reservedcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);
        setTitle(CatalogFragment.titleofthebook);

        TextView title = (TextView) findViewById(R.id.infoTitle);
        TextView author = (TextView) findViewById(R.id.infoAuthor);
        TextView category = (TextView) findViewById(R.id.infoCategory);
        TextView isbn = (TextView) findViewById(R.id.isbn);
        TextView pg = (TextView) findViewById(R.id.infopg);
        TextView summary = (TextView) findViewById(R.id.summary);
        status = (TextView) findViewById(R.id.status);

        ImageView bookCover = (ImageView) findViewById(R.id.bigimageofbook);

        Button checkOut = (Button) findViewById(R.id.checkOut);
        Button reserve = (Button) findViewById(R.id.reserve);


        title.setText(CatalogFragment.titleofthebook);
        author.setText(CatalogFragment.authorofthebook);
        category.setText(category.getText().toString() + " " + CatalogFragment.category);
        isbn.setText("ISBN: " + CatalogFragment.isbn);
        pg.setText("Pagecount: " + CatalogFragment.pg + "");
        summary.setText(CatalogFragment.summary);
        status.setText(status.getText().toString() + CatalogFragment.getStatus());

        bookCover.setImageResource(CatalogFragment.id);

    }

    public void checkoutOnClick(View view) {
        if (status.getText().toString().contains("Available")) {

            final Context context = view.getContext();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Please enter your password to proceed");

            input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String entered = input.getText().toString();

                    Log.d("BAD", "password in bookinfo confirmation" + Login.getPassword());

                    if (entered.equals(Login.getPassword())) {

                        checkedoutcount++;

                        checkedoutbooks.add(CatalogFragment.titleofthebook);

                        Log.d("BAD", checkedoutbooks.toString());

                        Intent activities = new Intent(context, CheckedOutConfirmation.class);
                        startActivity(activities);
                        finish();

                    } else {
                        Toast.makeText(context, "password is incorrect", Toast.LENGTH_LONG).show();
                        input.setText("");
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        } else {
            Toast.makeText(getApplicationContext(), "This book is currently unavailable for check out now. You can reserve this book however", Toast.LENGTH_SHORT).show();
        }
    }

    public void reserveOnClick(View view) {
        if (status.getText().toString().contains("Unavailable")) {
            final Context context = view.getContext();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Please enter your password to proceed");

            input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String entered = input.getText().toString();

                    Log.d("BAD", "password in bookinfo confirmation for reserve" + Login.getPassword());

                    if (entered.equals(Login.getPassword())) {

                        reservedcount++;

                        Intent activities = new Intent(context, ReservedConfirmation.class);
                        startActivity(activities);
                        finish();

                    } else {
                        Toast.makeText(context, "password is incorrect", Toast.LENGTH_LONG).show();
                        input.setText("");
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }
}
