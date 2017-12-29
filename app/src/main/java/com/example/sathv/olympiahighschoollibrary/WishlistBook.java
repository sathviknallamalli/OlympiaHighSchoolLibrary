package com.example.sathv.olympiahighschoollibrary;

/**
 * Created by sathv on 12/15/2017.
 */

public class WishlistBook {
    public String title;
    public String author;
    public String category;
    public int imageid;

    //wishlist book constructor with necessary parameters for customlayout in wishlist
    public WishlistBook(String title, String author, String category, int imageid) {
        this.title = title;
        this.author = author;
        this.imageid = imageid;
        this.category = category;
    }
}
