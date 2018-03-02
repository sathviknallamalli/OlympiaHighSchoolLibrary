package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class Book {
    public String title;
    public String author;
    public String category;
    public String pageCount;
    public String added;
    public String isbn;
    public String status;
    public String summary;
    public int imageid;

    //parameters for Book contructor
    public Book(String title, String author, String pageCount, int imageid, String category, String added, String isbn, String status, String summary) {
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

    public String getTitle() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}