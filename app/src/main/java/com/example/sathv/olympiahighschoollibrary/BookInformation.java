package com.example.sathv.olympiahighschoollibrary;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    ProgressBar pbj;

    NavigationView navigationView = null;

    Firebase mRootRef;

    String reservations, checkedoutto, datething;


    String reservationsforthisbook;
    static int checkedoutcount = 0;
    static int reservedcount = 0;


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

    CatalogFragment cf;

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

        cf = new CatalogFragment();

        setTitle(CatalogFragment.titleofthebook);

        mRootRef = new Firebase("https://libeary-8d044.firebaseio.com/Users");

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
        Button review = (Button) findViewById(R.id.writeareview);

        pbj = (ProgressBar) findViewById(R.id.pbj);
        pbj.setVisibility(View.GONE);

        navigationView = (NavigationView) findViewById(R.id.nav_view);


        //Book b = CatalogFragment.books.get(CatalogFragment.pos);

        author.setText(CatalogFragment.authorofthebook);
        category.setText(category.getText().toString() + " " + CatalogFragment.category);
        isbn.setText("ISBN: " + CatalogFragment.isbn);
        pg.setText("Pagecount: " + CatalogFragment.pg);
        summary.setText(CatalogFragment.summary);
        summary.setMovementMethod(new ScrollingMovementMethod());
        summary.setVerticalScrollBarEnabled(true);

        status.setText(status.getText().toString() + " " + cf.getStatus());

        bookCover.setImageResource(CatalogFragment.id);


        //based on the status, set color text

        if (cf.getStatus().equals("Available")) {
            status.setTextColor(getResources().getColor(R.color.forestgreeen));
        } else if (cf.getStatus().equals("Unavailable")) {
            status.setTextColor(getResources().getColor(R.color.crimson));
        }

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean alreadycheckout = ischeckedout(CatalogFragment.titleofthebook);

                SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                String email = sp.getString(getString(R.string.email), "em");

                if (CatalogFragment.reservedlist.contains(email)) {
                    Toast.makeText(BookInformation.this, "You have already reserved this book", Toast.LENGTH_LONG).show();
                } else if (alreadycheckout) {
                    Toast.makeText(BookInformation.this, "You have checked this book out", Toast.LENGTH_LONG).show();

                } else {
                    method();
                }
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
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String entered = input.getText().toString();
                    SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    String password = sp.getString(getString(R.string.password), "Unknown");

                    //make sure that password is correct
                    if (entered.equals(password)) {

                        //set and determine the due date
                        int noOfDays = 14; //i.e two weeks
                        int daysbeforedue = 2;

                        Calendar calendar = Calendar.getInstance();

                        //due date set
                        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                        Date duedate = calendar.getTime();

                        //reminder date
                        calendar.add(Calendar.DAY_OF_YEAR, daysbeforedue);
                        Date reminderdate = calendar.getTime();

                        reminderdates.add(reminderdate);
                        setDatetoputinconfirmation(duedate.toString());

                        notification = new NotificationCompat.Builder(BookInformation.this);
                        notification.setAutoCancel(true);

                        notification.setSmallIcon(R.mipmap.ic_launcher);
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle(CatalogFragment.titleofthebook + " checked out");
                        notification.setContentText("You have successfully checked out the following book: " + CatalogFragment.titleofthebook);

                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(uniqueid, notification.build());

                        pbj.setVisibility(View.VISIBLE);
                        CatalogFragment l = new CatalogFragment();
                        updatecheckout(CatalogFragment.titleofthebook, CatalogFragment.authorofthebook, CatalogFragment.category,
                                CatalogFragment.pg, CatalogFragment.summary, CatalogFragment.isbn);


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

    private void updatecheckout(String title, String author, String category, String pagecount, String summary,
                                String isbn) {

        Firebase mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + title);

        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String email = sp.getString(getString(R.string.email), "Unknown");
        String tempres = sp.getString(getString(R.string.reservations), "unknown");


        final FirebaseBook bookdets = new FirebaseBook(title, author,
                category, pagecount, summary,
                isbn, "1", getDatetoputinconfirmation(), email, tempres);

        mReffname.setValue(bookdets);

    }


    private void updatereserve(final String datething, final String checkedoutto) {

        final Firebase mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + CatalogFragment.titleofthebook);
        mReffname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //store the information in Map
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                reservationsforthisbook = map.get("reservations");
                if(reservationsforthisbook.isEmpty() || reservationsforthisbook == null){
                    reservationsforthisbook = "";
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        String email = sp.getString(getString(R.string.email), "em");
        String newres = reservationsforthisbook + email + ", ";
        if (CatalogFragment.status.equals("Available")) {
            CatalogFragment.status = "0";
        } else {
            CatalogFragment.status = "1";
        }

        final FirebaseBook bookdets = new FirebaseBook(CatalogFragment.titleofthebook, CatalogFragment.authorofthebook,
                CatalogFragment.category, CatalogFragment.pg, CatalogFragment.summary,
                CatalogFragment.isbn, CatalogFragment.status, datething, checkedoutto, newres);

        mReffname.setValue(bookdets);


    }


    public boolean ischeckedout(String title) {
        Firebase mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + title);
        boolean ischecked;
        mReffname.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //store the information in Map
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String checkedoutto = map.get("checkedoutto");
                SharedPreferences preferences = getSharedPreferences("checked", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.checkedoutto), checkedoutto);
                editor.commit();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        SharedPreferences preferences = getSharedPreferences("checked", Context.MODE_PRIVATE);
        String wordstring = preferences.getString(getString(R.string.checkedoutto), "");

        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String emailofuser = sp.getString(getString(R.string.email), "");

        if (wordstring.equals(emailofuser)) {
            ischecked = true;
        } else {
            ischecked = false;
        }
        return ischecked;
    }


    public void reviewonclick(View v) {
        final Context context = v.getContext();
        Intent activities = new Intent(context, WriteaReview.class);
        startActivity(activities);
        finish();
    }

    public void method() {
        Firebase mReffname = new Firebase("https://libeary-8d044.firebaseio.com/Books/" + CatalogFragment.titleofthebook);

        mReffname.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //store the information in Map
                Map<String, String> map = dataSnapshot.getValue(Map.class);

                String checkedoutto = map.get("checkedoutto");
                SharedPreferences preferences = getSharedPreferences("checked", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.checkedoutto), checkedoutto);
                editor.commit();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        final SharedPreferences sharedPreferences = getSharedPreferences("checked", Context.MODE_PRIVATE);
        //only allow reservations to be made when book is unavailable
        AlertDialog.Builder builder = new AlertDialog.Builder(BookInformation.this);
        builder.setTitle("Please enter your password to proceed");

        input = new EditText(BookInformation.this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String entered = input.getText().toString();
                SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                String password = sp.getString(getString(R.string.password), "Unknown");

                //make sure password is correct
                if (entered.equals(password)) {

                    //increase reserved count for Profile fragment to recrod count
                    reservedcount++;

                    notification = new NotificationCompat.Builder(BookInformation.this);
                    notification.setAutoCancel(true);

                    notification.setSmallIcon(R.mipmap.ic_launcher);
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle(CatalogFragment.titleofthebook + " reserved");
                    notification.setContentText("You have successfully reserved the following book: " + CatalogFragment.titleofthebook);

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(uniqueid, notification.build());

                    //initializeCountDrawerreserve();
                    updatereserve(CatalogFragment.reservedlist,
                            (sharedPreferences.getString(getString(R.string.checkedoutto), "nothing")));
                    //start the activity for confirm reservation
                    Intent activities = new Intent(BookInformation.this, ReservedConfirmation.class);
                    startActivity(activities);
                    finish();

                } else {
                    Toast.makeText(BookInformation.this, "password is incorrect", Toast.LENGTH_LONG).show();
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


