package com.example.sathv.olympiahighschoollibrary;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class CheckedOutConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out_confirmation);
        setTitle("Confirm check out");

        CatalogFragment cf = new CatalogFragment();

        View view = (ConstraintLayout) findViewById(R.id.clayout);
        final Snackbar snackbar = Snackbar.make(view, cf.capitalzeTitle(CatalogFragment.titleofthebook) + "has been checked out and available for pickup", Snackbar.LENGTH_LONG);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

        //update the status of the book to unavailable
        cf.setStatus("Unavailable", CatalogFragment.pos);

        //set each field for confirmation
        TextView bookName = (TextView) findViewById(R.id.bookName);
        TextView checkedoutto = (TextView) findViewById(R.id.checkedoutto);
        TextView dueDate = (TextView) findViewById(R.id.dueDate);

        bookName.setText("Book name: " + cf.capitalzeTitle(CatalogFragment.titleofthebook));
        checkedoutto.setText("Checked out to " + Login.getUsername());
        dueDate.setText("Due date: " + BookInformation.getDatetoputinconfirmation());
    }
}
