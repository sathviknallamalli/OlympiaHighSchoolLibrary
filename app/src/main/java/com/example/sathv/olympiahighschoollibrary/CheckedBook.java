package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class CheckedBook {

    //checked book necessary fields
    public String title;
    public String date;
    public int imageid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    //constructor
    public CheckedBook(String title, String date, int imageid) {
        this.title = title;
        this.date = date;
        this.imageid = imageid;
    }
}
