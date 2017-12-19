package com.example.sathv.olympiahighschoollibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class ReservedConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_confirmation);

        setTitle("Confirm reservation");

        Toast.makeText(getApplicationContext(), "This book is reserved and you will be notified when the book is available for pickup", Toast.LENGTH_SHORT).show();

        TextView reserveBook = (TextView) findViewById(R.id.reservename);
        TextView reservedto = (TextView) findViewById(R.id.reserveusernmae);

        reserveBook.setText("Book name: " + CatalogFragment.titleofthebook);
        reservedto.setText("Reserved to " + Login.getUsername());
    }
}
