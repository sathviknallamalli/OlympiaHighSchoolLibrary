package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class CheckedBook {

    //checked book necessary fields
    public String title;
    public String date;
    public int imageid;

    //constructor
    public CheckedBook(String title, String date, int imageid) {
        this.title = title;
        this.date = date;
        this.imageid = imageid;
    }
}
