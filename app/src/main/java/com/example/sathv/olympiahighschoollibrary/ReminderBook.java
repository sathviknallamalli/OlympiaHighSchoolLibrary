package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReminderBook {
    public String title;
    public int imageid;
    public String duedatephrase;

    //reminder book adapter with necessary parameters to make reservedbook
    public ReminderBook(String title, int imageid, String duedatephrase) {
        this.title = title;
        this.imageid = imageid;
        this.duedatephrase = duedatephrase;
    }
}
