package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class Book {
    public String title;
    public String author;
    public String category;
    public int pageCount;
    public int imageid;
    public String added;
    public String isbn;
    public String status;
    public String summary;

    //parameters for Book contructor
    public Book(String title, String author, int pageCount, int imageid, String category, String added, String isbn, String status, String summary) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.imageid = imageid;
        this.category = category;
        this.added = added;
        this.isbn = isbn;
        this.status = status;
        this.summary = summary;
    }
}