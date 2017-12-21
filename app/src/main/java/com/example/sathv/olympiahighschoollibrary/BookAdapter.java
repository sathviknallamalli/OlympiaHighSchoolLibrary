package com.example.sathv.olympiahighschoollibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sathv on 12/15/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    Context context;
    int resource;
    ArrayList<Book> books = null;

    public BookAdapter(Context context, int resource,ArrayList<Book> books ) {
        super(context, resource, books);
        this.context = context;
        this.resource = resource;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = books.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customlayout, parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.titleofbookinreserved);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        TextView bookCount = (TextView) convertView.findViewById(R.id.pageCount);
        TextView bookCategory = (TextView) convertView.findViewById(R.id.bookCategory);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.reservedimage);

        bookTitle.setText(book.title);
        bookAuthor.setText(book.author);
        bookCount.setText("Pagecount: " + book.pageCount + "");
        bookCategory.setText(book.category);
        bookImage.setImageResource(book.imageid);

        return convertView;
    }
}
