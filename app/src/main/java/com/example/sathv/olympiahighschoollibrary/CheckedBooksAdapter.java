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

public class CheckedBooksAdapter extends ArrayAdapter<CheckedBook> {

    Context context;
    int resource;
    ArrayList<CheckedBook> arraylistcheckedbooks = null;

    //checked books adapter contructor
    public CheckedBooksAdapter(Context context, int resource, ArrayList<CheckedBook> arraylistcheckedbooks ) {
        super(context, resource, arraylistcheckedbooks);
        this.context = context;
        this.resource = resource;
        this.arraylistcheckedbooks = arraylistcheckedbooks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckedBook checkedBook = arraylistcheckedbooks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemforchecked, parent, false);
        }

        //retrieve the fields
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView dateinchecked = (TextView) convertView.findViewById(R.id.checkedoutdateforrowitem);
        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookimage);

        //set the appropriate fields with the appropriate info
        bookTitle.setText(checkedBook.title);
        dateinchecked.setText("DUE DATE: "+ checkedBook.date);
        //change color od due date to be prominent
        dateinchecked.setTextColor(convertView.getResources().getColor(R.color.crimson));
        bookImage.setImageResource(checkedBook.imageid);

        return convertView;
    }
}
