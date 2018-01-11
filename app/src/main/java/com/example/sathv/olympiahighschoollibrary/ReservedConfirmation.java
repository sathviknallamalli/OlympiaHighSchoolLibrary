package com.example.sathv.olympiahighschoollibrary;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ReservedConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_confirmation);
        setTitle("Confirm reservation");

        CatalogFragment cf = new CatalogFragment();

        View view = (ConstraintLayout) findViewById(R.id.rlayout);
        final Snackbar snackbar = Snackbar.make(view, cf.capitalzeTitle(CatalogFragment.titleofthebook) + "has been reserved and you will be notified when the book is avilable for pickup", Snackbar.LENGTH_LONG);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

        //retireve variables
        TextView reserveBook = (TextView) findViewById(R.id.reservename);
        TextView reservedto = (TextView) findViewById(R.id.reserveusernmae);

        //set each field with appropriate variables by retrieving as static variables
        reserveBook.setText("Book name: " + cf.capitalzeTitle(CatalogFragment.titleofthebook));
        reservedto.setText("Reserved to " + Login.getUsername());
    }
}
