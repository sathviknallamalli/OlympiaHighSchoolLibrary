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
import java.util.Calendar;
import java.util.Date;

public class BookInformation extends AppCompatActivity {

    TextView status;
    EditText input;

    static int checkedoutcount = 0;
    static int reservedcount = 0;

    static ArrayList checkedoutbookstitles = new ArrayList();
    static ArrayList checkedoutbooksdates = new ArrayList();
    static ArrayList checkedoutbooksimages = new ArrayList();
    static ArrayList<Date> reminderdates = new ArrayList<Date>();

    static ArrayList reservedbooktitles = new ArrayList();
    static ArrayList reservedbookauthor = new ArrayList();
    static ArrayList reservedbookimages = new ArrayList();


    public static String getDatetoputinconfirmation() {
        return datetoputinconfirmation;
    }

    public static void setDatetoputinconfirmation(String datetoputinconfirmation) {
        BookInformation.datetoputinconfirmation = datetoputinconfirmation;
    }

    static String datetoputinconfirmation;

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

        if (CatalogFragment.getStatus().equals("Available")) {
            status.setTextColor(getResources().getColor(R.color.forestgreeen));
        } else if (CatalogFragment.getStatus().equals("Unavailable")) {
            status.setTextColor(getResources().getColor(R.color.crimson));
        }

    }

    public void checkoutOnClick(View view) {
        if (status.getText().toString().contains("Available")) {

            final Context context = view.getContext();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Please enter your password to proceed");

            input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String entered = input.getText().toString();

                    Log.d("BAD", "password in bookinfo confirmation" + Login.getPassword());

                    if (entered.equals(Login.getPassword())) {

                        checkedoutcount++;


                        checkedoutbookstitles.add(CatalogFragment.titleofthebook);

                        Log.d("BAD", "titles array " + checkedoutbookstitles.toString());

                        int noOfDays = 14; //i.e two weeks
                        int daysbeforedue= 2;

                        //current date
                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = new Date();
                        calendar.setTime(currentDate);

                        //due date set
                        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                        Date duedate = calendar.getTime();

                        //reminder date
                        calendar.add(Calendar.DAY_OF_YEAR, daysbeforedue);
                        Date reminderdate = calendar.getTime();

                        reminderdates.add(reminderdate);
                        setDatetoputinconfirmation(duedate.toString());

                        checkedoutbooksdates.add(getDatetoputinconfirmation());

                        Log.d("BAD", "dates array " + checkedoutbookstitles.toString());

                        checkedoutbooksimages.add(CatalogFragment.id);

                        Log.d("BAD", "images array " + checkedoutbooksimages.toString());

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
            Toast.makeText(getApplicationContext(), "This book is currently unavailable for check out now. You can reserve this book however", Toast.LENGTH_LONG).show();
        }
    }

    public void reserveOnClick(View view) {
        if (status.getText().toString().contains("Unavailable")) {
            final Context context = view.getContext();
            if (reservedbooktitles.contains(CatalogFragment.titleofthebook) || reservedbookimages.contains(CatalogFragment.id) || reservedbookauthor.contains(CatalogFragment.authorofthebook)) {
                Toast.makeText(context, "Book is already reserved", Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please enter your password to proceed");

                input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String entered = input.getText().toString();

                        Log.d("BAD", "password in bookinfo confirmation for reserve" + Login.getPassword());

                        if (entered.equals(Login.getPassword())) {

                            reservedcount++;

                            reservedbooktitles.add(CatalogFragment.titleofthebook);
                            reservedbookauthor.add(CatalogFragment.authorofthebook);
                            reservedbookimages.add(CatalogFragment.id);

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


        } else {
            Toast.makeText(getApplicationContext(), "Please check out the book instead", Toast.LENGTH_SHORT).show();

        }
    }
}
