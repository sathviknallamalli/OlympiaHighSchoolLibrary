package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class WishlistBook {
    public String title;
    public String author;
    public String category;
    public int imageid;

    public WishlistBook(String title, String author, String category, int imageid) {
        this.title = title;
        this.author = author;
        this.imageid = imageid;
        this.category = category;
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
