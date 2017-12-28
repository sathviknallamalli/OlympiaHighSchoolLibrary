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

    /*public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }*/
}
