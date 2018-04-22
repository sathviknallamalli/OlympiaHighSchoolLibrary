package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 3/1/2018.
 */

public class FirebaseBook {
    private String title, author, category, pagecount, summary, isbn, status, duedate, checkedoutto, reservations;

    public String getReservations() {
        return reservations;
    }

    public void setReservations(String reservations) {
        this.reservations = reservations;
    }

    public FirebaseBook(String title, String author, String category, String pagecount, String summary, String isbn,
                        String status, String duedate, String checkedoutto, String reservations) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.pagecount = pagecount;
        this.summary = summary;
        this.isbn = isbn;
        this.status = status;
        this.duedate = duedate;
        this.checkedoutto = checkedoutto;
        this.reservations = reservations;
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

    public String getPagecount() {
        return pagecount;
    }

    public void setPagecount(String pagecount) {
        this.pagecount = pagecount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getCheckedoutto() {
        return checkedoutto;
    }

    public void setCheckedoutto(String checkedoutto) {
        this.checkedoutto = checkedoutto;
    }
}
