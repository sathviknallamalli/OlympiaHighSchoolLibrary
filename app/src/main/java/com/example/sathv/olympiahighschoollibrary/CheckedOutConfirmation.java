package com.example.sathv.olympiahighschoollibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CheckedOutConfirmation extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out_confirmation);

        setTitle("Confirm check out");

        Toast.makeText(getApplicationContext(), "This book is checked out to your account and avaiable for pickup.", Toast.LENGTH_SHORT).show();

        CatalogFragment.setStatus("Unavailable", CatalogFragment.pos);

        TextView bookName = (TextView) findViewById(R.id.bookName);
        TextView checkedoutto = (TextView) findViewById(R.id.checkedoutto);
        TextView dueDate = (TextView) findViewById(R.id.dueDate);

        bookName.setText("Book name: " + CatalogFragment.titleofthebook);
        checkedoutto.setText("Checked out to " + Login.getUsername());

        int noOfDays = 14; //i.e two weeks
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();

        calendar.setTime(currentDate);

        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date duedate = calendar.getTime();

        dueDate.setText("Due date: " + duedate.toString());

    }
}
