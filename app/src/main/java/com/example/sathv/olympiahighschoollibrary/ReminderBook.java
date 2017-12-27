package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class ReminderBook {
    public String title;
    public int imageid;
    public String duedatephrase;

    public ReminderBook(String title, int imageid, String duedatephrase) {
        this.title = title;
        this.imageid = imageid;
        this.duedatephrase = duedatephrase;
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
