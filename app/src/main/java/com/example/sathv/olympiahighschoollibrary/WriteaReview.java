package com.example.sathv.olympiahighschoollibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WriteaReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writea_review);

        setTitle(CatalogFragment.title + " Write a review");


    }
}
