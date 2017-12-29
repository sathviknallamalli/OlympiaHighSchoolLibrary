package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReservedBook {
    public String title;
    public String author;
    public int imageid;

    //returned book constructor and necessary parameter
    public ReservedBook(String title, String author, int imageid) {
        this.title = title;
        this.author = author;
        this.imageid = imageid;
    }
}
