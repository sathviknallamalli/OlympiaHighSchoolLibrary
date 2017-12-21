package com.example.sathv.olympiahighschoollibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class CheckedOutConfirmation extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out_confirmation);

        setTitle("Confirm check out");

        Toast.makeText(getApplicationContext(), "This book is checked out to your account and avaiable for pickup.", Toast.LENGTH_LONG).show();

        CatalogFragment.setStatus("Unavailable", CatalogFragment.pos);

        TextView bookName = (TextView) findViewById(R.id.bookName);
        TextView checkedoutto = (TextView) findViewById(R.id.checkedoutto);
        TextView dueDate = (TextView) findViewById(R.id.dueDate);

        bookName.setText("Book name: " + CatalogFragment.titleofthebook);
        checkedoutto.setText("Checked out to " + Login.getUsername());

        dueDate.setText("Due date: " + BookInformation.getDatetoputinconfirmation());

    }
}
