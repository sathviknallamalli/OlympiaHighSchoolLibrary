package com.example.sathv.olympiahighschoollibrary;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookInformation extends AppCompatActivity {

    TextView status;
    // TextView title;
    TextView author;
    TextView category;
    TextView isbn;
    TextView pg;
    TextView summary;
    EditText input;
    ImageView bookCover;
    Button checkOut;
    Button reserve;
    Button ratebut;

    ProgressBar pbj;

    NavigationView navigationView = null;

    TextView rc, cc;

    static int checkedoutcount = 0;
    static int reservedcount = 0;

    //arraylist to store checked out books when clicked to load in the checked fragment
    static ArrayList checkedoutbookstitles = new ArrayList();
    static ArrayList checkedoutbooksdates = new ArrayList();
    static ArrayList checkedoutbooksimages = new ArrayList();

    //the dates when reminders need to be given based on checked out date
    static ArrayList<Date> reminderdates = new ArrayList<Date>();

    //arraylust to store reserved books when to load in the reserved fragment
    static ArrayList reservedbooktitles = new ArrayList();
    static ArrayList reservedbookauthor = new ArrayList();
    static ArrayList reservedbookimages = new ArrayList();

    //getters and setters for datestring
    static String datetoputinconfirmation;

    NotificationCompat.Builder notification;
    private static final int uniqueid = 45612;

    public static String getDatetoputinconfirmation() {
        return datetoputinconfirmation;
    }

    public static void setDatetoputinconfirmation(String datetoputinconfirmation) {
        BookInformation.datetoputinconfirmation = datetoputinconfirmation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);

        //based on the selected book in catalog, title is set for activity
        setTitle(CatalogFragment.titleofthebook);

        //  title = (TextView) findViewById(R.id.infoTitle);
        author = (TextView) findViewById(R.id.infoAuthor);
        category = (TextView) findViewById(R.id.infoCategory);
        isbn = (TextView) findViewById(R.id.isbn);
        pg = (TextView) findViewById(R.id.infopg);
        summary = (TextView) findViewById(R.id.summary);
        status = (TextView) findViewById(R.id.status);

        bookCover = (ImageView) findViewById(R.id.bigimageofbook);

        checkOut = (Button) findViewById(R.id.checkOut);
        reserve = (Button) findViewById(R.id.reserve);
        ratebut = (Button) findViewById(R.id.ratebut);

        pbj = (ProgressBar) findViewById(R.id.pbj);
        pbj.setVisibility(View.GONE);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

//        cc = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_checkedbooks));

//        rc = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_reservedbooks));

        //set the bookinfo fields with appropriate text based on selected book in catalog fragment
        //  title.setText(CatalogFragment.titleofthebook);
        author.setText(CatalogFragment.authorofthebook);
        category.setText(category.getText().toString() + " " + CatalogFragment.category);
        isbn.setText("ISBN: " + CatalogFragment.isbn);
        pg.setText("Pagecount: " + CatalogFragment.pg + "");
        summary.setText(CatalogFragment.summary);
        summary.setMovementMethod(new ScrollingMovementMethod());
        summary.setVerticalScrollBarEnabled(true);

        status.setText(status.getText().toString() + " " + CatalogFragment.getStatus());

        final RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);

        bookCover.setImageResource(CatalogFragment.id);

        //based on the status, set color text
        if (CatalogFragment.getStatus().equals("Available")) {
            status.setTextColor(getResources().getColor(R.color.forestgreeen));
        } else if (CatalogFragment.getStatus().equals("Unavailable")) {
            status.setTextColor(getResources().getColor(R.color.crimson));
        }

        ratebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Snackbar snackbar = Snackbar.make(view, "Your rating " + rb.getRating(), Snackbar.LENGTH_LONG);
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });

    }

    //onclick action for checking out in bookinfo
    public void checkoutOnClick(View view) {

        //only allow checkout when status is available
        if (status.getText().toString().contains("Available")) {

            final Context context = view.getContext();

            //set up an alertdialog so that uer enters password to checkout the book
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Please enter your password to proceed");

            input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String entered = input.getText().toString();

                    //make sure that password is correct
                    if (entered.equals(Login.getPassword())) {

                        //increase count so that in Profile fragment stats are shown
                        checkedoutcount++;

                        //add this to the checkedoutarraylists to display in checked fragment
                        checkedoutbookstitles.add(CatalogFragment.titleofthebook);

                        //set and determine the due date
                        int noOfDays = 14; //i.e two weeks
                        int daysbeforedue = 2;

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

                        //add
                        checkedoutbooksdates.add(getDatetoputinconfirmation());

                        //add
                        checkedoutbooksimages.add(CatalogFragment.id);

                        notification = new NotificationCompat.Builder(BookInformation.this);
                        notification.setAutoCancel(true);

                        notification.setSmallIcon(R.mipmap.ic_launcher);
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle("Book checked out");
                        notification.setContentText("You have successfully checked out the following book: " + CatalogFragment.titleofthebook);

                        Intent intent = new Intent(context, BookInformation.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(BookInformation.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pendingIntent);

                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(uniqueid, notification.build());

                        pbj.setVisibility(View.VISIBLE);
                        updatecheckout();

                        //initializeCountDrawerchecke();

                        //start checked out confirmation activity for user
                        Intent activities = new Intent(context, CheckedOutConfirmation.class);
                        startActivity(activities);
                        finish();

                    } else {
                        Toast.makeText(context, "password is incorrect", Toast.LENGTH_LONG).show();
                        input.setText("");
                    }

                }
            });
            //cancel button for alert dialog to dismiss
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

    private void updatecheckout() {
        String url = "https://sathviknallamalli.000webhostapp.com/updatecheckedoutstatus.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    //if the php scropt that is in the url return success

                    pbj.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if the php script returns an error, set visibility to gone and print
                Toast.makeText(getApplicationContext(), "ERROR " + error.toString(), Toast.LENGTH_SHORT).show();

                pbj.setVisibility(View.GONE);
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                //put necessary parameters for the php script using the hashmap
                params.put("booktitle", CatalogFragment.titleofthebook);

                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

    //reserve button onclick
    public void reserveOnClick(View view) {

        //only allow reservations to be made when book is unavailable
        if (status.getText().toString().contains("Unavailable")) {
            final Context context = view.getContext();

            //make sure book is not already reserved
            if (reservedbooktitles.contains(CatalogFragment.titleofthebook) || reservedbookimages.contains(CatalogFragment.id) || reservedbookauthor.contains(CatalogFragment.authorofthebook)) {
                Toast.makeText(context, "Book is already reserved", Toast.LENGTH_LONG).show();
            } else {

                //build alertdialog for user to enter password in order to reserve book
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please enter your password to proceed");

                input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String entered = input.getText().toString();

                        //make sure password is correct
                        if (entered.equals(Login.getPassword())) {

                            //increase reserved count for Profile fragment to recrod count
                            reservedcount++;

                            //add info to the reserved arraylists for reserved fragment
                            reservedbooktitles.add(CatalogFragment.titleofthebook);
                            reservedbookauthor.add(CatalogFragment.authorofthebook);
                            reservedbookimages.add(CatalogFragment.id);


                            notification = new NotificationCompat.Builder(BookInformation.this);
                            notification.setAutoCancel(true);

                            notification.setSmallIcon(R.mipmap.ic_launcher);
                            notification.setWhen(System.currentTimeMillis());
                            notification.setContentTitle("Book reserved");
                            notification.setContentText("You have successfully reserved the following book: " + CatalogFragment.titleofthebook);

                            Intent intent = new Intent(context, BookInformation.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(BookInformation.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pendingIntent);

                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(uniqueid, notification.build());

                            //initializeCountDrawerreserve();

                            //start the activity for confirm reservation
                            Intent activities = new Intent(context, ReservedConfirmation.class);
                            startActivity(activities);
                            finish();

                        } else {
                            Toast.makeText(context, "password is incorrect", Toast.LENGTH_LONG).show();
                            input.setText("");
                        }
                    }
                });
                //builder second button for negative
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        }
        //if book is available then dont reserve and instead check out the book
        else {
            Toast.makeText(getApplicationContext(), "Please check out the book instead", Toast.LENGTH_SHORT).show();

        }
    }

    private void initializeCountDrawerreserve() {
        //Gravity property aligns the text
        rc.setGravity(Gravity.CENTER_VERTICAL);
        rc.setTypeface(null, Typeface.BOLD);
        rc.setTextColor(getResources().getColor(R.color.colorAccent));
        rc.setText(BookInformation.reservedcount + "");


    }

    private void initializeCountDrawerchecke() {
        //Gravity property aligns the text
        cc.setGravity(Gravity.CENTER_VERTICAL);
        cc.setTypeface(null, Typeface.BOLD);
        cc.setTextColor(getResources().getColor(R.color.colorAccent));
        cc.setText(BookInformation.checkedoutcount + "");
    }
}


